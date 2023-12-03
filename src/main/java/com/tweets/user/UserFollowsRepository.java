package com.tweets.user;

import com.tweets.user.entity.User;
import com.tweets.user.entity.UserFollows;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserFollowsRepository extends JpaRepository<UserFollows, Long> {
    UserFollows findByFollowerAndFollowing(User follower, User following);
}
