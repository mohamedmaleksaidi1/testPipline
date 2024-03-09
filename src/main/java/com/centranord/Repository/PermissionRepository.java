package com.centranord.Repository;

import com.centranord.Entity.Permission;
import com.centranord.Enum.ActionPermission;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends MongoRepository<Permission, String> {
    Permission findByResourceNameAndAction(String resourceName, ActionPermission action);
    List<Permission> findAll();

}
