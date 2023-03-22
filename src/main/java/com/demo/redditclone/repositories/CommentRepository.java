package com.demo.redditclone.repositories;

import com.demo.redditclone.model.Comment;
import com.demo.redditclone.model.Post;
import com.demo.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
