package com.capstone.library.repository;

import com.capstone.library.model.Actors;
import com.capstone.library.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleTypeRepository extends JpaRepository<RoleType, Long> {

    Optional<RoleType> findById(Integer id);

    Optional<RoleType> findByRole(Actors role);
}
