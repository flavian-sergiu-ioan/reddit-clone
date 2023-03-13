package com.demo.redditclone.repositories;

import com.demo.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
