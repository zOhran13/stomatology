package ba.unsa.etf.ppis.mapper;

import ba.unsa.etf.ppis.Model.UserEntity;
import ba.unsa.etf.ppis.Repository.RoleRepository;
import ba.unsa.etf.ppis.Service.RoleServiceImpl;
import ba.unsa.etf.ppis.Service.UserService;
import ba.unsa.etf.ppis.dto.UserDto;
import ba.unsa.etf.ppis.mapper.RoleMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private static RoleRepository roleRepository;

    // Convert User JPA Entity into UserDto
    public UserMapper(RoleRepository roleService){
        roleRepository = roleService;
    }
    public static UserDto mapToUserDto(UserEntity user){
        String roleId = user.getRole() != null ? user.getRole().getRoleId() : null;
        UserDto userDto = new UserDto(
               roleId,
                user.getType(),
                user.getEmail(),
                user.getName(),
                user.getPasswordHash(),
                user.getUserId()
        );
        return userDto;
    }

    // Convert UserDto into User JPA Entity
    public UserEntity mapToUser(UserDto userDto){
        UserEntity user = new UserEntity(
                roleRepository.findById(userDto.getRoleId()).orElse(null),
                userDto.getType(),
                userDto.getEmail(),
                userDto.getName(),
                userDto.getPasswordHash()
        );
        return user;
    }
}
