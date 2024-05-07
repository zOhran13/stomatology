package ba.unsa.etf.ppis.mapper;

import ba.unsa.etf.ppis.Model.UserEntity;
import ba.unsa.etf.ppis.dto.UserDto;

public class UserMapper {

    // Convert User JPA Entity into UserDto
    public static UserDto mapToUserDto(UserEntity user){
        UserDto userDto = new UserDto(
                user.getType(),
                user.getName(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getUserId()
        );
        return userDto;
    }

    // Convert UserDto into User JPA Entity
    public static UserEntity mapToUser(UserDto userDto){
        UserEntity user = new UserEntity(
                userDto.getType(),
                userDto.getName(),
                userDto.getEmail(),
                userDto.getPasswordHash()
        );
        return user;
    }
}