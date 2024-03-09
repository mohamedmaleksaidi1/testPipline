package com.centranord.Entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Builder
@Document(collection = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements UserDetails {

    @Id
    private String id;




    private String firstname;
    private String lastName;

    private String email;
    private boolean status;

    @Getter
    private String password;






    private String address;

    private String governorate;



    private Role role;



    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



}
