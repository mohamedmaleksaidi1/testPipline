package com.centranord.Services;

import com.centranord.Config.UserNotFoundException;
import com.centranord.DTO.ChangePasswordRequest;
import com.centranord.DTO.RegisterRequest;
import com.centranord.DTO.UserCreateRequest;
import com.centranord.Entity.Role;
import com.centranord.Entity.User;
import com.centranord.Repository.RoleRepository;
import com.centranord.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.centranord.ServiceInterface.UserService;
import org.webjars.NotFoundException;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImp  implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository repositoryRole;

    public Set<String> getPasswordValidityErrors(String password) {
        Set<String> errors = new HashSet<>();

        if (!Pattern.compile(".*[0-9].*").matcher(password).find()) {
            errors.add("Le mot de passe doit contenir au moins un chiffre.");
        }
        if (!Pattern.compile(".*[a-z].*").matcher(password).find()) {
            errors.add("Le mot de passe doit contenir au moins une lettre minuscule.");
        }
        if (!Pattern.compile(".*[A-Z].*").matcher(password).find()) {
            errors.add("Le mot de passe doit contenir au moins une lettre majuscule.");
        }
        if (!Pattern.compile(".*[@#$%^&+=!].*").matcher(password).find()) {
            errors.add("Le mot de passe doit contenir au moins un caractère spécial.");
        }
        if (password.length() < 8) {
            errors.add("Le mot de passe doit contenir au moins 8 caractères.");
        }

        return errors;
    }

    @Override
    public User createUser(UserCreateRequest request) {
        Optional<Role> optionalRole = repositoryRole.findById(request.getRoleId());

        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe ne peut pas être vide.");
        }

        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new IllegalArgumentException("L'adresse e-mail ne peut pas être vide.");
        }

        Set<String> passwordErrors = getPasswordValidityErrors(request.getPassword());
        if (!passwordErrors.isEmpty()) {
            throw new IllegalArgumentException("Erreurs de validation du mot de passe : " + passwordErrors);
        }

        var user = User.builder()
                .lastName(request.getLastName())
                .firstname(request.getFirstname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(optionalRole.orElseThrow(() -> new NotFoundException("Le rôle spécifié n'a pas été trouvé.")))
                .address(request.getAddress())
                .governorate(request.getGovernorate())
                .status(request.isStatus())
                .build();

        return userRepository.save(user);
    }




    @Override
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Mot de passe actuel incorrect");
        }

        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Le nouveau mot de passe et la confirmation ne correspondent pas");
        }
        Set<String> passwordErrors = getPasswordValidityErrors(request.getNewPassword());
        if (!passwordErrors.isEmpty()) {
            throw new IllegalArgumentException("Le nouveau mot de passe ne respecte pas les critères de complexité: " + String.join(", ", passwordErrors));
        }


        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        System.out.println("Modification réussie du mot de passe !");
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }


    @Override
    public User updateUser(String userId, RegisterRequest userUpdateDTO)  {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        // Mettez à jour uniquement les champs spécifiés dans le DTO
        if (userUpdateDTO.getFirstname() != null) {
            existingUser.setFirstname(userUpdateDTO.getFirstname());
        }
        if (userUpdateDTO.getLastname() != null) {
            existingUser.setFirstname(userUpdateDTO.getFirstname());
        }
        if (userUpdateDTO.getEmail() != null) {
            existingUser.setEmail(userUpdateDTO.getEmail());
        }
        if (userUpdateDTO.getPassword() != null) {
            existingUser.setPassword(userUpdateDTO.getPassword());
        }

        if (userUpdateDTO.getAddress() != null) {
            existingUser.setAddress(userUpdateDTO.getAddress());
        }
        if (userUpdateDTO.getGovernorate() != null) {
            existingUser.setGovernorate(userUpdateDTO.getGovernorate());
        }


        if (userUpdateDTO.getRoleId() != null) {
            Role role = repositoryRole.findById(userUpdateDTO.getRoleId())
                    .orElseThrow(() -> new UserNotFoundException("Role not found with id: " + userUpdateDTO.getRoleId()));
            existingUser.setRole(role);
        }

        return userRepository.save(existingUser);
    }

    @Override
    public void modifierMotDePasse(String userId, String nouveauMotDePasse, String confirmationMotDePasse) {
        if (!nouveauMotDePasse.equals(confirmationMotDePasse)) {
            throw new IllegalArgumentException("Le nouveau mot de passe et la confirmation ne correspondent pas.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé avec l'ID : " + userId));

        user.setPassword(passwordEncoder.encode(nouveauMotDePasse));
        userRepository.save(user);
    }

    @Override
    public void deleteUserById(String userId) {

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {

            userRepository.deleteById(userId);
        } else {

            throw new UserNotFoundException("Utilisateur non trouvé avec l'ID : " + userId);
        }
    }


}



