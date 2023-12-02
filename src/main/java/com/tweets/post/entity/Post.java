
package com.tweets.post.entity;

import com.tweets.comment.entity.Comment;
import com.tweets.common.entity.BaseEntity;
import com.tweets.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "posts")
public class Post extends BaseEntity {

    @Column(name = "content")
    private String content;

    @Column(name = "numberOfLikes", columnDefinition = "number default 0")
    private Number numberOfLikes;

    //relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();
}
