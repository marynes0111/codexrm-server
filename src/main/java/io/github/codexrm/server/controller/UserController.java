package io.github.codexrm.server.controller;

import io.github.codexrm.server.Component.DTOConverter;
import io.github.codexrm.server.Model.Reference;
import io.github.codexrm.server.Model.User;
import io.github.codexrm.server.dto.ReferenceDTO;
import io.github.codexrm.server.dto.ReferenceLibraryDTO;
import io.github.codexrm.server.dto.UserDTO;
import io.github.codexrm.server.service.SyncService;
import io.github.codexrm.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.ArrayList;
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

    @GetMapping("/getById/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable final Integer id){
        UserDTO userDTO = dtoConverter.toUserDTO(userService.findById(id));
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable final Integer id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
