package com.zegline.sgrspring.service.security;

import com.zegline.sgrspring.model.security.SGRUser;
import com.zegline.sgrspring.repository.security.SGRUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SGRUserService implements UserDetailsService {

    @Autowired
    private SGRUserRepository ur;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SGRUser sgrUser = ur.findByUsername(username);

        if (sgrUser == null) throw new UsernameNotFoundException("user not found");

        return new User(
                sgrUser.getUsername(),
                sgrUser.getPassword(),
                new ArrayList<>()
        );
    }

    public void saveUser(SGRUser sgrUser){
        ur.save(sgrUser);
    }
}
