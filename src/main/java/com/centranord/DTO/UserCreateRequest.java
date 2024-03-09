package com.centranord.DTO;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserCreateRequest {
    private String firstname;
    private String lastName;
    private String email;
    private String password;
    private String address;
    private boolean status;
    private String governorate;
    private String roleId;
}
