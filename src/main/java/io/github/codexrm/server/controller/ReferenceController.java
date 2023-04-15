package io.github.codexrm.server.controller;

import io.github.codexrm.server.component.DTOConverter;
import io.github.codexrm.server.dto.*;
import io.github.codexrm.server.enums.*;
import io.github.codexrm.server.model.*;
import io.github.codexrm.server.service.*;
import org.jbibtex.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/Reference")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
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
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReferencePageDTO> getAll(
            @RequestParam(required = false) String year,
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam Integer userId,
            @RequestBody(required = false) SortReference sort){

        User user = userService.get(userId);

        Page<Reference> pageTuts= referenceService.getAll(user, year, title, page, size, sort);

        if (pageTuts == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else{
            ReferencePageDTO referencePageDTO = getReferencePageDTO(pageTuts);
            return ResponseEntity.ok().body(referencePageDTO);
        }
    }

    @GetMapping("/Get/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReferenceDTO> getById(@PathVariable final Integer id){
         ReferenceDTO referenceDTO = dtoConverter.toReferenceDTO(referenceService.get(id));
        return ResponseEntity.ok().body(referenceDTO);
    }

    @PostMapping("/Add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReferenceDTO> add(@RequestBody final ReferenceDTO referenceDTO){
        Reference reference = dtoConverter.createReference(referenceDTO);
        ReferenceDTO referenceDTOAdded = dtoConverter.toReferenceDTO(referenceService.add(reference));
        return new ResponseEntity<>(referenceDTOAdded, HttpStatus.CREATED);
    }

    @PutMapping("/Update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReferenceDTO> update( @RequestBody final ReferenceDTO referenceDTO){
        Reference reference = dtoConverter.toReference(referenceDTO);
        ReferenceDTO referenceDTOUpdated = dtoConverter.toReferenceDTO(referenceService.update(reference));
        return new ResponseEntity<>(referenceDTOUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/Delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> delete(@PathVariable final Integer id) {
        referenceService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/DeleteGroup")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteGroup(@RequestBody ArrayList<Integer> idList) {
        for(Integer id: idList) {
            referenceService.delete(id);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/Sync")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReferencePageDTO> sync(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestBody final ReferenceLibraryDTO referenceLibrary) {

        List <Reference> newReferenceList = dtoConverter.toReferenceList(referenceLibrary.getNewReferencesList());
        List <Reference> updateReferenceList =  dtoConverter.toReferenceList(referenceLibrary.getUpdatedReferencesList());

        referenceService.sync(newReferenceList,updateReferenceList,referenceLibrary.getDeletedReferencesList());

        User user = userService.get(referenceLibrary.getUserId());

        ReferencePageDTO referencePageDTO = getReferencePageDTO(referenceService.getAll(user, author, title, page, size, referenceLibrary.getSortReference()));

        if (referencePageDTO == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
        return ResponseEntity.ok().body(referencePageDTO);
    }

    @PostMapping("/Import")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity.BodyBuilder importReferences(
            @RequestParam String path,
            @RequestParam Integer userId,
            @RequestParam String format){

        try {
            User user = userService.get(userId);
            ArrayList<Reference> refereceList = referenceService.importReferences(path, format);
            for (Reference reference: refereceList) {
                reference.setUser(user);
                referenceService.add(reference);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok();
    }

    @PostMapping("/Export")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity.BodyBuilder exportReferences(
            @RequestParam String path,
            @RequestParam String format,
            @RequestBody final ArrayList<Integer> idList){

        ArrayList<Reference> referenceList = new ArrayList<>();
        for (Integer id: idList) {
            referenceList.add(referenceService.get(id));
        }

        try {
            referenceService.exportReferences(new File(path), referenceList, format);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok();
    }

    @PostMapping("/GetAllFromUsers")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ReferencePageDTO> getAllFromUsers(
            @RequestParam(required = false) String year,
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestBody(required = false) SortReference sort) {

        Page<Reference> pageTuts = referenceService.getAllFromUsers(year, title, page, size, sort);

        if (pageTuts == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else {
            ReferencePageDTO referencePageDTO = getReferencePageDTO(pageTuts);
            return ResponseEntity.ok().body(referencePageDTO);
        }

    }

    private ReferencePageDTO getReferencePageDTO(Page<Reference> pageTuts){
        if (pageTuts.getContent().isEmpty()) {
            return null;
        }

        List<ReferenceDTO> referenceDTOList = dtoConverter.toReferenceDTOList(pageTuts.getContent());
        PageDTO pageDTO = new PageDTO(pageTuts.getNumber(), pageTuts.getTotalElements(), pageTuts.getTotalPages());

       return new ReferencePageDTO(referenceDTOList,pageDTO);
    }
}
