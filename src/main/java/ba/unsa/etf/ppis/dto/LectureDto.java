package ba.unsa.etf.ppis.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class LectureDto {
    private String lectureId;
    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private String details;
    @Getter
    @Setter
    private LocalDateTime date;
    @Setter
    @Getter
    private String location;
    @Setter
    @Getter
    private String link;


}


