package com.demo.redditclone.mapper;


import com.demo.redditclone.dto.VoteDto;
import com.demo.redditclone.model.Post;
import com.demo.redditclone.model.User;
import com.demo.redditclone.model.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VoteMapper {

    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "voteType", source = "voteDto.voteType")
    Vote map(VoteDto voteDto, Post post, User user);

}
