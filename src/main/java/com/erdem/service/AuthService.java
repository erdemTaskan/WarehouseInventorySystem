package com.erdem.service;

import com.erdem.dto.AuthResponse;
import com.erdem.dto.LoginRequest;
import com.erdem.dto.RegisterRequest;
import com.erdem.exception.InvalidRefreshTokenException;
import com.erdem.jwt.JwtUtils;
import com.erdem.model.RefreshToken;
import com.erdem.model.User;
import com.erdem.repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
        private static final Logger logger= LoggerFactory.getLogger(AuthService.class);

        private final IUserRepository userRepository;
        private final JwtUtils jwtUtils;
        private final AuthenticationManager authenticationManager;
        private final PasswordEncoder passwordEncoder;
        private final IRefreshTokenService refreshTokenService;

    public AuthService(IUserRepository userRepository, JwtUtils jwtUtils, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, IRefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
    }

    public AuthResponse register(RegisterRequest request){

        User user=new User();

        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        String accessToken= jwtUtils.generateToken(user.getUsername());
        RefreshToken refreshToken= refreshTokenService.createRefreshToken(user);

        return new AuthResponse(accessToken,refreshToken.getToken());

    }

    public AuthResponse login(LoginRequest request){
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));

        String accessToken= jwtUtils.generateToken(request.getUsername());

        User user= userRepository.findByUsername(request.getUsername()).orElseThrow(()->new UsernameNotFoundException("User not found"));


        RefreshToken refreshToken= refreshTokenService.createRefreshToken(user);

        return new AuthResponse(accessToken,refreshToken.getToken());
    }
    public AuthResponse refresh(String refreshTokenStr){
        RefreshToken refreshToken=refreshTokenService.validateRefreshToken(refreshTokenStr);

        String newAccessToken= jwtUtils.generateToken(refreshToken.getUser().getUsername());
        return new AuthResponse(newAccessToken,refreshToken.getToken());
    }
    public void logout(String refreshToken){
        RefreshToken token= refreshTokenService.getByToken(refreshToken).orElseThrow(InvalidRefreshTokenException::new);
        refreshTokenService.deleteToken(token.getUser());

    }



}
