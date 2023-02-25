package io.github.codexrm.server.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import io.github.codexrm.server.enums.ERole;
import io.github.codexrm.server.model.Role;
import io.github.codexrm.server.model.User;
import io.github.codexrm.server.payload.request.LoginRequest;
import io.github.codexrm.server.payload.request.SignupRequest;
import io.github.codexrm.server.payload.response.MessageResponse;
import io.github.codexrm.server.payload.response.UserInfoResponse;
import io.github.codexrm.server.repository.RoleRepository;
import io.github.codexrm.server.repository.UserRepository;
import io.github.codexrm.server.security.jwt.JwtUtils;
import io.github.codexrm.server.security.services.UserDetailsImpl;
import io.github.codexrm.server.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest){

        MessageResponse message = authService.registerUser(signUpRequest);

        if(message.getMessage().equals("User registered successfully!"))
            return ResponseEntity.ok(message);

        else
            return ResponseEntity.badRequest().body(message);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    UserDetailsImpl userDetails = authService.authenticateUser(loginRequest);

    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
        .body(new UserInfoResponse(userDetails.getId(),
                                   userDetails.getUsername(),
                                   userDetails.getEmail(),
                                   userDetails.getName(),
                                   userDetails.getLastName(),
                                   userDetails.isEnabled(),
                                   roles));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(new MessageResponse("You've been signed out!"));
    }
}
