package io.github.codexrm.server.service;

import io.github.codexrm.server.dto.ReferenceDTO;
import io.github.codexrm.server.dto.ReferenceLibraryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

@Service
public class SyncService {

    private Hashtable<Integer,ReferenceDTO> referenceTable;

    @Autowired
    public SyncService() {
        this.referenceTable = new Hashtable<>();
    }

    public ArrayList<ReferenceDTO> sync (ReferenceLibraryDTO referenceLibraryDTO) {

        ArrayList<ReferenceDTO> list = new ArrayList<>();

        for(ReferenceDTO referenceDTO: referenceLibraryDTO.getNewReferencesList()){
            referenceTable.put(referenceDTO.getId(),referenceDTO);
        }

        for(ReferenceDTO referenceDTO: referenceLibraryDTO.getUpdatedReferencesList()){
            referenceTable.remove(referenceDTO.getId());
            referenceTable.put(referenceDTO.getId(),referenceDTO);
        }

        for(Integer id: referenceLibraryDTO.getDeletedReferencesList()){
            referenceTable.remove(id);
        }

        Enumeration<Integer> e = referenceTable.keys();
        while(e.hasMoreElements()) {
            list.add(referenceTable.get(e.nextElement()));
        }

        return list;
    }
}
