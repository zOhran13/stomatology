package ba.unsa.etf.ppis.Repository;

import ba.unsa.etf.ppis.Model.Role;
import ba.unsa.etf.ppis.Model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByNameContainingIgnoreCase(String name);

}
