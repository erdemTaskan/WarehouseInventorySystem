package com.erdem.security;

import com.erdem.model.User;
import com.erdem.repository.IUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserRepository userRepository;

    public CustomUserDetailsService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optional=userRepository.findByUsername(username);

        if (optional.isEmpty()){
            throw new UsernameNotFoundException("User not found..."+username);
        }

        return new CustomUserDetails(optional.get());
    }
}
