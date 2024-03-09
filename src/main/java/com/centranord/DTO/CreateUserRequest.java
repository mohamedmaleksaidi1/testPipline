package com.centranord.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    private String username;
    private String password;
    private String role;
    private List<String> permissions;


}
