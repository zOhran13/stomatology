package ba.unsa.etf.ppis.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

public class LectureDto {
    private String lectureId;
    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private String details;
    @Setter
    @Getter
    private String location;
    @Setter
    @Getter
    private String link;


}


