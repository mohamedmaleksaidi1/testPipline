package com.centranord.Controller;

import com.centranord.DTO.CreateRoleAndAssignPermissionsRequest;
import com.centranord.DTO.RoleCreateRequest;
import com.centranord.Enum.ActionPermission;
import com.centranord.Repository.PermissionRepository;
import com.centranord.Repository.RoleRepository;
import com.centranord.Entity.Permission;
import com.centranord.Entity.Role;
import com.centranord.ServiceInterface.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RoleController {


    @Autowired
    private RoleService roleService;







    @GetMapping("/getallroles")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }



    @PostMapping("/createRoleAndAssignPermissions")
    public ResponseEntity<?> createRoleAndAssignPermissions(@RequestBody CreateRoleAndAssignPermissionsRequest request) {
        Role savedRole = roleService.createRoleAndAssignPermissions(request);
        return ResponseEntity.ok(savedRole);
    }


    @GetMapping("/getAllPermissions")
    public List<Permission> getAllPermissions() {
        return roleService.getAllPermissions();
    }

    @PostMapping("/createPermission")
    public ResponseEntity<?> createPermission(@RequestParam String resourceName, @RequestParam ActionPermission action) {
        Permission savedPermission = roleService.createPermission(resourceName, action);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPermission);
    }
    @PatchMapping("/{roleId}")
    public ResponseEntity<Role> updateRole(@PathVariable String roleId, @RequestBody CreateRoleAndAssignPermissionsRequest updateRoleRequest) {
        Role updatedRole = roleService.updateRole(roleId, updateRoleRequest);
        return ResponseEntity.ok(updatedRole);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<?> deleteRole(@PathVariable String roleId) {
        roleService.deleteRoleById(roleId);
        return ResponseEntity.ok().build();
    }

}