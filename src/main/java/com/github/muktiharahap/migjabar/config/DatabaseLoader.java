package com.github.muktiharahap.migjabar.config;

import com.github.muktiharahap.migjabar.repository.RoleRepository;
import com.github.muktiharahap.migjabar.domain.Role;
import com.github.muktiharahap.migjabar.domain.User;
import com.github.muktiharahap.migjabar.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * @author  on 10/9/2017.
 */
@Component
@Transactional
public class DatabaseLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DatabaseLoader.class);

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public DatabaseLoader(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Role role = this.roleRepository.findByRole(AuthoritiesConstants.ADMIN);
        Role role1 = this.roleRepository.findByRole(AuthoritiesConstants.USER);

        if (role == null) {
            role = new Role();
            role.setRole(AuthoritiesConstants.ADMIN);
            this.roleRepository.save(role);
        }

        if (role1 == null) {
            role = new Role();
            role.setRole(AuthoritiesConstants.USER);
            this.roleRepository.save(role);
        }

        User user = this.userRepository.findByEmail("admin@migjabar.com");
        if (user == null) {
            role = this.roleRepository.findOneByRole(AuthoritiesConstants.ADMIN);
            role1 = this.roleRepository.findOneByRole(AuthoritiesConstants.USER);
            Set<Role> roles = new HashSet<>();
            User newUser = new User();
            newUser.setEmail("admin@migjabar.com");
            newUser.setLogin("admin");
            newUser.setEmei("");
            newUser.setNotel("");
            newUser.setNik("admin");
            newUser.setPassword("admin");
            newUser.setFirstName("Admin");
            newUser.setLastName("Administrator");
            roles.add(role);
            roles.add(role1);
            newUser.setRoles(roles);
            this.userRepository.save(newUser);
        }
    }
}
