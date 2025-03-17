package com.springdata.demo.repository;

import com.mongodb.lang.NonNull;
import com.springdata.demo.entity.AdditionalData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdditionalDataRepository extends MongoRepository<AdditionalData, String> {
    boolean existsByLoginUserId(String loginUserId);
    Optional<AdditionalData> findByLoginUserId(String userId);
     void deleteById(@NonNull String id);
}
