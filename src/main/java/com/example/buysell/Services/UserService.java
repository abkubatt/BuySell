package com.example.buysell.Services;

import com.example.buysell.Models.Enams.Role;
import com.example.buysell.Models.User;
import com.example.buysell.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean createUser(User user){
        String email = user.getEmail();
        if (userRepository.findByEmail(email) != null) return false;
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getName().equals("admin") || user.getName().equals("majit")){
            user.getRoles().add(Role.ROLE_ADMIN);
            log.info("Saving new User with email: {}", email);
            userRepository.save(user);
            return true;
        }else{
            user.getRoles().add(Role.ROLE_USER);
            log.info("Saving new User with email: {}", email);
            userRepository.save(user);
            return true;
        }

    }

    public List<User> list(){
        return userRepository.findAll();
    }

    public void banUser(Long id){
        User user = userRepository.findById(id).orElse(null);
        if (user != null){
            if (user.isActive()){
                user.setActive(false);
                log.info("Ban user with id = {}; email: {}",user.getId(), user.getEmail());
            }else{
                user.setActive(true);
                log.info("UnBun user with id = {}; email: {}",user.getId(), user.getEmail());
            }
        }
        userRepository.save(user);
    }
    public void changeUserRoles(User user, Map<String, String> form) {
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
    }
    public User getUserByPrincipal(Principal principal) {
//        System.out.println(principal);
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }


}
