package ba.unsa.etf.ppis.dto;

import ba.unsa.etf.ppis.Model.Role;
import lombok.*;

@Setter
@Getter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Role role;
    private String type;
    private String name;
    private String email;
    private String passwordHash;
    @Getter
    private String userId;
}
