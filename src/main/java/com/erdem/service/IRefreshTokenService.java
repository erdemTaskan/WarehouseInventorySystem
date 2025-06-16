package com.erdem.service;

import com.erdem.model.RefreshToken;
import com.erdem.model.User;

import java.util.Optional;

public interface IRefreshTokenService {
    RefreshToken createRefreshToken(User user);

    Optional<RefreshToken>  getByToken(String refreshToken);

    RefreshToken validateRefreshToken(String token);

    void deleteToken(User user);
}
