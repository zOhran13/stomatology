package ba.unsa.etf.ppis.Repository;

import ba.unsa.etf.ppis.Model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, String> {
}

