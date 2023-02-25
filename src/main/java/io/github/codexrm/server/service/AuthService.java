package io.github.codexrm.server.service;

import io.github.codexrm.server.enums.ERole;
import io.github.codexrm.server.model.Role;
import io.github.codexrm.server.model.User;
import io.github.codexrm.server.payload.request.LoginRequest;
import io.github.codexrm.server.payload.request.SignupRequest;
import io.github.codexrm.server.payload.response.MessageResponse;
import io.github.codexrm.server.payload.response.UserInfoResponse;
import io.github.codexrm.server.repository.ReferenceRepository;
import io.github.codexrm.server.repository.RoleRepository;
import io.github.codexrm.server.repository.UserRepository;
import io.github.codexrm.server.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

   private UserRepository userRepository;
   private RoleRepository roleRepository;
   private PasswordEncoder encoder;
   private AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(final UserRepository userRepository, RoleRepository roleRepository,
                       PasswordEncoder encoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
    }

    public MessageResponse registerUser(SignupRequest signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
        return new MessageResponse("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
        return new MessageResponse("Error: Email is already in use!");
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(), signUpRequest.getName(),
                signUpRequest.getLastName(), signUpRequest.getEmail(), signUpRequest.isEnabled(),
            encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);

        } else {
        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);

                    break;
                case "manager":
                    Role managerRole = roleRepository.findByName(ERole.ROLE_MANAGER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(managerRole);

                    break;

                case "auditor":
                    Role auditorRole = roleRepository.findByName(ERole.ROLE_AUDITOR)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(auditorRole);

                    break;
                default:
                    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
            }
        });
        }

        user.setRoles(roles);
        userRepository.save(user);
        return new MessageResponse("User registered successfully!");
    }

    public UserDetailsImpl authenticateUser(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return  (UserDetailsImpl) authentication.getPrincipal();
    }
}
