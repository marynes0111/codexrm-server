package io.github.codexrm.server.controller;

import io.github.codexrm.server.component.DTOConverter;
import io.github.codexrm.server.model.Reference;
import io.github.codexrm.server.dto.ReferenceDTO;
import io.github.codexrm.server.dto.ReferenceLibraryDTO;
import io.github.codexrm.server.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/syncTable")
    public ResponseEntity<List<ReferenceDTO>> syncTable(@RequestBody final ReferenceLibraryDTO referenceLibrary) {
            List <Reference> newReferenceList = (List<Reference>) dtoConverter.toReferenceList(referenceLibrary.getNewReferencesList());
            List <Reference> updateReferenceList = (List<Reference>) dtoConverter.toReferenceList(referenceLibrary.getUpdatedReferencesList());

            List<ReferenceDTO> referenceDTOList = (List<ReferenceDTO>) dtoConverter.toReferenceDTOList(syncService.sync(newReferenceList,updateReferenceList,referenceLibrary.getDeletedReferencesList(),referenceLibrary.getUsername()));
           return new ResponseEntity<>(referenceDTOList, HttpStatus.OK);
    }
}
