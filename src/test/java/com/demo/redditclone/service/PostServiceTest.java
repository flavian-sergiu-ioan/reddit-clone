package com.demo.redditclone.service;

import com.demo.redditclone.dto.PostRequest;
import com.demo.redditclone.dto.PostResponse;
import com.demo.redditclone.mapper.PostMapper;
import com.demo.redditclone.model.Post;
import com.demo.redditclone.model.Subreddit;
import com.demo.redditclone.model.User;
import com.demo.redditclone.repositories.PostRepository;
import com.demo.redditclone.repositories.SubredditRepository;
import com.demo.redditclone.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static java.util.Collections.emptyList;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private SubredditRepository subredditRepository;
    @Mock
    private AuthService authService;
    @Mock
    private PostMapper postMapper;
    @Mock
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;
    @Captor
    private ArgumentCaptor<Post> postArgumentCaptor;

    PostService postService;

    @BeforeEach
    public void setup() {
        postService = new PostService(subredditRepository, authService, postMapper, postRepository, userRepository);
    }

    @Test
    @DisplayName("should find post by Id")
    public void shouldFindPostById() {
        final Post post = new Post(123L, "First post", "http://url.site", "Test", 0, null, Instant.now(), null);
        final PostResponse postResponse = new PostResponse(123L, "First post", "http://url.site", "Test", "Test user", "Test Subreddit", 0, 0, "1 Hour ago", false, false);
        Mockito.when(postMapper.mapToDto(Mockito.any(Post.class))).thenReturn(postResponse);
        Mockito.when(postRepository.findById(123L)).thenReturn(Optional.of(post));

        final PostResponse actualResponse = postService.getPost(123L);
        Assertions.assertEquals(actualResponse.getId(), postResponse.getId());
        Assertions.assertEquals(actualResponse.getPostName(), postResponse.getPostName());

    }

    @Test
    @DisplayName("Should Save Posts")
    void shouldSavePosts() {
        User currentUser = new User(123L, "test user", "secret password", "user@email.com", Instant.now(), true);
        Subreddit subreddit = new Subreddit(123L, "First Subreddit", "Subreddit Description", emptyList(), Instant.now(), currentUser);
        Post post = new Post(123L, "First Post", "http://url.site", "Test",
                0, null, Instant.now(), null);
        PostRequest postRequest = new PostRequest(null, "First Subreddit", "First Post", "http://url.site", "Test");

        Mockito.when(subredditRepository.findByName("First Subreddit")).thenReturn(Optional.of(subreddit));
        Mockito.when(authService.getCurrentUser()).thenReturn(currentUser);
        Mockito.when(postMapper.map(postRequest, subreddit, currentUser)).thenReturn(post);

        postService.save(postRequest);

        Mockito.verify(postRepository, Mockito.times(1)).save(postArgumentCaptor.capture());

        Assertions.assertEquals(postArgumentCaptor.getValue().getPostId(), 123L);
        Assertions.assertEquals(postArgumentCaptor.getValue().getPostName(), "First Post");
    }

}