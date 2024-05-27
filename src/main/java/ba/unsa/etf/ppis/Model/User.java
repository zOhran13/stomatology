package ba.unsa.etf.ppis.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor



@Table(name = "user")
@Entity // This tells Hibernate to make a table out of this class
public class User {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", columnDefinition = "VARCHAR(64)")
    private String userId;
    @Setter
    @Getter

    @OneToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private Role role;

    @Setter
    @Getter
    @Column(name = "user_type", columnDefinition = "VARCHAR(64)")
    private String type;

    @Setter
    @Getter
    @Column(name = "user_name", columnDefinition = "VARCHAR(1024)")
    private String name;

    @Getter
    @Setter
    @Column(name = "email", columnDefinition = "VARCHAR(256)")
    private String email;
    @Setter
    @Getter
    @Column(name = "passwordHash", columnDefinition = "VARCHAR(256)")
    private String password;

    @JsonBackReference
    @ManyToMany(mappedBy = "attendants")
    private Set<Lecture> lectures = new HashSet<>();

    public User() {

    }

    public User(Role role, String type, String email, String name, String password) {
        this.role= role;
        this.email = email;
        this.name = name;
        this.password = password;
        this.type = type;
    }



    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + userId +
                ", type=" +type+ '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='" + password + '\'' +
                '}';
    }
}
