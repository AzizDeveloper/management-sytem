package dev.aziz.dtos;

import dev.aziz.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpDto {

    @NotBlank(message = "Firstname should not be empty")
    private String firstName;

    @NotBlank(message = "Lastname should not be empty")
    private String lastName;

    @Email
    @NotBlank(message = "Email should not be empty")
    private String email;

    @NotEmpty(message = "Password should not be empty")
    private char[] password;

    @NotEmpty(message = "Role should not be empty")
    private List<Role> roles;

}
