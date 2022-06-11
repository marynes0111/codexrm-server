package io.github.codexrm.server.service;

import io.github.codexrm.server.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        init();
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
    private void init(){
        ConferenceProceedingsReferenceDTO r0 = new ConferenceProceedingsReferenceDTO
                ("Urbina,Odri", "Impcto economico en el este del pais", LocalDate.of(20021,01,01),
                        "gg",0, "3","4","Mexico,DF");

        ThesisReferenceDTO r1 = new ThesisReferenceDTO("Ramirez,Juan",
                "Violencia en relaciones de parejas",LocalDate.of(2004,01,01),
                "ff",1,"Iberoamericana","Masters","Puebla");

        ArticleReferenceDTO r2 = new ArticleReferenceDTO("Allen,James",
                "Cultural construction zones",LocalDate.of(2004,01,01),
                "aa",2,"theacher education","55","3","214-226");

        BookReferenceDTO r3 = new BookReferenceDTO("Stallings,William",
                "Organzacion y arquitecturas de computadoras",LocalDate.of(2005,01,01),
                "bb",3,"MartinRomero,Miguel","1","18","Guayaquil,Ecuador","21");

        referenceTable.put(r0.getId(),r0);
        referenceTable.put(r1.getId(),r1);
        referenceTable.put(r2.getId(),r2);
        referenceTable.put(r3.getId(),r3);
    }
}
