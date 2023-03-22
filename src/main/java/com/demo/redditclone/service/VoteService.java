package com.demo.redditclone.service;

import com.demo.redditclone.dto.VoteDto;
import com.demo.redditclone.exceptions.PostNotFoundException;
import com.demo.redditclone.exceptions.SpringRedditException;
import com.demo.redditclone.mapper.VoteMapper;
import com.demo.redditclone.model.Post;
import com.demo.redditclone.model.User;
import com.demo.redditclone.model.Vote;
import com.demo.redditclone.model.VoteType;
import com.demo.redditclone.repositories.PostRepository;
import com.demo.redditclone.repositories.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final PostRepository postRepository;
    private final VoteRepository voteRepository;
    private final AuthService authService;
    private final VoteMapper voteMapper;

    public void vote(VoteDto voteDto) {
        User currentUser = authService.getCurrentUser();
        Post post = postRepository.findById(voteDto.getPostId()).orElseThrow(() -> new PostNotFoundException("Post not found with id - " + voteDto.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, currentUser);
        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
            throw new SpringRedditException("You have already " + voteDto.getVoteType() + " for this post");
        }
        if (VoteType.UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(voteMapper.map(voteDto, post, currentUser));
        postRepository.save(post);
    }

}
