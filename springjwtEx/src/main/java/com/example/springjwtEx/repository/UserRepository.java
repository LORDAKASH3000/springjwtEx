package com.example.springjwtEx.repository;

import com.example.springjwtEx.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
    @NonNull
    User getReferenceById(@NonNull Integer id);
}
