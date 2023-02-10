package io.github.codexrm.server.service;

import io.github.codexrm.server.enums.SortUser;
import io.github.codexrm.server.model.User;
import io.github.codexrm.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(final UserRepository userRepository) {this.userRepository = userRepository;}

    public Page<User> getAll(String username, int page, int size, SortUser sort) {

        Sort.Order order = getOrder(sort);
        Pageable pagingSort = PageRequest.of(page, size, Sort.by(order));

        if (username == null)
            return userRepository.findAll(pagingSort);
        else
            return  userRepository.findByUsernameContaining(username, pagingSort);
    }

    public User get(Integer id) {return userRepository.findById(id).get();}

    public User add(User user) {
        if (user.getId() != null && userRepository.existsById(user.getId())) {
            throw new EntityExistsException("There is already existing entity with such ID in the database.");
        }
        return userRepository.save(user);
    }

    public User update(User user) {
        if (user.getId() != null && !userRepository.existsById(user.getId())) {
            throw new EntityNotFoundException("There is no entity with such ID in the database.");
        }

        return userRepository.save(user);
    }

    public void delete(Integer id) { userRepository.deleteById(id); }

    private Sort.Order getOrder(SortUser sort) {

        if (sort == null) {
            return new Sort.Order(Sort.Direction.ASC, "id");

        }else {
            switch (sort) {
                case idAsc:
                    return new Sort.Order(Sort.Direction.ASC, "id");

                case idDesc:
                    return new Sort.Order(Sort.Direction.DESC, "id");

                case nameAsc:
                    return new Sort.Order(Sort.Direction.ASC, "name");

                case nameDesc:
                    return new Sort.Order(Sort.Direction.DESC, "name");

                case lastNameAsc:
                    return new Sort.Order(Sort.Direction.ASC, "lastname");

                case lastNameDesc:
                    return new Sort.Order(Sort.Direction.DESC, "lastname");

                case emailAsc:
                    return new Sort.Order(Sort.Direction.ASC, "email");

                case emailDesc:
                    return new Sort.Order(Sort.Direction.DESC, "email");

                case enabledAsc:
                    return new Sort.Order(Sort.Direction.ASC, "enabled");

                case enabledDesc:
                    return new Sort.Order(Sort.Direction.DESC, "enabled");

                case usernameAsc:
                    return new Sort.Order(Sort.Direction.ASC, "username");

                case usernameDesc:
                    return new Sort.Order(Sort.Direction.DESC, "username");

                case passwordAsc:
                    return new Sort.Order(Sort.Direction.ASC, "password");

                default:
                    return new Sort.Order(Sort.Direction.DESC, "password");

            }
        }
    }
}
