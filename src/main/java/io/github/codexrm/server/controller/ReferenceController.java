package io.github.codexrm.server.controller;

import io.github.codexrm.server.component.DTOConverter;
import io.github.codexrm.server.dto.*;
import io.github.codexrm.server.enums.*;
import io.github.codexrm.server.model.*;
import io.github.codexrm.server.service.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jbibtex.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;

@RequestMapping("/api/Reference")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReferenceController {

    private final ReferenceService referenceService;
    private final UserService userService;
    private final DTOConverter dtoConverter;

    private static String UPLOADED_FOLDER = "tempUpload";

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
            @RequestParam(defaultValue = "10") int size,
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
    public ResponseEntity<ReferenceDTO> add(@RequestParam Integer userId, @RequestBody final ReferenceDTO referenceDTO){
        Reference reference = dtoConverter.createReference(referenceDTO, userService.get(userId));
        ReferenceDTO referenceDTOAdded = dtoConverter.toReferenceDTO(referenceService.add(reference));
        return new ResponseEntity<>(referenceDTOAdded, HttpStatus.CREATED);
    }

    @PutMapping("/Update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReferenceDTO> update(@RequestParam Integer userId, @RequestBody final ReferenceDTO referenceDTO){
        Reference reference = dtoConverter.toReference(referenceDTO, userService.get(userId));
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
            @RequestParam(defaultValue = "10") int size,
            @RequestBody final ReferenceLibraryDTO referenceLibrary) {

        List <Reference> newReferenceList = dtoConverter.toReferenceList(referenceLibrary.getNewReferencesList(), userService.get(referenceLibrary.getUserId()));
        List <Reference> updateReferenceList =  dtoConverter.toReferenceList(referenceLibrary.getUpdatedReferencesList(), userService.get(referenceLibrary.getUserId()));

        referenceService.sync(newReferenceList,updateReferenceList,referenceLibrary.getDeletedReferencesList());

        User user = userService.get(referenceLibrary.getUserId());

        ReferencePageDTO referencePageDTO = getReferencePageDTO(referenceService.getAll(user, author, title, page, size, referenceLibrary.getSortReference()));

        if (referencePageDTO == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
        return ResponseEntity.ok().body(referencePageDTO);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/Import", consumes = {"multipart/form-data"})
    @Operation(summary = "Upload a single File")
    public ResponseEntity<?> importReferences(
            @RequestParam Integer userId,
            @RequestParam String format,
            @RequestParam("file") MultipartFile uploadfile){

      //  logger.debug("Single file upload!");
        if (uploadfile.isEmpty()) {
            return new ResponseEntity("You must select a file!", HttpStatus.OK);
        }
        try {
            saveUploadedFiles(Arrays.asList(uploadfile));

            User user = userService.get(userId);
            File file = new File(UPLOADED_FOLDER, uploadfile.getOriginalFilename());
            ArrayList<Reference> refereceList = referenceService.importReferences(file.getPath(), format);
            for (Reference reference: refereceList) {
                reference.setUser(user);
                referenceService.add(reference);
            }
            file.delete();
            return new ResponseEntity<>("Reference Imported!", HttpStatus.OK);

        } catch (IOException | ParseException e) {
            return new ResponseEntity < > (HttpStatus.BAD_REQUEST);
        }

    }
    @RequestMapping(path = "/Export", method = RequestMethod.POST)
    @Operation(summary = "Download a File")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity <Resource> exportReferences(
            @RequestParam String fileName,
            @RequestParam String format,
            @RequestBody final ArrayList<Integer> idList) throws IOException {

        ArrayList<Reference> referenceList = new ArrayList<>();
        for (Integer id: idList) {
            referenceList.add(referenceService.get(id));
        }
        Path path = Paths.get(UPLOADED_FOLDER, fileName);
        File file = new File(path.toString());
        try {
            referenceService.exportReferences(file, referenceList, format);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
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

    // save file
    private void saveUploadedFiles(List <MultipartFile> files) throws IOException {

        for (MultipartFile file: files) {
            if (file.isEmpty()) {
                continue;
                // next pls
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER, file.getOriginalFilename());
            Files.write(path, bytes);
        }
    }
}
