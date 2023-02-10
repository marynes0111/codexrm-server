package io.github.codexrm.server;

import io.github.codexrm.server.enums.SortUser;
import io.github.codexrm.server.model.User;
import io.github.codexrm.server.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserTest {

    @Autowired
    UserService userService;

    @Test
    void testUserServiceCRUD() {

        User userA = new User("marynes","Marynes","Diaz","mary@unah.edu.cu",true,"123");
        User userB = new User("maria","Maria","Gomez","mgomez@unah.edu.cu",true, "maria123");
        User userC = new User("luis","Luis","Acosta","acosta@unah.edu.cu",true, "luis01");

        User userASaved = userService.add(userA);
        User userBSaved = userService.add(userB);
        User userCSaved = userService.add(userC);

        User userAFound = userService.get(userASaved.getId());
        userAFound.setPassword("mary");

        User userAUpdated = userService.update(userAFound);

        User userD = userService.get(userAFound.getId());
        assertEquals("mary", userD.getPassword());

        userService.delete(userD.getId());

    }

    @Test
    void testUserServiceGetAll() {

        Page<User> pageTuts = userService.getAll(null,0,6, SortUser.nameAsc);
        assertEquals(6, pageTuts.getContent().size());

    }
}
