package com.centranord.ServiceInterface;

import com.centranord.DTO.CreateRoleAndAssignPermissionsRequest;
import com.centranord.Entity.Permission;
import com.centranord.Entity.Role;
import com.centranord.Enum.ActionPermission;

import java.util.List;

public interface RoleService {
    Role createRoleAndAssignPermissions(CreateRoleAndAssignPermissionsRequest request);
    List<Role> getAllRoles();
    List<Permission> getAllPermissions();
    Permission createPermission(String resourceName, ActionPermission action);

     Role updateRole(String roleId, CreateRoleAndAssignPermissionsRequest updateRoleRequest);
     void deleteRoleById(String roleId);
}
