package com.scm.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scm.entity.User;
import com.scm.repo.UserRepo;

@Service
public class CustomUserDetailsService  implements UserDetailsService {
  
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          User user = userRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not found "));
          String password = user.getPassword();
          System.out.println(password);

          return org.springframework.security.core.userdetails.User.builder()
          .username(user.getEmail())
          .password(user.getPassword())
          .disabled(!user.isEnabled())  
        //   .accountLocked(!user.isAccountNonLocked())
        //   .accountExpired(!user.isAccountNonExpired())
        //   .credentialsExpired(!user.isCredentialsNonExpired())
          .authorities(user.getAuthorities())
          .build();
    }

}

