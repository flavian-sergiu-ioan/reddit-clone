package com.demo.redditclone.service;

import com.demo.redditclone.dto.PostRequest;
import com.demo.redditclone.dto.PostResponse;
import com.demo.redditclone.exceptions.PostNotFoundException;
import com.demo.redditclone.exceptions.SpringRedditException;
import com.demo.redditclone.exceptions.SubredditNotFoundException;
import com.demo.redditclone.mapper.PostMapper;
import com.demo.redditclone.model.Post;
import com.demo.redditclone.model.Subreddit;
import com.demo.redditclone.model.User;
import com.demo.redditclone.repositories.PostRepository;
import com.demo.redditclone.repositories.SubredditRepository;
import com.demo.redditclone.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final SubredditRepository subredditRepository;

    private final AuthService authService;

    private final PostMapper postMapper;

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    public void save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName()).orElseThrow(() -> new SubredditNotFoundException("Subreddit not found with name - " + postRequest.getSubredditName()));
        postRepository.save(postMapper.map(postRequest, subreddit, authService.getCurrentUser()));
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream().map(postMapper::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
      Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post not found for id - " + id));
      return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id).orElseThrow(() -> new SubredditNotFoundException("Subreddit not found with id - " + id));
        return postRepository.findAllBySubreddit(subreddit).stream().map(postMapper::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String name) {
        User user = userRepository.findByUsername(name).orElseThrow(() -> new SpringRedditException("User not found - " + name));
        return postRepository.findAllByUser(user).stream().map(postMapper::mapToDto).collect(Collectors.toList());
    }
}
