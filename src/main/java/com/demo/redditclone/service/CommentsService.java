package com.demo.redditclone.service;

import com.demo.redditclone.dto.CommentsDto;
import com.demo.redditclone.exceptions.PostNotFoundException;
import com.demo.redditclone.mapper.CommentMapper;
import com.demo.redditclone.model.Comment;
import com.demo.redditclone.model.NotificationEmail;
import com.demo.redditclone.model.Post;
import com.demo.redditclone.model.User;
import com.demo.redditclone.repositories.CommentRepository;
import com.demo.redditclone.repositories.PostRepository;
import com.demo.redditclone.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentsService {

    private static final String POST_URL = "";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailService mailService;

    public void save(CommentsDto commentsDto) {
       Post post = postRepository.findById(commentsDto.getPostId())
               .orElseThrow(() -> new PostNotFoundException("Post not found with Id " + commentsDto.getPostId()));
       Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
       commentRepository.save(comment);

        String message = post.getUser().getUsername() + " posted a comment on your post." + POST_URL;
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
    }

    public List<CommentsDto> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException("User not found with name " + userName));
        return commentRepository.findAllByUser(user).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
    }
}
