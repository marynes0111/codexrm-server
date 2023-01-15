package io.github.codexrm.server.service;

import io.github.codexrm.server.model.Reference;
import io.github.codexrm.server.model.User;
import io.github.codexrm.server.repository.ReferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReferenceService {

    private final ReferenceRepository referenceRepository;

    @Autowired
    public ReferenceService(final ReferenceRepository referenceRepository) {
        this.referenceRepository = referenceRepository;
    }

    public List<Reference> findAll(User user) { return referenceRepository.findByUser(user); }

    public Reference findById(Integer id) {
        return referenceRepository.findById(id).get();
    }

    public List<Reference> findByAuthor(String author, User user) { return referenceRepository.findByAuthorAndUser(author, user); }

    public List<Reference> findByTitle(String title, User user) { return referenceRepository.findByTitleAndUser(title, user); }

    public Reference save(Reference reference) {
        if (reference.getId() != null && referenceRepository.existsById(reference.getId())) {
            throw new EntityExistsException("There is already existing entity with such ID in the database.");
        }
        return referenceRepository.save(reference);
    }

    public Reference update(Reference reference) {
        if (reference.getId() != null && !referenceRepository.existsById(reference.getId())) {
            throw new EntityNotFoundException("There is no entity with such ID in the database.");
        }

        return referenceRepository.save(reference);
    }

    public void delete(Integer id) {referenceRepository.deleteById(id);}

    public void deleteAll(User user) {
        List<Reference> list = findAll(user);
        for (Reference reference:list){
            delete(reference.getId());
        }
    }

    public void deleteGroup(ArrayList<Integer> idList) {
        for (Integer id :idList){
            delete(id);
        }
    }
}
