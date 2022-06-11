package io.github.codexrm.server.controller;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RequestMapping("/Reference")
@RestController
public class SyncController {

    @Autowired
    private SyncService syncService;

    @PostMapping(path = { "/sync" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<ReferenceDTO>> syncTable(@RequestBody ReferenceLibraryDTO referenceLibrary) throws URISyntaxException {
        try {
            return new ResponseEntity<List<ReferenceDTO>>(syncService.sync(referenceLibrary), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
