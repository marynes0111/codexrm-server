package io.github.codexrm.server.controller;

import io.github.codexrm.server.component.DTOConverter;
import io.github.codexrm.server.dto.*;
import io.github.codexrm.server.enums.SortReference;
import io.github.codexrm.server.model.Reference;
import io.github.codexrm.server.model.User;
import io.github.codexrm.server.service.ReferenceService;
import io.github.codexrm.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public ReferenceController(ReferenceService referenceService,UserService userService, DTOConverter dtoConverter) {
        this.referenceService = referenceService;
        this.userService = userService;
        this.dtoConverter = dtoConverter;
    }

    @PostMapping("/GetAll")
    public ResponseEntity<ReferencePageDTO> getAll(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestBody(required = false) SortReference sort){

        User user = userService.get(2);

        Page<Reference> pageTuts = referenceService.getAll(user, author, title, page, size, sort);

        if (pageTuts.getContent().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<ReferenceDTO> referenceDTOList = dtoConverter.toReferenceDTOList(pageTuts.getContent());
        PageDTO pageDTO = new PageDTO(pageTuts.getNumber(), pageTuts.getTotalElements(), pageTuts.getTotalPages());
        ReferencePageDTO referencePageDTO = new ReferencePageDTO(referenceDTOList,pageDTO);

        return ResponseEntity.ok().body(referencePageDTO);
    }

    @GetMapping("/Get/{id}")
    public ResponseEntity<ReferenceDTO> getById(@PathVariable final Integer id){
         ReferenceDTO referenceDTO = dtoConverter.toReferenceDTO(referenceService.get(id));
        return ResponseEntity.ok().body(referenceDTO);
    }

    @PostMapping("/Add")
    public ResponseEntity<ReferenceDTO> add(@RequestBody final ReferenceDTO referenceDTO){
        Reference reference = dtoConverter.createReference(referenceDTO);
        ReferenceDTO referenceDTOAdded = dtoConverter.toReferenceDTO(referenceService.add(reference));
        return new ResponseEntity<>(referenceDTOAdded, HttpStatus.CREATED);
    }

    @PutMapping("/Update")
    public ResponseEntity<ReferenceDTO> update( @RequestBody final ReferenceDTO referenceDTO){
        Reference reference = dtoConverter.toReference(referenceDTO);
        ReferenceDTO referenceDTOUpdated = dtoConverter.toReferenceDTO(referenceService.update(reference));
        return new ResponseEntity<>(referenceDTOUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<?> delete(@PathVariable final Integer id) {
        referenceService.delete(id);
        return ResponseEntity.ok().build();
    }

}
