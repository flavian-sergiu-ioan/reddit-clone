package com.demo.redditclone.service;

import com.demo.redditclone.dto.SubredditDto;
import com.demo.redditclone.exceptions.SpringRedditException;
import com.demo.redditclone.mapper.SubredditMapper;
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
    private final SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit subreddit =  subredditRepository.save(subredditMapper.mapDtoToEntity(subredditDto));
        subredditDto.setId(subreddit.getId());
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll().stream().map(subredditMapper::mapEntityToDto).collect(Collectors.toList());
    }

    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id).orElseThrow(() -> new SpringRedditException("No subreddit found for id -" + id));
        return subredditMapper.mapEntityToDto(subreddit);
    }
}
