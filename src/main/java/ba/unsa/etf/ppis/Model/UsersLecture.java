package ba.unsa.etf.ppis.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.api.client.util.DateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "userslecture")
public class UsersLecture {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "userslecture_id", columnDefinition = "VARCHAR(64)")
    private String userslectureId;

    @JsonIgnore
    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "lecture_id", referencedColumnName = "lecture_id")
    private Lecture lecture;

    @JsonIgnore
    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Getter
    @Setter
    @Column(name ="date_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime dateTime;
}