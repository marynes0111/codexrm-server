package io.github.codexrm.server.controller;

import io.github.codexrm.server.Component.DTOConverter;
import io.github.codexrm.server.Model.Reference;
import io.github.codexrm.server.Model.User;
import io.github.codexrm.server.dto.ReferenceDTO;
import io.github.codexrm.server.dto.UserDTO;
import io.github.codexrm.server.service.ReferenceService;
import io.github.codexrm.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/Reference")
@RestController
public class ReferenceController {

    private final ReferenceService referenceService;
    private final UserService userService;
    private final DTOConverter dtoConverter;

    @Autowired
    public ReferenceController(ReferenceService referenceService, DTOConverter dtoConverter, UserService userService) {
        this.referenceService = referenceService;
        this.dtoConverter = dtoConverter;
        this.userService = userService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ReferenceDTO>> getAll(@RequestBody final User user){
        List<ReferenceDTO> referenceDTOList = dtoConverter.toReferenceDTOList(referenceService.findAll(user));
        return ResponseEntity.ok().body(referenceDTOList);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ReferenceDTO> getById(@PathVariable final Integer id){
        ReferenceDTO ReferenceDTO = dtoConverter.toReferenceDTO(referenceService.findById(id));
        return ResponseEntity.ok().body(ReferenceDTO);
    }

    @PostMapping("/add")
    public ResponseEntity<ReferenceDTO> add(@RequestBody final ReferenceDTO referenceDTO){
        Reference reference = dtoConverter.toReference(referenceDTO);
        ReferenceDTO referenceDTOAdded = dtoConverter.toReferenceDTO(referenceService.save(reference));
        return new ResponseEntity<>(referenceDTOAdded, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ReferenceDTO> update( @RequestBody final ReferenceDTO referenceDTO){
        Reference reference = dtoConverter.toReference(referenceDTO);
        ReferenceDTO referenceDTOUpdated = dtoConverter.toReferenceDTO(referenceService.update(reference));
        return new ResponseEntity<>(referenceDTOUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable final Integer id) {
        referenceService.delete(id);
        return ResponseEntity.ok().build();
    }
}
