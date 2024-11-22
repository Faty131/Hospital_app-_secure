package ma.bachri.patientmvc.security.services;

import lombok.AllArgsConstructor;
import ma.bachri.patientmvc.security.entities.AppUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor // Génère un constructeur avec parametre
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountService accountService; // pour gérer les utilisateurs et rôles.

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Charge l'utilisateur depuis le service AccountService.
        AppUser appUser = accountService.loadUserByUsername(username);

        // Si l'utilisateur n'existe pas, une exception est levée.
        if (appUser == null)
            throw new UsernameNotFoundException(String.format("User %s not found", username));

        // Récupère les rôles de l'utilisateur sous forme de tableau de chaînes de caractères.
        String[] roles = appUser.getRoles().stream()
                .map(role -> role.getRole())
                .toArray(String[]::new);

        // Crée un objet UserDetails contenant les informations de l'utilisateur.
        UserDetails userDetails = User.withUsername(appUser.getUsername())
                .password(appUser.getPassword()) // Mot de passe (devrait être haché).
                .roles(roles)
                .build();

        // Retourne les détails de l'utilisateur pour qu'ils soient utilisés par Spring Security.
        return userDetails;
    }
}
