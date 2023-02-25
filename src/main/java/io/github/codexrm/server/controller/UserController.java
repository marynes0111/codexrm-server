package io.github.codexrm.server.controller;

import io.github.codexrm.server.component.DTOConverter;
import io.github.codexrm.server.dto.PageDTO;
import io.github.codexrm.server.dto.UserPageDTO;
import io.github.codexrm.server.enums.SortUser;
import io.github.codexrm.server.dto.UserDTO;
import io.github.codexrm.server.model.User;
import io.github.codexrm.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/User")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserService userService;
    private final DTOConverter dtoConverter;

    @Autowired
    public UserController(UserService userService, DTOConverter dtoConverter) {
        this.userService = userService;
        this.dtoConverter = dtoConverter;
    }

    @PostMapping("/GetAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserPageDTO> getAll(
            @RequestParam(required = false) String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestBody(required = false) SortUser sort){

        Page<User> pageTuts = userService.getAll(username,page,size,sort);

        if (pageTuts.getContent().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<UserDTO> userDTOList = dtoConverter.toUserDTOList(pageTuts.getContent());
        PageDTO pageDTO = new PageDTO(pageTuts.getNumber(), pageTuts.getTotalElements(), pageTuts.getTotalPages());
        UserPageDTO userPageDTO = new UserPageDTO(userDTOList,pageDTO);

        return ResponseEntity.ok().body(userPageDTO);

    }

    @GetMapping("/Get/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('MODERATOR') or hasRole('AUDITOR')")
    public ResponseEntity<UserDTO> getByUsername(@PathVariable final Integer id){
        UserDTO userDTO = dtoConverter.toUserDTO(userService.get(id));
        return ResponseEntity.ok().body(userDTO);
    }

    @PostMapping("/Add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> add(@RequestBody final UserDTO userDTO){
        User user = dtoConverter.toUser(userDTO);
        UserDTO userDTOAdded = dtoConverter.toUserDTO(userService.add(user));
        return new ResponseEntity<>(userDTOAdded, HttpStatus.CREATED);
    }

    @PutMapping("/Update")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('MODERATOR') or hasRole('AUDITOR')")
    public ResponseEntity<UserDTO> update(@RequestBody final UserDTO userDTO){
        User user = dtoConverter.toUser(userDTO);
        UserDTO userDTOUpdated = dtoConverter.toUserDTO(userService.update(user));
        return new ResponseEntity<>(userDTOUpdated, HttpStatus.OK);
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
        for(Integer id: idList) {
            userService.delete(id);
        }
        return ResponseEntity.ok().build();
    }
}
