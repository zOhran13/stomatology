package ba.unsa.etf.ppis.Service;

import ba.unsa.etf.ppis.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto create(UserDto userDto);



    List<UserDto> getAllUsers();

    UserDto getUserByEmail(String email);

    UserDto getUserById(String id);

    UserDto updateUser(UserDto userDto, String password);

    void deleteUser(UserDto userDto);

    List<UserDto> searchUsersByName(String name);
}
