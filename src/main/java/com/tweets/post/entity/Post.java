
package com.tweets.post.entity;

import com.tweets.comment.entity.Comment;
import com.tweets.common.entity.BaseEntityAudit;
import com.tweets.user.entity.User;
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
public class Post extends BaseEntityAudit {

    @Column(name = "content")
    private String content;

    @Column(name = "numberOfLikes", columnDefinition = "int default 0")
    private Integer numberOfLikes;

    //relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Like> likes = new ArrayList<>();
}
