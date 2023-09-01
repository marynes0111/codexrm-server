package io.github.codexrm.server.controller;

import io.github.codexrm.server.component.DTOConverter;
import io.github.codexrm.server.dto.PageDTO;
import io.github.codexrm.server.dto.ReferenceDTO;
import io.github.codexrm.server.dto.ReferenceLibraryDTO;
import io.github.codexrm.server.dto.ReferencePageDTO;
import io.github.codexrm.server.enums.SortReference;
import io.github.codexrm.server.model.Reference;
import io.github.codexrm.server.model.User;
import io.github.codexrm.server.security.services.UserDetailsImpl;
import io.github.codexrm.server.service.ReferenceService;
import io.github.codexrm.server.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RequestMapping("/api/Reference")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReferenceController {

    private static final String UPLOADED_FOLDER = "tempUpload";
    private final ReferenceService referenceService;
    private final UserService userService;
    private final DTOConverter dtoConverter;

    @Autowired
    public ReferenceController(ReferenceService referenceService, UserService userService, DTOConverter dtoConverter) {
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
            @RequestBody(required = false) SortReference sort) {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.get(userDetails.getId());

        Page<Reference> pageTuts = referenceService.getAll(user, year, title, page, size, sort);

        if (pageTuts == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

         else {
            ReferencePageDTO referencePageDTO = getReferencePageDTO(pageTuts);
            return ResponseEntity.ok().body(referencePageDTO);
        }
    }

    @GetMapping("/Get/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReferenceDTO> getById(@PathVariable final Integer id) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Reference reference = referenceService.get(id);

        if (verificateUser(userDetails.getId(), reference.getUser().getId())) {
            ReferenceDTO referenceDTO = dtoConverter.toReferenceDTO(reference);
            return ResponseEntity.ok().body(referenceDTO);

        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/Add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReferenceDTO> add(@RequestBody final ReferenceDTO referenceDTO) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Reference reference = dtoConverter.createReference(referenceDTO, userService.get(userDetails.getId()));

        if (reference != null) {
            ReferenceDTO referenceDTOAdded = dtoConverter.toReferenceDTO(referenceService.add(reference));
            return new ResponseEntity<>(referenceDTOAdded, HttpStatus.CREATED);

        } else return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping("/Update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReferenceDTO> update(@RequestBody final ReferenceDTO referenceDTO) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Reference reference = dtoConverter.toReference(referenceDTO, userService.get(userDetails.getId()));

        if (reference != null) {
            ReferenceDTO referenceDTOUpdated = dtoConverter.toReferenceDTO(referenceService.update(reference));
            return new ResponseEntity<>(referenceDTOUpdated, HttpStatus.OK);

        } else return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/Delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> delete(@PathVariable final Integer id) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Reference reference = referenceService.get(id);

        if (verificateUser(userDetails.getId(), reference.getUser().getId())) {
            referenceService.delete(id);
            return ResponseEntity.ok().build();

        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/DeleteGroup")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteGroup(@RequestBody ArrayList<Integer> idList) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ArrayList<Integer> newList = verificateUser(userDetails.getId(), idList);

        for (Integer id : newList) {
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

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.get(userDetails.getId());

        List<Reference> newReferenceList = dtoConverter.toReferenceList(referenceLibrary.getNewReferencesList(), user);
        List<Reference> updateReferenceList = dtoConverter.toReferenceList(referenceLibrary.getUpdatedReferencesList(), user);

        referenceService.sync(newReferenceList, updateReferenceList, referenceLibrary.getDeletedReferencesList());

        ReferencePageDTO referencePageDTO = getReferencePageDTO(referenceService.getAll(user, author, title, page, size, referenceLibrary.getSortReference()));

        if (referencePageDTO == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        else return ResponseEntity.ok().body(referencePageDTO);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/Import", consumes = {"multipart/form-data"})
    @Operation(summary = "Upload a single File")
    public ResponseEntity<?> importReferences(
            @RequestParam String format,
            @RequestParam("file") MultipartFile uploadfile) {

        //  logger.debug("Single file upload!");
        if (uploadfile.isEmpty())
            return new ResponseEntity("You must select a file!", HttpStatus.OK);

        try {
            saveUploadedFiles(Arrays.asList(uploadfile));

            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userService.get(userDetails.getId());
            File file = new File(UPLOADED_FOLDER, uploadfile.getOriginalFilename());
            ArrayList<Reference> refereceList = referenceService.importReferences(file.getPath(), format);
            for (Reference reference : refereceList) {
                reference.setUser(user);
                referenceService.add(reference);
            }
            file.delete();
            return new ResponseEntity<>("Reference Imported!", HttpStatus.OK);

        } catch (IOException | ParseException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(path = "/Export", method = RequestMethod.POST)
    @Operation(summary = "Download a File")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Resource> exportReferences(
            @RequestParam String fileName,
            @RequestParam String format,
            @RequestBody final ArrayList<Integer> idList) throws IOException {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ArrayList<Reference> referenceList = new ArrayList<>();

        ArrayList<Integer> newIdList = verificateUser(userDetails.getId(), idList);
        for (Integer id : newIdList) {
            referenceList.add(referenceService.get(id));
        }

        Path path = Paths.get(UPLOADED_FOLDER, fileName);
        File file = new File(path.toString());

        try {
            referenceService.exportReferences(file, referenceList, format);
        } catch (IOException e) {
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

    private ReferencePageDTO getReferencePageDTO(Page<Reference> pageTuts) {
        if (pageTuts.getContent().isEmpty())
            return null;

        List<ReferenceDTO> referenceDTOList = dtoConverter.toReferenceDTOList(pageTuts.getContent());
        PageDTO pageDTO = new PageDTO(pageTuts.getNumber(), pageTuts.getTotalElements(), pageTuts.getTotalPages());

        return new ReferencePageDTO(referenceDTOList, pageDTO);
    }

    // save file
    private void saveUploadedFiles(List<MultipartFile> files) throws IOException {

        for (MultipartFile file : files) {
            if (file.isEmpty())
                continue;

            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER, file.getOriginalFilename());
            Files.write(path, bytes);
        }
    }

    private boolean verificateUser(Integer userId, Integer referenceUserId) {
        return Objects.equals(referenceUserId, userId);
    }

    private ArrayList<Integer> verificateUser(Integer userId, ArrayList<Integer> referenceId) {
        ArrayList<Integer> idList = new ArrayList<>();

        for (Integer id : referenceId) {
            Reference reference = referenceService.get(id);
            if (verificateUser(userId, reference.getUser().getId()))
                idList.add(id);
        }
        return idList;
    }
}
