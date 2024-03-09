package com.centranord.ServiceInterface;

import com.centranord.DTO.ChangePasswordRequest;
import com.centranord.DTO.RegisterRequest;
import com.centranord.DTO.UserCreateRequest;
import com.centranord.Entity.User;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


public interface UserService {
    User createUser(UserCreateRequest request);
    void changePassword(ChangePasswordRequest request, Principal connectedUser);
     List<User> getAllUsers();
     Optional<User> getUserById(String id);
    public void deleteUserById(String userId);
    User updateUser(String userId, RegisterRequest userUpdateDTO) ;
     void modifierMotDePasse(String userId, String nouveauMotDePasse, String confirmationMotDePasse);
}
