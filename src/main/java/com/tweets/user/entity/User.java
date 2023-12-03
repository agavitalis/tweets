package com.tweets.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tweets.comment.entity.Comment;
import com.tweets.common.entity.BaseEntityAudit;
import com.tweets.post.entity.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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


    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<UserFollows> following = new HashSet<>();

    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<UserFollows> followers = new HashSet<>();

    public void follow(User user) {
        UserFollows userFollows = new UserFollows(this, user);
        following.add(userFollows);
        user.getFollowers().add(userFollows);
    }

    public void unfollow(User user) {
        UserFollows userFollows = new UserFollows(this, user);
        following.remove(userFollows);
        user.getFollowers().remove(userFollows);
    }

}
