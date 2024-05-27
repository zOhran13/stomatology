package ba.unsa.etf.ppis.Service;

import ba.unsa.etf.ppis.Model.Lecture;
import ba.unsa.etf.ppis.Model.TypeOfLecture;
import ba.unsa.etf.ppis.Repository.LectureRepository;
import ba.unsa.etf.ppis.Repository.TypeOfLectureRepository;
import ba.unsa.etf.ppis.dto.LectureDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ba.unsa.etf.ppis.mapper.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LectureService {
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private TypeOfLectureRepository typeOfLectureRepository;
    public Lecture createLectureCongress(LectureDto lecture) {
        Optional<TypeOfLecture> t=typeOfLectureRepository.findByName("congress");
        Lecture l=LectureMapper.toEntity(lecture, t);
        return lectureRepository.save(l);
    }
    public Lecture createLecture(LectureDto lecture) {
        Optional<TypeOfLecture> t=typeOfLectureRepository.findByName("lecture");
        Lecture l=LectureMapper.toEntity(lecture, t);
        return lectureRepository.save(l);
    }

    public Optional<Lecture> getLectureCongress(String lectureId) {
        Optional<Lecture> lectureOptional = lectureRepository.findById(lectureId);
        return lectureOptional.filter(lecture ->
                lecture.getTypeOfLecture() != null &&
                        "congress".equalsIgnoreCase(lecture.getTypeOfLecture().getName())
        );
    }
    public Optional<Lecture> getLecture(String lectureId) {
        Optional<Lecture> lectureOptional = lectureRepository.findById(lectureId);
        return lectureOptional.filter(lecture ->
                lecture.getTypeOfLecture() != null &&
                        "lecture".equalsIgnoreCase(lecture.getTypeOfLecture().getName())
        );
    }

    public List<Lecture> getAllCongressLectures() {
        List<Lecture> allLectures = lectureRepository.findAll();

        return allLectures.stream()
                .filter(lecture ->
                        lecture.getTypeOfLecture() != null &&
                                "congress".equalsIgnoreCase(lecture.getTypeOfLecture().getName())
                )
                .collect(Collectors.toList());
    }
    public List<Lecture> getAllLectures() {
        List<Lecture> allLectures = lectureRepository.findAll();

        return allLectures.stream()
                .filter(lecture ->
                        lecture.getTypeOfLecture() != null &&
                                "lecture".equalsIgnoreCase(lecture.getTypeOfLecture().getName())
                )
                .collect(Collectors.toList());
    }

    public void deleteLecture(String lectureId) {
        lectureRepository.deleteById(lectureId);
    }

    public List<Lecture> getAllLecturesForSpeaker(String dentistId) {
        return lectureRepository.getAllBySpeakerId(dentistId);
    }
}

