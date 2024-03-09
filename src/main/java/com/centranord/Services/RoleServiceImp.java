package com.centranord.Services;


import com.centranord.DTO.CreateRoleAndAssignPermissionsRequest;
import com.centranord.Entity.Permission;
import com.centranord.Entity.Role;
import com.centranord.Enum.ActionPermission;
import com.centranord.Repository.PermissionRepository;
import com.centranord.Repository.RoleRepository;
import com.centranord.ServiceInterface.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImp  implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;


    @Override
    public Role createRoleAndAssignPermissions(CreateRoleAndAssignPermissionsRequest request) {
        Role role = new Role();
        role.setName(request.getRoleName());
        Role savedRole = roleRepository.save(role);

        Set<Permission> permissions = new HashSet<>();
        for (String permissionId : request.getPermissionIds()) {
            Optional<Permission> optionalPermission = permissionRepository.findById(permissionId);
            if (optionalPermission.isPresent()) {
                permissions.add(optionalPermission.get());
            } else {
                throw new NotFoundException("La permission demandée n'a pas été trouvée pour l'ID : " + permissionId);
            }
        }
        savedRole.setPermissions(permissions);


        return roleRepository.save(savedRole);

    }


    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();

    }

    @Override
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    @Override
    public Permission createPermission(String resourceName, ActionPermission action) {
        Permission permission = new Permission();
        permission.setResourceName(resourceName);
        permission.setAction(action);

        return permissionRepository.save(permission);
    }

}
