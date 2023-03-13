package com.demo.redditclone.repositories;

import com.demo.redditclone.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
