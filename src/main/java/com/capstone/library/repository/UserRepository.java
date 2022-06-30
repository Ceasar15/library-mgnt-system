package com.capstone.library.repository;

import com.capstone.library.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<UserModel, Long> {
    public static final String FindAllLibrarians =
            "SELECT  users.id, users.username, users.email, " + "user_type.role " + "FROM" + " " +
                    "users INNER JOIN user_and_roles " + "ON user_and_roles.user_id = users.id " +
                    "INNER " + "JOIN user_type ON " + "user_type.id = user_and_roles.role_type_id ";

    @Query(value = FindAllLibrarians, nativeQuery = true)
    public List<UserModelMini> findAllLibrarians();


    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<UserModel> findByUsername(String username);
}
