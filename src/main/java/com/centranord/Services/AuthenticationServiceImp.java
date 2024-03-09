package com.centranord.Services;

import com.centranord.Config.JwtService;
import com.centranord.DTO.AuthenticationRequest;
import com.centranord.DTO.AuthenticationResponse;
import com.centranord.DTO.RegisterRequest;
import com.centranord.DTO.TokenType;
import com.centranord.Entity.Permission;
import com.centranord.Entity.Role;
import com.centranord.Entity.Token;
import com.centranord.Entity.User;
import com.centranord.Enum.ActionPermission;
import com.centranord.Repository.RoleRepository;
import com.centranord.Repository.TokenRepository;
import com.centranord.Repository.UserRepository;
import com.centranord.ServiceInterface.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {
    private final UserRepository repositoryUser;
    private final RoleRepository repositoryRole;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        Optional<Role> optionalRole = repositoryRole.findById(request.getRoleId());

        var user = User.builder()

                .lastName(request.getLastname())
                .firstname(request.getFirstname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(optionalRole.get())
                .address(request.getAddress())
                .governorate(request.getGovernorate())
                .status(request.isStatus())
                .build();

        var savedUser = repositoryUser.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (InternalAuthenticationServiceException e) {
            throw new RuntimeException("Erreur d'authentification : L'adresse e-mail ou le mot de passe est invalide.");
        } catch (AuthenticationException e) {
            if (e instanceof BadCredentialsException) {
                System.out.println("Password invalid");
                throw new RuntimeException("Adresse e-mail ou mot de passe invalide");
            } else {
                throw new RuntimeException("L'authentification a échoué : " + e.getMessage());
            }
        }
        Optional<User> userOptional = repositoryUser.findByEmail(request.getEmail());
        User user = userOptional.orElseThrow(() -> new RuntimeException("User not found"));
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        String userRole = user.getRole().getName();
        Set<Permission> permissions = user.getRole().getPermissions();
        List<String> permissionInfo = new ArrayList<>();
        List<String> resourceNames = new ArrayList<>();
        for (Permission permission : permissions) {
            String permissionString = permission.getResourceName() + " - " + permission.getAction();
            permissionInfo.add(permissionString);
            resourceNames.add(permission.getResourceName());
            System.out.println("Permission Info: " + permissionString);
        }

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .role(userRole)
                .permissions(permissionInfo)
                .resourceNames(resourceNames)
                .build();
    }



    @Override
    public void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    @Override
    public void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllByUserIdAndExpiredFalseOrRevokedFalse((user.getId()));
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repositoryUser.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }


}
