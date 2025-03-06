package dev.aziz.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.aziz.dtos.UserDto;
import dev.aziz.entities.Role;
import dev.aziz.exceptions.AppException;
import dev.aziz.repositories.RoleRepository;
import dev.aziz.services.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class UserAuthenticationProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private final UserService userService;
    private final RoleRepository roleRepository;

    @PostConstruct
    protected void init() {
        // this is to avoid having the raw secret key available in the JVM
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserDto userDto) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1 hour

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        List<String> roles = userDto.getRoles().stream().map(Role::getName).toList();

        return JWT.create()
                .withSubject(userDto.getEmail())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim("firstName", userDto.getFirstName())
                .withClaim("lastName", userDto.getLastName())
                .withClaim("isEnabled", userDto.getIsEnabled())
                .withClaim("roles", roles)
                .sign(algorithm);
    }

    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm)
                .build();

        DecodedJWT decoded = verifier.verify(token);

        List<String> rolesNames = decoded.getClaim("roles").asList(String.class);

        Set<Role> roles = rolesNames.stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new AppException("Role not found.", HttpStatus.NOT_FOUND)))
                .collect(Collectors.toSet());

        UserDto user = UserDto.builder()
                .email(decoded.getSubject())
                .firstName(decoded.getClaim("firstName").asString())
                .lastName(decoded.getClaim("lastName").asString())
                .isEnabled(decoded.getClaim("isEnabled").asBoolean())
                .roles(roles)
                .build();

        if (!user.getIsEnabled()) {
            System.out.println("validateToken");
            System.out.println("user.isEnabled():" + user.getIsEnabled());
            System.out.println(user);
            throw new AppException("Verify your email:" + user.getEmail(), HttpStatus.FORBIDDEN);
        }

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }

    public Authentication validateTokenStrongly(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm)
                .build();

        DecodedJWT decoded = verifier.verify(token);

        UserDto user = userService.findByLogin(decoded.getSubject());

        if (!user.getIsEnabled()) {
            System.out.println("validateTokenStrongly");
            System.out.println("user.isEnabled():" + user.getIsEnabled());
            System.out.println(user);
            throw new AppException("Verify your email:" + user.getEmail(), HttpStatus.FORBIDDEN);
        }

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }

}
