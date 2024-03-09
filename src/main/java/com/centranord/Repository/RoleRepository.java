package com.centranord.Repository;

import com.centranord.Entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
    Role findByName(String name);
    Optional<Role> findById(String id);

    List<Role> findAll();

}
