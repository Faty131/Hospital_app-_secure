package ma.bachri.patientmvc.entities;

// Importation des annotations et classes nécessaires pour JPA, validation, et autres fonctionnalités

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * La classe Patient représente une entité JPA qui sera mappée à une table dans la base de données.
 * Elle contient les informations relatives à un patient.
 */
@Entity // Indique que cette classe est une entité JPA.
@Data // Génère automatiquement les getters, setters, equals, hashCode et toString grâce à Lombok.
@AllArgsConstructor // constructeur avec parameteres
@NoArgsConstructor // constructeur sans parameteres
public class Patient {

    @Id // Indique que ce champ est la clé primaire de l'entité.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // La clé primaire est générée automatiquement par la base de données.
    private Long id;

    @NotEmpty // Indique que le champ nom ne doit pas être vide.
    @Size(min = 4, max = 50)
    private String nom;

    @Temporal(TemporalType.DATE) //pour afficher juste la date
    @DateTimeFormat(pattern = "yyyy-MM-dd") // formater le format de la date
    private Date dateNaissance;

    private boolean malade; // Indique si le patient est malade ou non (true/false).

    @DecimalMin("100") // Définit la valeur minimale autorisée pour le champ score.
    private int score; // Représente le score du patient (par exemple, pour un indicateur médical).
}
