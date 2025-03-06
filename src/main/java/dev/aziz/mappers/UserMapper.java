package dev.aziz.mappers;

import dev.aziz.dtos.SignUpDto;
import dev.aziz.dtos.UserDto;
import dev.aziz.entities.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

    public UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }

        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .isEnabled(user.getIsEnabled())
                .roles(user.getRoles())
                .tasks(user.getTasks())
                .build();
    }

    public User toUser(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        return User.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .isEnabled(userDto.getIsEnabled())
                .roles(userDto.getRoles())
                .tasks(userDto.getTasks())
                .build();
    }

    public List<UserDto> usersToUserDtos(List<User> users) {
        if (users == null) {
            return null;
        }

        return users.stream()
                .map(this::toUserDto)
                .collect(Collectors.toList());
    }

    public User signUpToUser(SignUpDto signUpDto) {
        if (signUpDto == null) {
            return null;
        }

        return User.builder()
                .firstName(signUpDto.getFirstName())
                .lastName(signUpDto.getLastName())
                .email(signUpDto.getEmail())
                .roles(Set.copyOf(signUpDto.getRoles()))
                .build();
    }
}