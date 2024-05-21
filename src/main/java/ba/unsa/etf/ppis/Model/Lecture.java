package ba.unsa.etf.ppis.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "lecture")
@Entity
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    @Column(name = "lecture_id", columnDefinition = "VARCHAR(64)")
    private String lectureId;
    @Setter
    @Getter
    @Column(name = "lecture_name", columnDefinition = "VARCHAR(64)")
    private String name;
    @Setter
    @Getter
    @Column(name = "details", columnDefinition = "VARCHAR(64)")
    private String details;
    @Setter
    @Getter
    @Column(name = "location", columnDefinition = "VARCHAR(64)")
    private String location;
    @Setter
    @Getter
    @Column(name = "link", columnDefinition = "VARCHAR(128)")
    private String link;
    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "lecturetype_id", referencedColumnName = "type_id")
    private TypeOfLecture typeOfLecture;

}

