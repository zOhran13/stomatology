package ba.unsa.etf.ppis.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Getter
    @Setter
    @Column(name = "date")
    private LocalDateTime date;
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
    @Setter
    @Getter
    @Column(name = "speaker_id", columnDefinition = "VARCHAR(64)")
    private String speakerId;

    // TODO Retrieve collection of all lecture attendants
    @Getter
    @Setter
    @JsonManagedReference
    @ManyToMany
    @JoinTable(
            name = "userslecture",
            joinColumns = @JoinColumn(name = "lecture_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> attendants = new HashSet<>();
}

