package io.github.codexrm.server;

import io.github.codexrm.server.model.*;
import io.github.codexrm.server.service.SyncService;
import io.github.codexrm.server.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class SyncTest {

    @Autowired
    SyncService syncService;
    @Autowired
    UserService userService;

    @Test
    void syncReference() {
        User userM = userService.findById(9);

       ConferenceProceedingReference r0 = new ConferenceProceedingReference("Urbina,Odri",
               "Impcto economico en el este del pais", LocalDate.of(20021,01,01),
               "gg",userM, "3","4","Mexico,DF");

        ThesisReference r1 = new ThesisReference("Ramirez,Juan",
                "Violencia en relaciones de parejas",LocalDate.of(2004,01,01),
                "ff",userM,"Iberoamericana","Masters","Puebla");

        BookReference r2 = new BookReference("Stallings,William",
                "Organzacion y arquitecturas de computadoras",LocalDate.of(2005,01,01),
                "bb",userM,"MartinRomero,Miguel","1","18","Guayaquil,Ecuador","21");

        ThesisReference r3 = new ThesisReference("Nunnez,Yulier",
                "Modelo determinsta para la autoverificacion",LocalDate.of(2013,01,01),
                "dd",userM,"Universidad de Alicante","phd","Alicante,España");

        List<Reference> newReferenceList = new ArrayList<>();
        newReferenceList.add(r0);
        newReferenceList.add(r1);
        newReferenceList.add(r2);
        newReferenceList.add(r3);

        ArticleReference article = new ArticleReference("Allen,James",
                "Construccion de la zona de cultivo", LocalDate.of(2004,04,04),
                "aa",userM,"Educacion","55","3","214-226");

        List<Reference> updateReferenceList = new ArrayList<>();

        updateReferenceList.add(article);

        List<Integer> deleteReferenceList = new ArrayList<>();
        deleteReferenceList.add(43);
        deleteReferenceList.add(44);

        List<Reference> listSync  = syncService.sync(newReferenceList,updateReferenceList,deleteReferenceList,userM.getId());
    }
}

