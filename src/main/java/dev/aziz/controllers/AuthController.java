package dev.aziz.controllers;

import dev.aziz.config.security.UserAuthenticationProvider;
import dev.aziz.dtos.CredentialsDto;
import dev.aziz.dtos.ResponseMessage;
import dev.aziz.dtos.SignUpDto;
import dev.aziz.dtos.UserDto;
import dev.aziz.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@Tag(name = "Authentication")
public class AuthController {

    private final UserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @Operation(
            description = "Post endpoint for login.",
            summary = "Login post endpoint"
    )
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid CredentialsDto credentialsDto) {
        UserDto userDto = userService.login(credentialsDto);
        userDto.setToken(userAuthenticationProvider.createToken(userDto));
        return ResponseEntity.ok(userDto);
    }

    @Operation(
            description = "Post endpoint for authentication. Requires SignUpDto with validations",
            summary = "Authentication post endpoint"
    )
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid SignUpDto user) {
        UserDto createdUser = userService.register(user);
        createdUser.setToken(userAuthenticationProvider.createToken(createdUser));
        return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
    }

    @Operation(
            description = "Get endpoint for verification. Requires activationCode in RequestParam",
            summary = "Verification get endpoint"
    )
    @GetMapping("/verify")
    public ResponseEntity<?> confirmUserAccount(@RequestParam("activationCode") String activationCode) {
        Boolean isSuccess = userService.verifyActivationCode(activationCode);
        return ResponseEntity.ok().body(new ResponseMessage("Account Verified. Success: " + isSuccess));
    }

}