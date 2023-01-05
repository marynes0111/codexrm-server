package io.github.codexrm.server;

import io.github.codexrm.server.model.User;
import io.github.codexrm.server.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserTest {

    @Autowired
    UserService userService;

    @Test
    void testUserServiceCRUD() {

        User userA = new User("marynes", "123");
        User userB = new User("maria", "maria123");
        User userC = new User("luis", "luis01");

        User userASaved = userService.save(userA);
        User userBSaved = userService.save(userB);
        User userCSaved = userService.save(userC);

        User userAFound = userService.findById(userASaved.getUsername());
        userAFound.setPassword("mary");

        User userAUpdated = userService.update(userAFound);

        User userD = userService.findById(userAFound.getUsername());
        assertEquals("mary", userD.getPassword());

        userService.delete(userD.getUsername());

       assertEquals(2, userService.findAll().size());
    }
}
