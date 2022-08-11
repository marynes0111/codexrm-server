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

    public User findById(Integer id) {return userRepository.findById(id).get();}

    public User save(User user) {
        if (user.getId() != null && userRepository.existsById(user.getId())) {
            throw new EntityExistsException("There is already existing entity with such ID in the database.");
        }
        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.findById(user.getId()).map(userFound -> {
            userFound.setUserCodex(user.getUserCodex());
            userFound.setPassword(user.getPassword());
            return userRepository.save(userFound);
        }).orElseThrow(() -> new EntityNotFoundException("UserId " + user.getId() + " not found"));
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    public void deleteAll() {userRepository.deleteAll();}
}
