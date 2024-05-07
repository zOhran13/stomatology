package ba.unsa.etf.ppis.Model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor



@Table(name = "user")
@Entity // This tells Hibernate to make a table out of this class
public class UserEntity {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", columnDefinition = "VARCHAR(64)")
    private String userId;
    @Setter
    @Getter

    @ManyToOne
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
    private String passwordHash;

    public UserEntity() {

    }

    public UserEntity(String type, String email, String name, String passwordHash) {

        this.email = email;
        this.name = name;
        this.passwordHash = passwordHash;
        this.type = type;
    }



    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + userId +
                ", type=" +type+ '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                '}';
    }
}
