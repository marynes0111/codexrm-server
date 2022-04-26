package io.github.codexrm.server.service;

import io.github.codexrm.server.dto.ReferenceDTO;
import io.github.codexrm.server.dto.ReferenceLibraryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Enumeration;

@Service
public class SyncService {

    private Library library;

    @Autowired
    public SyncService() {
        this.library = new Library();
    }

    public ArrayList<ReferenceDTO> sync (ReferenceLibraryDTO referenceLibraryDTO) {

        ArrayList<ReferenceDTO> list = new ArrayList<>();

        for(ReferenceDTO referenceDTO: referenceLibraryDTO.getNewReferencesList()){
          library.setReference(referenceDTO);
        }

        for(ReferenceDTO referenceDTO: referenceLibraryDTO.getUpdatedReferencesList()){
            library.getReferenceTable().remove(referenceDTO.getId());
            library.setReference(referenceDTO);
        }

        for(Integer id: referenceLibraryDTO.getDeletedReferencesList()){
            library.getReferenceTable().remove(id);
        }

        Enumeration<Integer> e = library.getReferenceTable().keys();
        while(e.hasMoreElements()) {
            list.add(library.getReferenceTable().get(e.nextElement()));
        }

        return list;
    }
}
