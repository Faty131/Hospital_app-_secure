package ma.bachri.patientmvc.security.entities;

// Importation des annotations nécessaires pour JPA et Lombok
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * La classe AppUser représente une entité JPA qui gère les utilisateurs dans le système de sécurité.
 * Chaque utilisateur possède des informations telles qu'un identifiant, un nom d'utilisateur, un mot de passe, un email,
 * et une liste de rôles.
 */
@Entity // Indique que cette classe est une entité JPA, mappée à une table dans la base de données.
@Data // Génère automatiquement les getters, setters, equals, hashCode, et toString grâce à Lombok.
@NoArgsConstructor // Génère un constructeur sans argument grâce à Lombok.
@AllArgsConstructor // Génère un constructeur avec tous les arguments grâce à Lombok.
@Builder // Permet de construire des objets AppUser en utilisant un design pattern builder.
public class AppUser {

    @Id // Indique que ce champ est la clé primaire de l'entité.
    private String userId; // Identifiant unique de l'utilisateur.

    @Column(unique = true) // Assure que le champ `username` est unique dans la base de données.
    private String username; // Nom d'utilisateur pour l'authentification.

    private String password; // Mot de passe de l'utilisateur (doit être stocké de manière sécurisée, comme haché).

    private String email; // Adresse email de l'utilisateur.

    @ManyToMany(fetch = FetchType.EAGER) // Relation Many-to-Many avec la classe `AppRole`.
    private List<AppRole> roles; // Liste des rôles associés à l'utilisateur.
}
