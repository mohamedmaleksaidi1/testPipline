package com.centranord.DTO;


import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RegisterRequest {




    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private boolean status;
    private String roleId;


    private String address;

    private String governorate;
}
