package ba.unsa.etf.ppis.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Table(name="type")
@Entity
public class TypeOfLecture {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "type_id", columnDefinition = "VARCHAR(64)")
    private String typeId;
    @Setter
    @Getter
    @Column(name = "type_name", columnDefinition = "VARCHAR(1024)")
    private String name;

}
