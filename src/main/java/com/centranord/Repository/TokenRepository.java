package com.centranord.Repository;


import com.centranord.Entity.Token;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface TokenRepository extends MongoRepository<Token, String> {

    List<Token> findAllByUserIdAndExpiredFalseOrRevokedFalse(String userId);

    Optional<Token> findByToken(String token);

}
