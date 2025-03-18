package com.springdata.demo.repository;

import com.mongodb.lang.NonNull;
import com.springdata.demo.entity.LoginUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<LoginUser, String> {
    Optional<LoginUser> findById(@NonNull String id);
    boolean existsByUsername(String username);
    boolean existsById(@NonNull String LoginUserId);
    void deleteById(@NonNull String id);

    Optional<LoginUser> findByUsername(String username);
}
