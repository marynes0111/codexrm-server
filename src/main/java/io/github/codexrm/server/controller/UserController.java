package io.github.codexrm.server.controller;

import io.github.codexrm.server.component.DTOConverter;
import io.github.codexrm.server.model.User;
import io.github.codexrm.server.dto.UserDTO;
import io.github.codexrm.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/User")
@RestController
public class UserController {

    private final UserService userService;
    private final DTOConverter dtoConverter;

    @Autowired
    public UserController(UserService userService, DTOConverter dtoConverter) {
        this.userService = userService;
        this.dtoConverter = dtoConverter;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserDTO>> getAll(){
        List<UserDTO> userDTOList = dtoConverter.toUserDTOList(userService.findAll());
        return ResponseEntity.ok().body(userDTOList);
    }

    @GetMapping("/getByUsername/{username}")
    public ResponseEntity<UserDTO> getByUsername(@PathVariable final String username){
        UserDTO userDTO = dtoConverter.toUserDTO(userService.findById(username));
        return ResponseEntity.ok().body(userDTO);
    }

    @PostMapping("/add")
    public ResponseEntity<UserDTO> add(@RequestBody final UserDTO userDTO){
        User user = dtoConverter.toUser(userDTO);
        UserDTO userDTOAdded = dtoConverter.toUserDTO(userService.save(user));
        return new ResponseEntity<>(userDTOAdded, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDTO> update( @RequestBody final UserDTO userDTO){
        User user = dtoConverter.toUser(userDTO);
        UserDTO userDTOUpdated = dtoConverter.toUserDTO(userService.update(user));
        return new ResponseEntity<>(userDTOUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<?> delete(@PathVariable final String username) {
        userService.delete(username);
        return ResponseEntity.ok().build();
    }
}
