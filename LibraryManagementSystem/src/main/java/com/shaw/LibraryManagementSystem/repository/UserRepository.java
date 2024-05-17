package com.shaw.LibraryManagementSystem.repository;

import com.shaw.LibraryManagementSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query(value = "select * from user where email=:email",nativeQuery = true)
    Optional<User> findByEmail(@Param("email") String email);
}
