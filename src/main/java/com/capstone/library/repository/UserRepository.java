package com.capstone.library.repository;

import com.capstone.library.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<UserModel, Long> {
    public static final String FindAllLibrarians = "SELECT  id, username, email FROM users";

    @Query(value = FindAllLibrarians, nativeQuery = true)
    public List<UserModelMini> findAllLibrarians();


    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<UserModel> findByUsername(String username);
}
