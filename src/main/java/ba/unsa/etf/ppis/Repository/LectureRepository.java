package ba.unsa.etf.ppis.Repository;

import ba.unsa.etf.ppis.Model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, String> {
    public List<Lecture> getAllBySpeakerId(String dentistId);
}

