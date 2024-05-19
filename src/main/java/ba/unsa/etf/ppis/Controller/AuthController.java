package ba.unsa.etf.ppis.Controller;

import ba.unsa.etf.ppis.Service.UserService;
import ba.unsa.etf.ppis.dto.auth.LoginRequestDto;
import ba.unsa.etf.ppis.dto.UserDto;
import ba.unsa.etf.ppis.dto.auth.LoginResponseDto;
import ba.unsa.etf.ppis.exceptions.UserNotFoundException;
import ba.unsa.etf.ppis.security.JwtTokenHelper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenHelper tokenHelper;

    @PostMapping(path = "/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
//        );
        var user = userService.getUserByEmail(loginRequestDto.getEmail());
        String token = tokenHelper.generateToken(user.getEmail());
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @PostMapping(path = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        UserDto createdUserDto = userService.create(userDto);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }
}
