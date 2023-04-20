package com.demo.redditclone.service;

import com.demo.redditclone.exceptions.SpringRedditException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CommentsServiceTest {

    @Test
    @DisplayName("should not include swear name in the comment")
    public void shouldNotcontainSwearWordsInsideComment() {
        CommentsService commentsService = new CommentsService(null, null, null, null, null, null);
        Assertions.assertFalse(commentsService.containsSwearWords("This is a clean comment"));
    }

    @Test
    @DisplayName("should throw exceptions when comment includes swear word")
    public void shouldFailWhenCommentContainsSwearWords() {
      CommentsService commentsService = new CommentsService(null, null, null, null, null, null);
      SpringRedditException springRedditException = Assertions.assertThrows(SpringRedditException.class, () -> commentsService.containsSwearWords("This is shit comment"));
      Assertions.assertTrue(springRedditException.getMessage().contains("Comments contains unacceptable language"));
    }
}