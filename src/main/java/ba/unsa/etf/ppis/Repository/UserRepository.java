package ba.unsa.etf.ppis.Repository;

import ba.unsa.etf.ppis.Model.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, String> {

    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);

    List<UserEntity> findByNameContainingIgnoreCase(String name);

}
