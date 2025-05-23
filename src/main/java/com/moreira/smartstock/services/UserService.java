package com.moreira.smartstock.services;

import com.moreira.smartstock.entities.Role;
import com.moreira.smartstock.entities.User;
import com.moreira.smartstock.projections.UserDetailsProjection;
import com.moreira.smartstock.repositories.UserRepository;
import com.moreira.smartstock.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserUtil userUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> result = repository.searchUserByUsername(username);

        if (result.isEmpty()) throw new UsernameNotFoundException("Usuário não encontrado");

        User user = new User();
        user.setEmail(result.get(0).getUsername());
        user.setPassword(result.get(0).getPassword());

        for (UserDetailsProjection projection : result) {
            user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
        }

        return user;
    }

    @Transactional(readOnly = true)
    public User getUserLogged() {
        try {
            String username = userUtil.getLoggedUsername();
            return repository.findByEmail(username).get();
        } catch (Exception e) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
    }
}
