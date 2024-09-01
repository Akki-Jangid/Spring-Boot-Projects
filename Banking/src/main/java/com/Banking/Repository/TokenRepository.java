package com.Banking.Repository;

import com.Banking.Dto.Jwt.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends MongoRepository<Token, Long> {

    boolean existsByToken(String token);
    void deleteByToken(String token);
}
