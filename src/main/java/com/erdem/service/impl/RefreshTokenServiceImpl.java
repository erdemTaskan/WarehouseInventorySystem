package com.erdem.service.impl;

import com.erdem.exception.InvalidRefreshTokenException;
import com.erdem.model.RefreshToken;
import com.erdem.model.User;
import com.erdem.repository.IRefreshTokenRepository;
import com.erdem.service.IRefreshTokenService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class RefreshTokenServiceImpl implements IRefreshTokenService {

    private final IRefreshTokenRepository refreshTokenRepository;

    public RefreshTokenServiceImpl(IRefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public RefreshToken createRefreshToken(User user) {

        refreshTokenRepository.deleteByUser(user);

        RefreshToken refreshToken=new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpireDate(Instant.now().plusSeconds(60*60*4));
        refreshToken.setToken(UUID.randomUUID().toString());

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public Optional<RefreshToken> getByToken(String refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken);
    }

    @Override
    public RefreshToken validateRefreshToken(String token) {
        RefreshToken refreshToken= refreshTokenRepository.findByToken(token).orElseThrow(InvalidRefreshTokenException::new);

        if (refreshToken.getExpireDate().isBefore(Instant.now())){
            refreshTokenRepository.delete(refreshToken);
            throw new InvalidRefreshTokenException();
        }
        return refreshToken;
    }

    @Override
    public void deleteToken(User user) {
        refreshTokenRepository.deleteByUser(user);

    }
}
