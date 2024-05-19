package ba.unsa.etf.ppis.mapper;

import ba.unsa.etf.ppis.Model.User;
import ba.unsa.etf.ppis.Repository.RoleRepository;
import ba.unsa.etf.ppis.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private static RoleRepository roleRepository;

    // Convert User JPA Entity into UserDto
    public UserMapper(RoleRepository roleService){
        roleRepository = roleService;
    }
    public static UserDto mapToUserDto(User user){
        String roleId = user.getRole() != null ? user.getRole().getRoleId() : null;
        UserDto userDto = new UserDto(
               roleId,
                user.getType(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getUserId()
        );
        return userDto;
    }

    // Convert UserDto into User JPA Entity
    public User mapToUser(UserDto userDto){
        User user = new User(
                roleRepository.findById(userDto.getRoleId()).orElse(null),
                userDto.getType(),
                userDto.getEmail(),
                userDto.getName(),
                userDto.getPassword()
        );
        return user;
    }
}
