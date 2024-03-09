package com.centranord.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.centranord.Enum.ActionPermission;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Document(collection = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role  {
    @Id
    private String id;
    private String name;

    private Set<Permission> permissions;



    public Collection<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        if (permissions != null) {
            permissions.forEach(permission -> {
                String resourceName = permission.getResourceName();
                ActionPermission action = permission.getAction();
                if (action != null) {
                    String authority = resourceName  + action.name();
                    authorities.add(new SimpleGrantedAuthority(authority));
                }
            });
        }

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name));

        return authorities;
    }


}
