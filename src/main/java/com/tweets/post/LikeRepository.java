package com.tweets.post;

import com.tweets.post.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like,Long> {
    boolean existsByUserIdAndPostId(Long userId, Long postId);
}
