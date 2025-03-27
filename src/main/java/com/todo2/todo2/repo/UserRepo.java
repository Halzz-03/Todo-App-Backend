package com.todo2.todo2.repo;

import com.todo2.todo2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {


    Optional<User> findByName(String username);
}
