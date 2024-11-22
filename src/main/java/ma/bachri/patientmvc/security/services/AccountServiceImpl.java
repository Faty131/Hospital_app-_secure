package ma.bachri.patientmvc.security.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import ma.bachri.patientmvc.security.entities.AppRole;
import ma.bachri.patientmvc.security.entities.AppUser;
import ma.bachri.patientmvc.security.repo.AppRoleRepository;
import ma.bachri.patientmvc.security.repo.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;
    @Override
    public AppUser addNewUser(String username, String password, String email, String confirmPassword) {
    AppUser appUser = appUserRepository.findByUsername(username);
    if (appUser != null) throw new RuntimeException("User already exists");
    if(!password.equals(confirmPassword)) throw new RuntimeException("Passwords do not match");
    appUser=AppUser.builder()
            .userId(UUID.randomUUID().toString())
            .username(username)
            .password(passwordEncoder.encode(password))
            .email(email)
            .build();
    AppUser savedAppUser = appUserRepository.save(appUser);
    return savedAppUser;
    }

    @Override
    public AppRole addNewRole(String role) {
        AppRole appRole = appRoleRepository.findById(role).orElse(null);

        if (appRole != null) throw new RuntimeException("This role already exists");
        appRole = AppRole.builder()
                .role(role)
                .build();
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String role) {
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRoles = appRoleRepository.findById(role).get();

        appUser.getRoles().add(appRoles);
        //appUserRepo.save(appUser);


    }

    @Override
    public void removeRoleFromUser(String username, String role) {
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRoles = appRoleRepository.findById(role).get();

        appUser.getRoles().remove(appRoles);

    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }


}
