package com.demo.redditclone.repositories;

import com.demo.redditclone.model.Post;
import com.demo.redditclone.model.Subreddit;
import com.demo.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findAllByUser(User user);
}
