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
        dto.setDate(lecture.getDate());
        dto.setLocation(lecture.getLocation());
        dto.setLink(lecture.getLink());
        dto.setSpeakerId(lecture.getSpeakerId());
        return dto;
    }

    // Convert LectureDto to Lecture entity
    public static Lecture toEntity(LectureDto dto, Optional<TypeOfLecture> typeOfLecture) {
        Lecture lecture = new Lecture();
        lecture.setName(dto.getName());
        lecture.setDate(dto.getDate());
        lecture.setDetails(dto.getDetails());
        lecture.setLocation(dto.getLocation());
        lecture.setLink(dto.getLink());
        lecture.setSpeakerId(dto.getSpeakerId());
        if (typeOfLecture.isPresent()) {
            lecture.setTypeOfLecture(typeOfLecture.get());
        } else {
            throw new IllegalArgumentException("TypeOfLecture must be provided");
        }
        return lecture;
    }
}

