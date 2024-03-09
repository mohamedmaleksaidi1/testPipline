package com.centranord.DTO;

import lombok.*;

import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoleWithPermissionsRequest {
    private String roleName;
    private Set<String> permissionIds;


}
