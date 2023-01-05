package io.github.codexrm.server.service;

import io.github.codexrm.server.model.User;
import io.github.codexrm.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(final UserRepository userRepository) {this.userRepository = userRepository;}

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(String username) {return userRepository.findById(username).get();}

    public User save(User user) {
        if (user.getUsername() != null && userRepository.existsById(user.getUsername())) {
            throw new EntityExistsException("There is already existing entity with such ID in the database.");
        }
        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.findById(user.getUsername()).map(userFound -> {
            userFound.setPassword(user.getPassword());
            return userRepository.save(userFound);
        }).orElseThrow(() -> new EntityNotFoundException("Username " + user.getUsername() + " not found"));
    }

    public void delete(String username) {
        userRepository.deleteById(username);
    }

    public void deleteAll() {userRepository.deleteAll();}
}
