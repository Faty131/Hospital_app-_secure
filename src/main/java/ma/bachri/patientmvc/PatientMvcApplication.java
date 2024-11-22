package ma.bachri.patientmvc;

import ma.bachri.patientmvc.repositories.PatientRepository;
import ma.bachri.patientmvc.security.services.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

/**
 *
 * Classe principale de l'application PatientMVC.
 * Configure et lance l'application Spring Boot.
 */
@SpringBootApplication
public class PatientMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientMvcApplication.class, args);
    }

    /**
     * Bean de type CommandLineRunner pour initialiser des patients dans la base de données.
     * Utile lors du développement ou pour pré-remplir les données.
     *
     * @param patientRepository Le dépôt pour la gestion des patients.
     * @return                  Une instance de CommandLineRunner.
     */
    //@Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository) {
        return args -> {
            // Exemple de création et sauvegarde de patients dans la base de données.
            /*patientRepository.save(new Patient(null, "Faty", new Date(), false, 122));
            patientRepository.save(new Patient(null, "Jalila", new Date(), true, 400));
            patientRepository.save(new Patient(null, "Bouchra", new Date(), false, 120));
            patientRepository.save(new Patient(null, "Safaa", new Date(), true, 300));*/
        };
    }

    /**
     * Bean de type CommandLineRunner pour gérer les utilisateurs avec JdbcUserDetailsManager.
     * Permet d'ajouter des utilisateurs si inexistants.
     *
     * @param jdbcUserDetailsManager Le gestionnaire des utilisateurs basé sur JDBC.
     * @return                       Une instance de CommandLineRunner.
     */
    //@Bean
    CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager) {
        PasswordEncoder passwordEncoder = passwordEncoder();
        return args -> {
            // Vérifie et crée des utilisateurs dans la base de données.
            UserDetails u1 = jdbcUserDetailsManager.loadUserByUsername("user");
            if (u1 == null) {
                jdbcUserDetailsManager.createUser(
                        User.withUsername("user").password(passwordEncoder.encode("1234")).roles("USER").build()
                );
            }

            UserDetails u2 = jdbcUserDetailsManager.loadUserByUsername("user1");
            if (u2 == null) {
                jdbcUserDetailsManager.createUser(
                        User.withUsername("user1").password(passwordEncoder.encode("1234")).roles("USER").build()
                );
            }

            UserDetails u3 = jdbcUserDetailsManager.loadUserByUsername("admin1");
            if (u3 == null) {
                jdbcUserDetailsManager.createUser(
                        User.withUsername("admin1").password(passwordEncoder.encode("1234")).roles("USER", "ADMIN").build()
                );
            }
        };
    }

    /**
     * Bean de type CommandLineRunner pour initialiser les utilisateurs et leurs rôles via AccountService.
     *
     * @param accountService Le service de gestion des utilisateurs et des rôles.
     * @return               Une instance de CommandLineRunner.
     */
    //@Bean
    CommandLineRunner commandLineRunnerUserDetails(AccountService accountService) {
        return args -> {
            // Ajoute des rôles à la base de données.
            accountService.addNewRole("USER");
            accountService.addNewRole("ADMIN");

            // Ajoute des utilisateurs avec leurs informations.
            accountService.addNewUser("user1", "1234", "user1@gmail.com", "1234");
            accountService.addNewUser("user2", "1234", "user2@gmail.com", "1234");
            accountService.addNewUser("admin", "1234", "admin@gmail.com", "1234");

            // Attribue des rôles aux utilisateurs.
            accountService.addRoleToUser("user1", "USER");
            accountService.addRoleToUser("user2", "USER");
            accountService.addRoleToUser("admin", "USER");
            accountService.addRoleToUser("admin", "ADMIN");
        };
    }

    /**
     * Bean pour encoder les mots de passe en utilisant BCrypt.
     *
     * @return Un encodeur de mots de passe.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
