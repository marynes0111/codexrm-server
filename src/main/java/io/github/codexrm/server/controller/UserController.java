package io.github.codexrm.server.controller;

import io.github.codexrm.server.component.DTOConverter;
import io.github.codexrm.server.dto.PageDTO;
import io.github.codexrm.server.dto.UserDTO;
import io.github.codexrm.server.dto.UserPageDTO;
import io.github.codexrm.server.enums.*;
import io.github.codexrm.server.model.User;
import io.github.codexrm.server.payload.request.AddUserRequest;
import io.github.codexrm.server.payload.request.UpdateUserPasswordRequest;
import io.github.codexrm.server.payload.response.MessageResponse;
import io.github.codexrm.server.security.services.UserDetailsImpl;
import io.github.codexrm.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/api/User")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserService userService;
    private final DTOConverter dtoConverter;
    private final PasswordEncoder encoder;

    @Autowired
    public UserController(UserService userService, DTOConverter dtoConverter, PasswordEncoder encoder) {
        this.userService = userService;
        this.dtoConverter = dtoConverter;
        this.encoder = encoder;
    }

    @PostMapping("/GetAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserPageDTO> getAll(
            @RequestParam(required = false) String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestBody(required = false) SortUser sort) {

        Page<User> pageTuts = userService.getAll(username, page, size, sort);

        if (pageTuts.getContent().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<UserDTO> userDTOList = dtoConverter.toUserDTOList(pageTuts.getContent());
        PageDTO pageDTO = new PageDTO(pageTuts.getNumber(), pageTuts.getTotalElements(), pageTuts.getTotalPages());
        UserPageDTO userPageDTO = new UserPageDTO(userDTOList, pageDTO);

        return ResponseEntity.ok().body(userPageDTO);
    }

    @GetMapping("/Get/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('MODERATOR') or hasRole('AUDITOR')")
    public ResponseEntity<UserDTO> getById(@PathVariable final Integer id) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(containsRole(userDetails))
            return ResponseEntity.ok().body(dtoConverter.toUserDTO(userService.get(id)));

        else
            if(Objects.equals(userDetails.getId(), id))
                return ResponseEntity.ok().body(dtoConverter.toUserDTO(userService.get(id)));

            else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/Add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> add(@RequestBody AddUserRequest addUserRequest) {

        String exist = userService.validateUser(addUserRequest.getUsername(), addUserRequest.getEmail());

        if (exist.equals("Error: Username is already taken!"))
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));

        if (exist.equals("Error: Email is already in use!"))
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));

        // Create new user's account
        User user = new User(addUserRequest.getUsername(), addUserRequest.getName(), addUserRequest.getLastName(), addUserRequest.getEmail(), addUserRequest.isEnabled(), encoder.encode(addUserRequest.getPassword()));
        userService.createUserAccount(user, false, addUserRequest.getRoles());
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PutMapping("/Update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> update(@RequestBody final UserDTO userDTO) {

        User user = dtoConverter.toUser(userDTO);
        user.setPassword(userService.getPasswordById(userDTO.getId()));
        return new ResponseEntity<>(dtoConverter.toUserDTO(userService.update(user)), HttpStatus.OK);
    }

    @PutMapping("/UpdatePassword")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updatePassword(@RequestBody final UpdateUserPasswordRequest updateUserPassword) {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.get(userDetails.getId());

        if (!encoder.matches(updateUserPassword.getCurrentPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Current password incorrect!"));

        } else {
            if (!updateUserPassword.getNewPassword().equals(updateUserPassword.getConfirmationPassword())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: New password and confirmation are different!"));

            } else {
                user.setPassword(encoder.encode(updateUserPassword.getNewPassword()));
                userService.update(user);
                return ResponseEntity.ok(new MessageResponse("Updated password!"));
            }
        }
    }

    @PutMapping("/Preferences")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDTO> updatePreferences(@RequestBody final UserDTO userDTO) {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = userDetails.getId();

        if (Objects.equals(userId, userDTO.getId())) {
            User user = dtoConverter.toUser(userDTO);
            User existingUser = userService.get(userDTO.getId());
            user.setRoles(existingUser.getRoles());
            user.setPassword(existingUser.getPassword());
            UserDTO userDTOUpdated = dtoConverter.toUserDTO(userService.update(user));
            return new ResponseEntity<>(userDTOUpdated, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/Delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable final Integer id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/DeleteGroup")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteGroup(@RequestBody ArrayList<Integer> idList) {
        for (Integer id : idList) {
            userService.delete(id);
        }
        return ResponseEntity.ok().build();
    }

    private boolean containsRole(UserDetails userDetails){
        boolean contains = false;
        for( GrantedAuthority a: userDetails.getAuthorities()){
            if(a.getAuthority().equals("ROLE_ADMIN"))
                contains = true;
        }
        return contains;
    }
}
