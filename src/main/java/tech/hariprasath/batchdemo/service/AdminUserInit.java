package tech.hariprasath.batchdemo.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tech.hariprasath.batchdemo.entity.User;
import tech.hariprasath.batchdemo.repository.UserRepository;
import tech.hariprasath.batchdemo.roles.Role;

@Component
public class AdminUserInit {

    @Bean
    public CommandLineRunner createAdminUser(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        return args -> {
            if (userRepository.findByName("admin").isEmpty()) {
                User admin = new User();
                admin.setName("admin");
                admin.setEmail("hariprasathk2023@gmail.com");
                admin.setPassword(passwordEncoder.encode("0311"));
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);
                System.out.println("Admin has been created");
            }
        };
    }
}
