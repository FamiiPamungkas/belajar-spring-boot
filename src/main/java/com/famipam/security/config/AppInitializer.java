package com.famipam.security.config;

import com.famipam.security.entity.Role;
import com.famipam.security.entity.User;
import com.famipam.security.entity.Menu;
import com.famipam.security.repository.RoleRepository;
import com.famipam.security.repository.UserRepository;
import com.famipam.security.repository.MenuRepository;
import com.famipam.security.util.DateUtils;
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
    private final MenuRepository menuRepository;
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
        if (menuRepository.count() > 0) return;

        InputStream inputStream = getClass().getResourceAsStream("/json/menus.json");
        List<Menu> menus = objectMapper.readValue(inputStream, new TypeReference<>() {
        });

        System.out.println(Arrays.toString(menus.toArray()));
        menuRepository.saveAll(menus);
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
            user.setPassword(passwordEncoder.encode(DateUtils.formatDefaultPassword(user.getBirthdate())));
            userRepository.save(user);
        }
    }

}
