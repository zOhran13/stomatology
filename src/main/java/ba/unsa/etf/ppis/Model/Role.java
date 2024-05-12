package ba.unsa.etf.ppis.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "role")
@Entity // This tells Hibernate to make a table out of this class
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "role_id", columnDefinition = "VARCHAR(64)")
    @Getter
    private String roleId;
    @Setter
    @Getter
    @Column(name = "role_name", columnDefinition = "VARCHAR(1024)")
    private String name;


    public Role(){}

    public Role(String name) {
        this.name = name;

    }



    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + roleId +
                ", name='" + name+ '\'' +
                '}';
    }
}
