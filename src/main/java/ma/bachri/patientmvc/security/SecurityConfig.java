package ma.bachri.patientmvc.security;

// Importation des classes et annotations nécessaires
import lombok.AllArgsConstructor;
import ma.bachri.patientmvc.security.services.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

/**
 * Configuration de la sécurité Spring Security.
 */
@Configuration // Indique que cette classe contient des beans de configuration.
@EnableWebSecurity // Active les fonctionnalités de sécurité web de Spring Security.
@EnableGlobalMethodSecurity(prePostEnabled = true) // Active la sécurité basée sur les annotations comme @PreAuthorize.
@AllArgsConstructor // Génère un constructeur avec tous les champs grâce à Lombok.
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder; // Bean pour encoder les mots de passe.
    private final UserDetailsServiceImpl userDetailsService; // Service pour gérer les utilisateurs.

    /**
     * Configuration pour gérer les utilisateurs via une base de données (JDBC).
     *
     * @param dataSource La source de données utilisée pour la gestion des utilisateurs.
     * @return           Un gestionnaire JDBC des utilisateurs.
     */
    //@Bean
    public JdbcUserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    /**
     * Configuration pour gérer les utilisateurs en mémoire.
     * Utile pour les environnements de développement ou les tests.
     *
     * @return Un gestionnaire en mémoire des utilisateurs.
     */
    //@Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        return new InMemoryUserDetailsManager(
                // Ajout de plusieurs utilisateurs avec leurs rôles et mots de passe encodés.
                User.withUsername("user1").password(passwordEncoder.encode("1234")).roles("USER").build(),
                User.withUsername("user2").password(passwordEncoder.encode("1234")).roles("USER").build(),
                User.withUsername("admin").password(passwordEncoder.encode("1234")).roles("USER", "ADMIN").build()
        );
    }

    /**
     * Configuration principale de la chaîne de filtres de sécurité.
     * Définit les règles pour l'accès et l'authentification des utilisateurs.
     *
     * @param httpSecurity L'objet HttpSecurity utilisé pour configurer la sécurité.
     * @return             La chaîne de filtres de sécurité configurée.
     * @throws Exception   Si une erreur survient lors de la configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // Configuration de la page de connexion personnalisée.
        httpSecurity.formLogin()
                .loginPage("/login") // URL de la page de connexion personnalisée.
                .defaultSuccessUrl("/") // URL redirigée après une connexion réussie.
                .permitAll(); // Permet l'accès à la page de connexion à tous les utilisateurs.

        // Autorisation pour les ressources publiques.
        httpSecurity.authorizeHttpRequests()
                .requestMatchers("/webjars/**", "/h2-console/**").permitAll();

        // Active l'option "Se souvenir de moi".
        httpSecurity.rememberMe();

        // Exemple de restrictions d'accès basées sur les rôles (commentées ici).
        // httpSecurity.authorizeHttpRequests().requestMatchers("/user/**").hasRole("USER");
        // httpSecurity.authorizeHttpRequests().requestMatchers("/admin/**").hasRole("ADMIN");

        // Tout autre requête nécessite une authentification.
        httpSecurity.authorizeHttpRequests().anyRequest().authenticated();

        // Gestion des exceptions pour les accès non autorisés.
        httpSecurity.exceptionHandling().accessDeniedPage("/notAuthorized");

        // Définit le service UserDetails pour la gestion des utilisateurs.
        httpSecurity.userDetailsService(userDetailsService);

        // Retourne la configuration de la chaîne de filtres.
        return httpSecurity.build();
    }
}
