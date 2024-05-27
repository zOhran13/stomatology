package ba.unsa.etf.ppis.Controller;

import ba.unsa.etf.ppis.Service.UserService;
import ba.unsa.etf.ppis.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;


@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("")
    public ResponseEntity<UserDto> getUserById(@RequestParam String id) {
        UserDto user = userService.getUserById(id);
        return ok(user);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        UserDto user = userService.getUserByEmail(email);
        return ok(user);
    }


    @PutMapping("/{email}")
    public ResponseEntity<UserDto> updateUserByEmail(@PathVariable String email, @RequestBody UserDto userDto) {
        UserDto updatedUserDto = userService.updateUser(userDto, email);

        return ok(updatedUserDto);
    }


    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUserByEmail(@RequestParam String email) {

        userService.deleteUser(email);
        return ResponseEntity.noContent().build(); // User deleted successfully

    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> searchUsersByName(@RequestParam String name) {
        List<UserDto> users = userService.searchUsersByName(name);
        return ok(users);
    }
}