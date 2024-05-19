package ba.unsa.etf.ppis.dto.auth;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class LoginRequestDto {
    private String email;

    private String password;
}
