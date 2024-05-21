package ba.unsa.etf.ppis.Controller;

import ba.unsa.etf.ppis.Model.Lecture;
import ba.unsa.etf.ppis.Model.UsersLecture;
import ba.unsa.etf.ppis.Service.UsersLectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users-lectures")
public class UsersLectureController {

    @Autowired
    private UsersLectureService usersLectureService;

    @PostMapping("/create")
    public ResponseEntity<UsersLecture> createUserLectureAssociation(@RequestParam String userId, @RequestParam String lectureId) {
        UsersLecture createdAssociation = usersLectureService.createUserLectureAssociation(userId, lectureId);
        return ResponseEntity.ok(createdAssociation);
    }

    @GetMapping("/user/{userId}/lectures")
    public ResponseEntity<List<Lecture>> getUsersLectures(@PathVariable String userId) {
        List<Lecture> userLectures = usersLectureService.getUsersLectures(userId);
        return ResponseEntity.ok(userLectures);
    }

    @GetMapping("/lecture/{lectureId}/users")
    public ResponseEntity<List<UsersLecture>> getLectureUserAssociations(@PathVariable String lectureId) {
        List<UsersLecture> lectureUserAssociations = usersLectureService.getLectureUserAssociations(lectureId);
        return ResponseEntity.ok(lectureUserAssociations);
    }

    @PutMapping("/update/{usersLectureId}")
    public ResponseEntity<UsersLecture> updateUserLectureAssociation(@PathVariable String usersLectureId, @RequestBody UsersLecture updatedUserLecture) {
        UsersLecture updatedAssociation = usersLectureService.updateUserLectureAssociation(usersLectureId, updatedUserLecture);
        if (updatedAssociation != null) {
            return ResponseEntity.ok(updatedAssociation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{usersLectureId}")
    public ResponseEntity<Void> deleteUserLectureAssociation(@PathVariable String usersLectureId) {
        usersLectureService.deleteUserLectureAssociation(usersLectureId);
        return ResponseEntity.noContent().build();
    }
}

