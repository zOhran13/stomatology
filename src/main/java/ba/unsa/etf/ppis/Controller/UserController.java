package ba.unsa.etf.ppis.Controller;

import ba.unsa.etf.ppis.Repository.UserRepository;
import ba.unsa.etf.ppis.Service.UserService;
import ba.unsa.etf.ppis.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
@AllArgsConstructor
public class UserController {
    @Autowired
    private UserRepository userRepository;
    //
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;


    @PostMapping(path = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        userDto.setPasswordHash(passwordEncoder.encode(userDto.getPasswordHash()));
        UserDto createdUserDto = userService.create(userDto);


        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    @GetMapping("/userId/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }


    @GetMapping(path = "/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        UserDto user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }


    @PutMapping("/user/{email}")
    public ResponseEntity<UserDto> updateUserByEmail(@PathVariable String email, @RequestBody UserDto userDto) {
        UserDto updatedUserDto = userService.updateUser(userDto, email);

        return ResponseEntity.ok(updatedUserDto);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUserByEmail(@RequestBody String email) {

        userService.deleteUser(email);
        return ResponseEntity.noContent().build(); // User deleted successfully

    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> searchUsersByName(@RequestParam String name) {
        List<UserDto> users = userService.searchUsersByName(name);
        return ResponseEntity.ok(users);
    }
}