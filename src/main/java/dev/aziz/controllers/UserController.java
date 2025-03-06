package dev.aziz.controllers;

import dev.aziz.dtos.SelectPerformerDto;
import dev.aziz.dtos.UserDto;
import dev.aziz.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "User management")
public class UserController {

    private final UserService userService;

    @Operation(
            description = "Get all users.",
            summary = "Get all users"
    )
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(
            description = "Get user by id. Id should be given in PathVariable",
            summary = "Get user by id"
    )
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getOneUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(
            description = "Edit performer of the task. In SelectPerformerDto     oldPerformer id, newPerformer id and taskId should be given",
            summary = "Edit performer of the task"
    )
    @PatchMapping("/users/edit")
    public ResponseEntity<UserDto> selectPerformer(@RequestBody SelectPerformerDto selectPerformerDto) {
        return ResponseEntity.ok(userService.selectThePerformer(selectPerformerDto));
    }

}
