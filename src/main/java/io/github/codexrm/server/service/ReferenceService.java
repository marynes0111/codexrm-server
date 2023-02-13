package io.github.codexrm.server.service;

import io.github.codexrm.server.enums.SortReference;
import io.github.codexrm.server.model.Reference;
import io.github.codexrm.server.model.User;
import io.github.codexrm.server.repository.ReferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ReferenceService {

    @Autowired
    private UserService userService;

    private final ReferenceRepository referenceRepository;

    @Autowired
    public ReferenceService(final ReferenceRepository referenceRepository) {
        this.referenceRepository = referenceRepository;
    }

    public Page<Reference> getAll(User user, String author, String title, int page, int size, SortReference sort) {

        Sort.Order order = getOrder(sort);
        Pageable pagingSort = PageRequest.of(page, size, Sort.by(order));

        if (author == null && title == null) {
            return referenceRepository.findByUser(user, pagingSort);
        }
        else{
            if(author == null && title != null){
                return referenceRepository.findByUserAndTitleContaining(user, title, pagingSort);
            }
            else{
                if(author != null && title == null){
                    return referenceRepository.findByUserAndAuthorContaining(user, author, pagingSort);
                }
                else{
                    return referenceRepository.findByUserAndAuthorContainingOrTitleContaining(user, author,title,pagingSort);
                }
            }
        }
    }

    public Reference get(Integer id) {
        return referenceRepository.findById(id).get();
    }

    public Reference add(Reference reference) {
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

    public void delete(Integer id) {
        referenceRepository.deleteById(id);
    }

    public void sync(List<Reference> newReferenceList, List<Reference> updateReferenceList, List<Integer> deleteReferenceList) {

        for(Reference reference: newReferenceList){
            add(reference);
        }
        for(Reference reference: updateReferenceList){
            update(reference);
        }
        for(Integer id: deleteReferenceList){
            delete(id);
        }
    }

    private Sort.Order getOrder(SortReference sort) {

        if (sort == null) {
            return new Sort.Order(Sort.Direction.ASC, "id");

        }else {
            switch (sort) {
                case idAsc:
                    return new Sort.Order(Sort.Direction.ASC, "id");

                case idDesc:
                    return new Sort.Order(Sort.Direction.DESC, "id");

                case authorAsc:
                    return new Sort.Order(Sort.Direction.ASC, "author");

                case authorDesc:
                    return new Sort.Order(Sort.Direction.DESC, "author");

                case titleAsc:
                    return new Sort.Order(Sort.Direction.ASC, "title");

                case titleDesc:
                    return new Sort.Order(Sort.Direction.DESC, "title");

                case dateAsc:
                    return new Sort.Order(Sort.Direction.ASC, "date");

                case dateDesc:
                    return new Sort.Order(Sort.Direction.DESC, "date");

                case noteAsc:
                    return new Sort.Order(Sort.Direction.ASC, "note");

                default:
                    return new Sort.Order(Sort.Direction.DESC, "note");

            }
        }
    }
}
