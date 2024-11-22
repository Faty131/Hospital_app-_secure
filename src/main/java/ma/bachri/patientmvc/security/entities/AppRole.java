package ma.bachri.patientmvc.security.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity // Indique que cette classe est une entité JPA, mappée à une table dans la base de données.
@Data // Génère automatiquement les getters, setters grâce à Lombok.
@NoArgsConstructor // Génère un constructeur sans argument grâce à Lombok.
@AllArgsConstructor // Génère un constructeur avec tous les arguments grâce à Lombok.
@Builder // Permet de construire des objets AppRole.
public class AppRole {

    @Id // Indique que le champ `role` est la clé primaire de l'entité.
    private String role;
}
