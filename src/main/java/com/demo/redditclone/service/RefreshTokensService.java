package com.demo.redditclone.service;

import com.demo.redditclone.exceptions.SpringRedditException;
import com.demo.redditclone.model.RefreshToken;
import com.demo.redditclone.repositories.RefreshTokenRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokensService {

    private final RefreshTokenRespository refreshTokenRespository;

    public RefreshToken generateRefreshTokens() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return refreshTokenRespository.save(refreshToken);
    }

    public void validateRefreshToken(String token) {
        refreshTokenRespository.findByToken(token).orElseThrow(() -> new SpringRedditException("Invalid refresh token " + token));
    }

    public void deleteRefreshToken(String token) {
        refreshTokenRespository.deleteByToken(token);
    }
}
