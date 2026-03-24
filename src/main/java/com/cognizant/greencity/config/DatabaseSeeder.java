package com.cognizant.greencity.config;

import com.cognizant.greencity.entity.User;
import com.cognizant.greencity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            try {
                String commonPassword = passwordEncoder.encode("Password123");

                // 1. System Administrators
                createUser("Admin User", "admin@greencity.com", "ADMIN", commonPassword);

                // 2. Compliance Officers
                createUser("Compliance Officer Sarah", "compliance1@greencity.com", "COMPLIANCE", commonPassword);
                createUser("Compliance Officer James", "compliance2@greencity.com", "COMPLIANCE", commonPassword);

                // 3. Auditors
                createUser("Auditor David", "auditor1@greencity.com", "AUDITOR", commonPassword);
                createUser("Auditor Sophia", "auditor2@greencity.com", "AUDITOR", commonPassword);

                // 4. City Planners
                createUser("Planner Mike", "planner1@greencity.com", "PLANNER", commonPassword);
                createUser("Planner Elena", "planner2@greencity.com", "PLANNER", commonPassword);

                // 5. Citizens
                createUser("Citizen Jane", "jane@citizen.com", "CITIZEN", commonPassword);

            } catch (Exception e) {
                throw e;
            }
        }
    }

    private void createUser(String name, String email, String role, String encodedPassword) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRole(role);
        user.setPasswordHash(encodedPassword);
        user.setStatus("ACTIVE");
        user.setPhone("1234567890");

        userRepository.saveAndFlush(user);
    }
}