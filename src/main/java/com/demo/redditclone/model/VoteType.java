package com.demo.redditclone.model;

import com.demo.redditclone.exceptions.SpringRedditException;

import java.util.Arrays;

public enum VoteType {
    UPVOTE(1), DOWNVOTE(-1);

    private Integer direction;

    VoteType(Integer direction) {
        this.direction = direction;
    }

    public static VoteType lookup(Integer direction) {
        return Arrays.stream(VoteType.values())
                .filter(value -> value.getDirection().equals(direction))
                .findAny()
                .orElseThrow(() -> new SpringRedditException("Vote not found"));
    }

    public Integer getDirection() {
        return direction;
    }
}
