package ba.unsa.etf.ppis.Controller;

import ba.unsa.etf.ppis.Model.Lecture;
import ba.unsa.etf.ppis.Service.LectureService;
import ba.unsa.etf.ppis.dto.LectureDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lectures")
public class LectureController {

    @Autowired
    private LectureService lectureService;

    @PostMapping("/createCongress")
    public ResponseEntity<Lecture> createCongressLecture(@RequestBody LectureDto lectureDto) {
        Lecture createdLecture = lectureService.createLectureCongress(lectureDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLecture);
    }

    @PostMapping("/create")
    public ResponseEntity<Lecture> createLecture(@RequestBody LectureDto lectureDto) {
        Lecture createdLecture = lectureService.createLecture(lectureDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLecture);
    }

    @GetMapping("/congress/{lectureId}")
    public ResponseEntity<Lecture> getCongressLecture(@PathVariable String lectureId) {
        Optional<Lecture> lectureOptional = lectureService.getLectureCongress(lectureId);
        return lectureOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{lectureId}")
    public ResponseEntity<Lecture> getLecture(@PathVariable String lectureId) {
        Optional<Lecture> lectureOptional = lectureService.getLecture(lectureId);
        return lectureOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/allCongress")
    public ResponseEntity<List<Lecture>> getAllCongressLectures() {
        List<Lecture> congressLectures = lectureService.getAllCongressLectures();
        return ResponseEntity.ok(congressLectures);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Lecture>> getAllLectures() {
        List<Lecture> allLectures = lectureService.getAllLectures();
        return ResponseEntity.ok(allLectures);
    }

    @GetMapping("/lecture")
    public ResponseEntity<List<Lecture>> getAllLecturesForSpeaker(@RequestParam String speakerId) {
        var lectures = lectureService.getAllLecturesForSpeaker(speakerId);
        return ResponseEntity.ok(lectures);
    }

    @DeleteMapping("/{lectureId}")
    public ResponseEntity<Void> deleteLecture(@PathVariable String lectureId) {
        lectureService.deleteLecture(lectureId);
        return ResponseEntity.noContent().build();
    }
}
