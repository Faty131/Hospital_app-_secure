package ma.bachri.patientmvc.security.services;

// Importation des annotations et classes nécessaires
import lombok.AllArgsConstructor;
import ma.bachri.patientmvc.security.entities.AppUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implémentation de l'interface UserDetailsService.
 * Cette classe est utilisée par Spring Security pour charger les détails des utilisateurs
 * nécessaires à l'authentification et à l'autorisation.
 */
@Service // Indique que cette classe est un composant Spring de type service.
@AllArgsConstructor // Génère un constructeur avec tous les champs grâce à Lombok.
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountService accountService; // Service pour gérer les utilisateurs et rôles.

    /**
     * Charge un utilisateur à partir de son nom d'utilisateur.
     * Cette méthode est utilisée par Spring Security pour l'authentification.
     *
     * @param username Nom d'utilisateur.
     * @return         Un objet UserDetails contenant les informations nécessaires à Spring Security.
     * @throws UsernameNotFoundException Si l'utilisateur n'est pas trouvé.
     */
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
                .roles(roles) // Rôles associés à l'utilisateur.
                .build();

        // Retourne les détails de l'utilisateur pour qu'ils soient utilisés par Spring Security.
        return userDetails;
    }
}
