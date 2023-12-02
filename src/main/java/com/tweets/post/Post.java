
package com.tweets.post;

import com.tweets.comment.Comment;
import com.tweets.common.entity.BaseEntity;
import com.tweets.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @Column(name = "numberOfLikes")
    private String numberOfLikes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();
}
