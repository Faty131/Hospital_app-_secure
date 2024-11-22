package ma.bachri.patientmvc.security.entities;

// Importation des annotations nécessaires pour JPA et Lombok
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * La classe AppRole représente une entité JPA utilisée pour définir les rôles dans le système de sécurité.
 * Chaque rôle est unique et identifié par son nom.
 */
@Entity // Indique que cette classe est une entité JPA, mappée à une table dans la base de données.
@Data // Génère automatiquement les getters, setters, equals, hashCode, et toString grâce à Lombok.
@NoArgsConstructor // Génère un constructeur sans argument grâce à Lombok.
@AllArgsConstructor // Génère un constructeur avec tous les arguments grâce à Lombok.
@Builder // Permet de construire des objets AppRole en utilisant un design pattern builder.
public class AppRole {

    @Id // Indique que le champ `role` est la clé primaire de l'entité.
    private String role; // Nom du rôle (par exemple : "ADMIN", "USER", etc.), qui sert d'identifiant unique.
}
