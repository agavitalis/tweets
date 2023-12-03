package com.tweets.user.entity;

import com.tweets.comment.entity.Comment;
import com.tweets.common.entity.BaseEntityAudit;
import com.tweets.post.entity.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table( name = "users")
public class User extends BaseEntityAudit {
    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "profilePicture")
    private String profilePicture;

    @Column(name = "password")
    private String password;

    //relationships
    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();
}
