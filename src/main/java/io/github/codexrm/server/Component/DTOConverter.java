package io.github.codexrm.server.Component;

import io.github.codexrm.server.Model.*;
import io.github.codexrm.server.dto.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DTOConverter {


    private final ModelMapper modelMapper;

    @Autowired
    public DTOConverter(ModelMapper modelMapper) {this.modelMapper = modelMapper;}


    //Reference
    public ReferenceDTO toReferenceDTO(final Reference reference) {

        if(reference.getClass() == ArticleReference.class){
            return modelMapper.map(reference,ArticleReferenceDTO.class);
        }
        else if(reference.getClass() == BookSectionReference.class){
            return modelMapper.map(reference,BookSectionReferenceDTO.class);
        }
        else if(reference.getClass() == BookReference.class){
            return modelMapper.map(reference,BookReferenceDTO.class);
        }
        else if(reference.getClass() == BookLetReference.class){
            return modelMapper.map(reference,BookLetReferenceDTO.class);
        }
        else if(reference.getClass() == ConferenceProceedingReference.class){
            return modelMapper.map(reference,ConferenceProceedingsReferenceDTO.class);
        }else{
            return modelMapper.map(reference,ThesisReferenceDTO.class);
        }

    }

    public Reference toReference(final ReferenceDTO referenceDTO) {

        if(referenceDTO.getClass() == ArticleReferenceDTO.class){
            return modelMapper.map(referenceDTO,ArticleReference.class);
        }
        else if(referenceDTO.getClass() == BookSectionReferenceDTO.class){
            return modelMapper.map(referenceDTO,BookSectionReference.class);
        }
        else if(referenceDTO.getClass() == BookReferenceDTO.class){
            return modelMapper.map(referenceDTO,BookReference.class);
        }
        else if(referenceDTO.getClass() == BookLetReferenceDTO.class){
            return modelMapper.map(referenceDTO,BookLetReference.class);
        }
        else if(referenceDTO.getClass() == ConferenceProceedingsReferenceDTO.class){
            return modelMapper.map(referenceDTO,ConferenceProceedingReference.class);
        }else{
            return modelMapper.map(referenceDTO,ThesisReference.class);
        }
    }

    public List<ReferenceDTO> toReferenceDTOList(final List<Reference> referenceList) {
        List<ReferenceDTO> referenceDTOList = new ArrayList<>();
        referenceList.forEach(reference ->
                referenceDTOList.add(toReferenceDTO(reference))
        );
        return referenceDTOList;
    }

    public List<Reference> toReferenceList(final List<ReferenceDTO> referenceDTOList) {
        List<Reference> referenceList = new ArrayList<>();
        referenceDTOList.forEach(referenceDTO ->
                referenceList.add(toReference(referenceDTO))
        );
        return referenceList;
    }

    //User
    public List<UserDTO> toUserDTOList(final List<User> userList) {
        List<UserDTO> userDTOList = new ArrayList<>();
        userList.forEach(user ->
                userDTOList.add(toUserDTO(user))
        );
        return userDTOList;
    }
    public List<User> toUserList(final List<UserDTO> userDTOList) {
        List<User> userList = new ArrayList<>();
        userDTOList.forEach(userDTO ->
                userList.add(toUser(userDTO))
        );
        return userList;
    }

    public UserDTO toUserDTO(final User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User toUser(final UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

}
