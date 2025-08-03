package com.backend.authservice.repository;


import com.backend.authservice.entity.User;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
@Hidden
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findUserByUserName(String username);
    boolean existsUsersByUserName(String username);
    @Query("SELECT u FROM User u JOIN FETCH u.role WHERE u.isDeleted = false")
    List<User> getAllByIsDeletedFalse();
}