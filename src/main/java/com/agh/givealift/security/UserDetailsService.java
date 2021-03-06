package com.agh.givealift.security;

import com.agh.givealift.model.entity.GalUser;
import com.agh.givealift.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserService userService;

    @Autowired
    public UserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        GalUser user = userService.getUserByUsername(username).get();
        if (user == null) throw new UsernameNotFoundException(username + " not found");
        return new UserDetails(user);
    }
}