package com.centranord.Controller;

import com.centranord.Config.UserNotFoundException;
import com.centranord.DTO.ChangePasswordRequest;
import com.centranord.DTO.EmailRequest;
import com.centranord.DTO.RegisterRequest;
import com.centranord.DTO.UserCreateRequest;
import com.centranord.Enum.ActionPermission;
import com.centranord.Repository.PermissionRepository;
import com.centranord.Repository.UserRepository;
import com.centranord.Entity.Permission;

import com.centranord.Entity.User;
import com.centranord.ServiceInterface.UserService;


import com.centranord.Services.EmailSenderService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private final UserService userService;
    private final EmailSenderService senderService;

    @Autowired
    private UserRepository repository;


    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody UserCreateRequest request) {
        User user = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("getById/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") String userId) {
        Optional<User> userOptional = userService.getUserById(userId);
        return userOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PatchMapping("changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request, Principal connectedUser
    ) {
        try {
            userService.changePassword(request, connectedUser);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/getallusers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }






    @PatchMapping("modifierMotDePasse/{userId}")
    public ResponseEntity<String> modifierMotDePasse(@PathVariable String userId, @RequestParam String nouveauMotDePasse, @RequestParam String confirmationMotDePasse) {
        try {
            userService.modifierMotDePasse(userId, nouveauMotDePasse,confirmationMotDePasse);
            return ResponseEntity.ok().body("Mot de passe modifié avec succès pour l'utilisateur avec l'ID : " + userId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/sendEmail")
    public ResponseEntity<User> sendEmail(@RequestBody EmailRequest emailRequest) {
        try {

            Optional<User> userOptional = repository.findByEmail(emailRequest.getToEmail());

            if (userOptional.isPresent()) {

                User user = userOptional.get();


                String subject = "Mail envoyé par l'équipe CentraNord - Restarter votre mot de passe";
                String body = "";
                senderService.sendSimpleEmail(emailRequest.getToEmail(), subject, body);


                return ResponseEntity.ok(user);
            } else {

                return ResponseEntity.notFound().build();
            }
        } catch (MessagingException e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PatchMapping("/{userId}")
    public User updateUser(@PathVariable String userId, @RequestBody RegisterRequest userUpdateDTO) {
        return userService.updateUser(userId, userUpdateDTO);
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable String userId) {
        try {
            userService.deleteUserById(userId);
            return ResponseEntity.ok().body("Utilisateur supprimé avec succès.");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite lors de la suppression de l'utilisateur.");
        }
    }
}
