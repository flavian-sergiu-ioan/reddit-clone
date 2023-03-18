package com.demo.redditclone.service;

import com.demo.redditclone.dto.SubredditDto;
import com.demo.redditclone.model.Subreddit;
import com.demo.redditclone.repositories.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit subreddit =  subredditRepository.save(mapSubredditdto(subredditDto));
        subredditDto.setId(subreddit.getId());
        return subredditDto;
    }

    private Subreddit mapSubredditdto(SubredditDto subredditDto) {
        return Subreddit.builder().name(subredditDto.getName()).description(subredditDto.getDescription()).build();
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private SubredditDto mapToDto(Subreddit subreddit) {
        return SubredditDto.builder()
                .name(subreddit.getName())
                .description(subreddit.getDescription())
                .id(subreddit.getId())
                .numberOfPosts(subreddit.getPosts().size())
                .build();
    }
}
