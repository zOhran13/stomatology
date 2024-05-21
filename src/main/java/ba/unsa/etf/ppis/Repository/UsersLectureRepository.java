package ba.unsa.etf.ppis.Repository;

import ba.unsa.etf.ppis.Model.Lecture;
import ba.unsa.etf.ppis.Model.User;
import ba.unsa.etf.ppis.Model.UsersLecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersLectureRepository extends JpaRepository<UsersLecture, String> {
    List<UsersLecture> findByUser(User userId);
    List<UsersLecture> findByLecture(Lecture lectureId);

}
