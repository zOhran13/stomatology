package ba.unsa.etf.ppis.mapper;

import ba.unsa.etf.ppis.Model.*;
import ba.unsa.etf.ppis.dto.LectureDto;

import java.util.Optional;

public class LectureMapper {

    // Convert Lecture entity to LectureDto
    public static LectureDto toDto(Lecture lecture) {
        LectureDto dto = new LectureDto();
        dto.setName(lecture.getName());
        dto.setDetails(lecture.getDetails());
        dto.setLocation(lecture.getLocation());
        dto.setLink(lecture.getLink());
        return dto;
    }

    // Convert LectureDto to Lecture entity
    public static Lecture toEntity(LectureDto dto, Optional<TypeOfLecture> typeOfLecture) {
        Lecture lecture = new Lecture();
        lecture.setName(dto.getName());
        lecture.setDetails(dto.getDetails());
        lecture.setLocation(dto.getLocation());
        lecture.setLink(dto.getLink());
        if (typeOfLecture.isPresent()) {
            lecture.setTypeOfLecture(typeOfLecture.get());
        } else {
            throw new IllegalArgumentException("TypeOfLecture must be provided");
        }
        return lecture;
    }
}

