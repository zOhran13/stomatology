package ba.unsa.etf.ppis.Service;

import ba.unsa.etf.ppis.Model.UserEntity;
import ba.unsa.etf.ppis.Repository.UserRepository;
import ba.unsa.etf.ppis.dto.UserDto;
import ba.unsa.etf.ppis.exceptions.InvalidFormatException;
import ba.unsa.etf.ppis.exceptions.UserAlreadyExistsException;
import ba.unsa.etf.ppis.exceptions.UserNotFoundException;
import ba.unsa.etf.ppis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    @Autowired
    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto create(UserDto userDto) {
        String email = userDto.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("User with email '" + email + "' already exists.");
        }




        String password = userDto.getPasswordHash();
        if (isPasswordValid(password)) {
            throw new InvalidFormatException("Invalid password. Password must meet policy requirements.");
        }

        if (isEmailValid(email)) {
            throw new InvalidFormatException("Invalid email format.");
        }

        UserEntity user = UserMapper.mapToUser(userDto);
        user.setPasswordHash(passwordEncoder.encode(password));

        UserEntity savedUser = userRepository.save(user);

        return UserMapper.mapToUserDto(savedUser);
    }





    private boolean isPasswordValid(String password) {

        return password.length() < 8;
    }

    private boolean isEmailValid(String email) {

        return !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    @Override
    public List<UserDto> getAllUsers() {
        Iterable<UserEntity> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (UserEntity userEntity : users) {
            userDtos.add(UserMapper.mapToUserDto(userEntity));
        }
        return userDtos;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            return UserMapper.mapToUserDto(user);
        }
        throw new UserNotFoundException("User not found with email: " + email);
    }
    @Override
    public UserDto getUserById(String id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));

        return UserMapper.mapToUserDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String email) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            user.setType(userDto.getType());
            user.setName(userDto.getName());
            user.setPasswordHash(passwordEncoder.encode(userDto.getPasswordHash()));
            UserEntity updatedUser = userRepository.save(user);


            return UserMapper.mapToUserDto(updatedUser);
        }
        throw new UserNotFoundException("User not found with email: " + email);
    }

    @Override
    public void deleteUser(UserDto userDto) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(userDto.getEmail());
        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
        } else {
            throw new UserNotFoundException("User not found with email: " + userDto.getEmail());
        }
    }

    @Override
    public List<UserDto> searchUsersByName(String name) {
        List<UserEntity> users = userRepository.findByNameContainingIgnoreCase(name);
        return users.stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());
    }


}
