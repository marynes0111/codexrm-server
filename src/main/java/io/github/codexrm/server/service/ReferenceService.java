package io.github.codexrm.server.service;

import io.github.codexrm.server.model.Reference;
import io.github.codexrm.server.model.User;
import io.github.codexrm.server.repository.ReferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ReferenceService {

    private final ReferenceRepository referenceRepository;

    @Autowired
    public ReferenceService(final ReferenceRepository referenceRepository) {
        this.referenceRepository = referenceRepository;
    }

    public List<Reference> findAll(User userId) {
        return referenceRepository.findByUserid(userId);
    }

    public Reference findById(Integer id) {
        return referenceRepository.findById(id).get();
    }

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
        if (reference.getUserid().getId() != findById(reference.getId()).getUserid().getId()) {
            throw new EntityExistsException("There is  entity with such User ID in the database.");
        }

        return referenceRepository.save(reference);
    }

    public void delete(Integer id) {referenceRepository.deleteById(id);}

    public void deleteAll(User userId) {
        List<Reference> list = findAll(userId);
        for (Reference reference:list){
            delete(reference.getId());
        }
    }
}
