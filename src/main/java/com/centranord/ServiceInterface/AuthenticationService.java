package com.centranord.ServiceInterface;

import com.centranord.DTO.AuthenticationRequest;
import com.centranord.DTO.AuthenticationResponse;
import com.centranord.DTO.RegisterRequest;
import com.centranord.Entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.Date;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    void saveUserToken(User user, String jwtToken);
    void revokeAllUserTokens(User user);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
