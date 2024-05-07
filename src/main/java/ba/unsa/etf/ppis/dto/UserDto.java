package ba.unsa.etf.ppis.dto;

import lombok.*;

@Setter
@Getter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String type;
    private String name;
    private String email;
    private String passwordHash;
    @Getter
    private String userId;
}
