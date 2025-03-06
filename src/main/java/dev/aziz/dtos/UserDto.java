package dev.aziz.dtos;

import dev.aziz.entities.Role;
import dev.aziz.entities.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean isEnabled;
    private String token;
    private Set<Role> roles = new HashSet<>();
    private List<Task> tasks = new ArrayList<>();

}
