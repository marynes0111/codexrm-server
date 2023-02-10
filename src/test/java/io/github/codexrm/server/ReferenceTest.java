package io.github.codexrm.server;

import io.github.codexrm.server.model.*;
import io.github.codexrm.server.service.ReferenceService;
import io.github.codexrm.server.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ReferenceTest {

    @Autowired
    ReferenceService referenceService;
    @Autowired
    UserService userService;

    @Test
    void deleteReference() {

        referenceService.deleteAll(userService.get(1));
    }


    @Test
    void testReferenceServiceCRUD() {
        User userM = userService.get(1);
        User userL = userService.get(2);

        ArticleReference article = new ArticleReference("Allen,James","Construccion de la zona de cultivo",
                LocalDate.of(2004,04,04),"aa",userM,"Educacion","55","3","214-226");

        BookLetReference bookLet = new BookLetReference("Medina,Juan","Introduccion a las funciones",
                LocalDate.of(2022,02,02),"dd",userM,"Inst. Ciencia y educacion","Guayaquil,Ecuador");

        BookReference book = new BookReference("Allen,James","Relacion entre las carreras",
                LocalDate.of(1997,07,07),"ii",userL,"Prencite Hall","3","7","Vedado,La Habana","5");

        BookSectionReference bookSection = new BookSectionReference("Fernandez,Maria","La deserc. de la enseñanza",
                LocalDate.of(2004,04,04),"cc",userM,"k.Madriz","2","3","New York","4","1","45-57");

        ConferenceProceedingReference proceeding = new ConferenceProceedingReference("Stallin,Willian","Cambio Climatico",
                LocalDate.of(2022,02,04),"kk",userL,"2","3","Madrid,España");

        ThesisReference thesis = new ThesisReference("Ramirez,Juan","Ayuda para Windows",
                LocalDate.of(2018,8,8),"jj",userL,"Fructuoso Rodriguez","master","Carretera Tapaste");

        ArticleReference articleSaved = (ArticleReference) referenceService.save(article);
        BookLetReference bookLetSaved = (BookLetReference) referenceService.save(bookLet);
        BookReference bookSaved = (BookReference) referenceService.save(book);
        BookSectionReference bookSectionSaved = (BookSectionReference) referenceService.save(bookSection);
        ConferenceProceedingReference proceedingSaved = (ConferenceProceedingReference) referenceService.save(proceeding);
        ThesisReference thesisSaved = (ThesisReference) referenceService.save(thesis);


        ArticleReference articleFound = (ArticleReference) referenceService.findById(articleSaved.getId());
        articleFound.setAuthor("Bond,James");

        Reference articleAUpdated = referenceService.update(articleFound);

        ArticleReference articleA = (ArticleReference) referenceService.findById(articleFound.getId());
        assertEquals("Bond,James", articleA.getAuthor());
        assertEquals("Construccion de la zona de cultivo", articleA.getTitle());
        assertEquals("Educacion", articleA.getJournal());

       referenceService.delete(book.getId());
    }
}
