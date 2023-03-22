package com.demo.redditclone.repositories;

import com.demo.redditclone.model.Post;
import com.demo.redditclone.model.User;
import com.demo.redditclone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
