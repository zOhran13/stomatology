package ba.unsa.etf.ppis.Repository;

import ba.unsa.etf.ppis.Model.TypeOfLecture;
import ba.unsa.etf.ppis.Model.UsersLecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeOfLectureRepository extends JpaRepository<TypeOfLecture, String> {
    Optional<TypeOfLecture> findByName(String name);
}
