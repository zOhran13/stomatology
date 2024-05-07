package ba.unsa.etf.ppis.Repository;

import ba.unsa.etf.ppis.Model.Role;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface RoleRepository extends CrudRepository<Role, String> {
    boolean existsByName(String name);

}
