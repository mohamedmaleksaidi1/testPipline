
/*
package com.centranord.Config;

import com.centranord.Entity.Role;
import com.centranord.Entity.User;
import com.centranord.Enum.ActionPermission;
import com.centranord.Repository.PermissionRepository;
import com.centranord.Entity.Permission;
import com.centranord.Repository.RoleRepository;
import com.centranord.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

@Configuration
public class DataInitializationConfig {

    @Component
    public class DataInitializer implements CommandLineRunner {
        private final PasswordEncoder passwordEncoder;
        private final UserRepository userRepository;
        private final RoleRepository roleRepository;
        private final PermissionRepository permissionRepository;

        public DataInitializer(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository, PermissionRepository permissionRepository) {
            this.passwordEncoder = passwordEncoder;
            this.userRepository = userRepository;
            this.roleRepository = roleRepository;
            this.permissionRepository = permissionRepository;
        }

        @Override
        public void run(String... args) {
            initializePermissions();
        }

        private void initializePermissions() {
            List<Permission> permissions = Arrays.asList(
                    new Permission(null, "Reclamation", ActionPermission.AJOUTER),
                    new Permission(null, "Reclamation", ActionPermission.SUPPRIMER),
                    new Permission(null, "Reclamation", ActionPermission.MODIFIER),
                    new Permission(null, "Reclamation", ActionPermission.LIRE),

                    new Permission(null, "Email", ActionPermission.AJOUTER),
                    new Permission(null, "Email", ActionPermission.SUPPRIMER),
                    new Permission(null, "Email", ActionPermission.MODIFIER),
                    new Permission(null, "Email", ActionPermission.LIRE),

                    new Permission(null, "siteweb", ActionPermission.AJOUTER),
                    new Permission(null, "siteweb", ActionPermission.SUPPRIMER),
                    new Permission(null, "siteweb", ActionPermission.MODIFIER),
                    new Permission(null, "siteweb", ActionPermission.LIRE),

                    new Permission(null, "Role", ActionPermission.AJOUTER),
                    new Permission(null, "Role", ActionPermission.SUPPRIMER),
                    new Permission(null, "Role", ActionPermission.MODIFIER),
                    new Permission(null, "Role", ActionPermission.LIRE),

                    new Permission(null, "user", ActionPermission.AJOUTER),
                    new Permission(null, "user", ActionPermission.SUPPRIMER),
                    new Permission(null, "user", ActionPermission.MODIFIER),
                    new Permission(null, "user", ActionPermission.LIRE),


                    new Permission(null, "Modifier_pwd", ActionPermission.MODIFIER_PASSWORD)
            );

            permissions.forEach(permission -> {
                if (permissionRepository.findByResourceNameAndAction(permission.getResourceName(), permission.getAction()) == null) {
                    permissionRepository.save(permission);
                }
            });
            Scanner scanner = new Scanner(System.in);
            System.out.println("Entrez le nom du rôle : ");
            String roleName = scanner.nextLine();

            Role role = new Role();
            role.setName(roleName);
            role.setName("ROLE_ADMIN");
            List<Permission> allPermissions = permissionRepository.findAll();

            role.setPermissions(new HashSet<>(allPermissions));

            roleRepository.save(role);
            System.out.println("Initialisation des données terminée.");

            User user = new User();
            user.setFirstname("mohamedmalek");
            user.setLastName("saidi");
            user.setEmail("maleksaidi492@gmail.com");
            user.setAddress("ariana");
            user.setGovernorate("Tunisie");
            user.setPassword(passwordEncoder.encode("111111111"));
            user.setStatus(true);
            user.setRole(role);
            userRepository.save(user);

            System.out.println("Initialisation des données terminée.");
        }
        }

    }


*/







