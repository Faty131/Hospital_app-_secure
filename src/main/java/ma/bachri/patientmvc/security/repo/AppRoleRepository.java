package ma.bachri.patientmvc.security.repo;

import ma.bachri.patientmvc.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, String> {
}
