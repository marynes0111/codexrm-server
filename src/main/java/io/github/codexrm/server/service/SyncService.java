package io.github.codexrm.server.service;

import io.github.codexrm.server.model.Reference;
import io.github.codexrm.server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SyncService {

    @Autowired
    private ReferenceService referenceService;
    @Autowired
    private UserService userService;

   /* public List<Reference> sync(List<Reference> newReferenceList, List<Reference> updateReferenceList, List<Integer> deleteReferenceList, Integer idUser) {

        User user = userService.get(idUser);

        for(Reference reference: newReferenceList){
            referenceService.add(reference);
        }
        for(Reference reference: updateReferenceList){
            referenceService.update(reference);
        }
        for(Integer id: deleteReferenceList){
          //  referenceService.delete(id);
        }
        //return referenceService.getAll(user);
    }*/
}
