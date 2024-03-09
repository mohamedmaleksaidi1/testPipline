package com.centranord.Repository;

import com.centranord.Entity.Reclamation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReclamationRepository extends MongoRepository<Reclamation, String> {

}
