package com.famipam.security.config;

import com.famipam.security.entity.Role;
import com.famipam.security.entity.User;
import com.famipam.security.entity.View;
import com.famipam.security.repository.RoleRepository;
import com.famipam.security.repository.UserRepository;
import com.famipam.security.repository.ViewRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class AppInitializer implements ApplicationRunner {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ViewRepository viewRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("### APP INITIALIZER RUN");
        buildViewData();
        buildRoleData();
        buildUserData();
    }

    private void buildViewData() throws IOException {
        System.out.println("## Build View Data");
        if (viewRepository.count()>0) return;

        InputStream inputStream = getClass().getResourceAsStream("/json/views.json");
        List<View> views = objectMapper.readValue(inputStream, new TypeReference<>() {
        });

        System.out.println(Arrays.toString(views.toArray()));
        viewRepository.saveAll(views);
    }

    private void buildRoleData() throws IOException {
        System.out.println("## Build Role Data");
        if (roleRepository.count() > 0) return;

        InputStream inputStream = getClass().getResourceAsStream("/json/roles.json");
        List<Role> roles = objectMapper.readValue(inputStream, new TypeReference<>() {
        });
        roleRepository.saveAll(roles);
    }

    private void buildUserData() throws IOException {
        System.out.println("## Build User Data");
        if (userRepository.count() > 0) return;

        InputStream inputStream = getClass().getResourceAsStream("/json/users.json");
        List<User> users = objectMapper.readValue(inputStream, new TypeReference<>() {
        });
        for (User user : users) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
    }

}
