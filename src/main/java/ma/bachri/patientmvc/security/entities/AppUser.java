package ma.bachri.patientmvc.security.entities;

// Importation des annotations nécessaires pour JPA et Lombok
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
représente une entité JPA qui gère les utilisateurs dans le système de sécurité.
 */
@Entity // Indique que cette classe est une entité JPA
@Data // Génère automatiquement les getters, setters grâce à Lombok.
@NoArgsConstructor // Génère un constructeur sans argument grâce à Lombok.
@AllArgsConstructor // Génère un constructeur avec tous les arguments grâce à Lombok.
@Builder // Permet de construire des objets AppUser.
public class AppUser {

    @Id // Indique que ce champ est la clé primaire de l'entité.
    private String userId;

    @Column(unique = true)
    private String username;
    private String password;

    private String email;

    @ManyToMany(fetch = FetchType.EAGER) //association entre deux entités où chaque élément
    // d'une entité peut être associé à plusieurs éléments d'une autre entité
    private List<AppRole> roles;
}
