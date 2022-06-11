package io.github.codexrm.server;

import io.github.codexrm.server.dto.*;
import io.github.codexrm.server.service.SyncService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootTest
public class SyncTest {

    @Autowired
    SyncService syncService;

    @Test
    void syncReference() {
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

        BookLetReferenceDTO r4 = new BookLetReferenceDTO("Medina,Juan",
                "Introduccion a funciones",LocalDate.of(2022,01,01),
                "ee",4,"inst ciencias","Guayaquil,Ecuador");

        ThesisReferenceDTO r5 = new ThesisReferenceDTO("Nunnez,Yulier",
                "Modelo determinsta para la autoverificacion",LocalDate.of(2013,01,01),
                "dd",5,"Universidad de Alicante","phd","Alicante,España");

        BookSectionReferenceDTO r6 = new BookSectionReferenceDTO("Fernandez,Maria",
                "La disercion en la enseñanza secundaria", LocalDate.of(2009,01,01),
                "cc",6, "Madriz,Kadil","2","3","New York,NY",
                "4","1","45-57");

        ReferenceLibraryDTO libraryTime0 = new ReferenceLibraryDTO();
        libraryTime0.setNewReference(r0);
        libraryTime0.setNewReference(r1);
        libraryTime0.setNewReference(r2);
        libraryTime0.setNewReference(r3);

        ArrayList<ReferenceDTO> listTime0 = syncService.sync(libraryTime0);
        Assertions.assertEquals(4, listTime0.size());


        r1.setAddress("Bogota,Colombia");
        r2.setNumber("10");

        ReferenceLibraryDTO libraryTime1 = new ReferenceLibraryDTO();
        libraryTime1.setNewReference(r4);
        libraryTime1.setNewReference(r5);
        libraryTime1.setNewReference(r6);
        libraryTime1.setUpdatedReference(r1);
        libraryTime1.setUpdatedReference(r2);
        libraryTime1.setDeletedReference(0);
        libraryTime1.setDeletedReference(3);

        ArrayList<ReferenceDTO> listTime1  = syncService.sync(libraryTime1);
        Assertions.assertEquals(5, listTime1.size());
    }
}
