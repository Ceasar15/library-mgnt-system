package com.capstone.library.repository;

import com.capstone.library.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {

    Boolean existsByUsername(String username);

    Optional<UserModel> findByUsername(String username);
}
