package io.github.codexrm.server.controller;

import io.github.codexrm.server.Component.DTOConverter;
import io.github.codexrm.server.Model.Reference;
import io.github.codexrm.server.dto.ReferenceDTO;
import io.github.codexrm.server.dto.ReferenceLibraryDTO;
import io.github.codexrm.server.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/Sync")
@RestController
public class SyncController {

    private final SyncService syncService;
    private final DTOConverter dtoConverter;

    @Autowired
    public SyncController(SyncService syncService, DTOConverter dtoConverter) {
        this.syncService = syncService;
        this.dtoConverter = dtoConverter;
    }

    @PostMapping(path = { "/syncTable" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<ReferenceDTO>> syncTable(@RequestBody ReferenceLibraryDTO referenceLibrary) throws URISyntaxException {
        try {
            ArrayList <Reference> newReferenceList = (ArrayList<Reference>) dtoConverter.toReferenceList(referenceLibrary.getNewReferencesList());
            ArrayList <Reference> updateReferenceList = (ArrayList<Reference>) dtoConverter.toReferenceList(referenceLibrary.getUpdatedReferencesList());

            List<ReferenceDTO> referenceDTOList = (ArrayList<ReferenceDTO>) dtoConverter.toReferenceDTOList(syncService.sync(newReferenceList,updateReferenceList,referenceLibrary.getDeletedReferencesList(),referenceLibrary.getUserId()));
           return new ResponseEntity<List<ReferenceDTO>>(referenceDTOList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
