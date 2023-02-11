package io.github.codexrm.server.component;

import io.github.codexrm.server.model.*;
import io.github.codexrm.server.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

        reference.getUser().setReferenceList(new ArrayList<>());

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
        }
        else if(reference.getClass() == ConferencePaperReference.class) {
            return modelMapper.map(reference,ConferencePaperReferenceDTO.class);
        }
        else if(reference.getClass() == WebPageReference.class) {
            return modelMapper.map(reference,WebPageReferenceDTO.class);
        }
        else
            return modelMapper.map(reference,ThesisReferenceDTO.class);
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
        }
        else if(referenceDTO.getClass() == ConferencePaperReferenceDTO.class){
            return modelMapper.map(referenceDTO,ConferencePaperReference.class);
        }
        else if(referenceDTO.getClass() == WebPageReferenceDTO.class){
            return modelMapper.map(referenceDTO,WebPageReference.class);
        }
        else{
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

    public List<Reference> createReferenceList(final List<ReferenceDTO> referenceDTOList) {

        List<Reference> referenceList = new ArrayList<>();
        referenceDTOList.forEach(referenceDTO ->
                referenceList.add(createReference(referenceDTO))
        );
        return referenceList;
    }

    public Reference createReference(final ReferenceDTO referenceDTO) {


        if(referenceDTO.getClass() == ArticleReferenceDTO.class){

            ArticleReferenceDTO articleDTO = (ArticleReferenceDTO) referenceDTO;
            return new ArticleReference(articleDTO.getAuthor(), articleDTO.getTitle(),
                    articleDTO.getLocalDate(), articleDTO.getNote(), articleDTO.getUser(),
                    articleDTO.getJournal(), articleDTO.getVolume(), articleDTO.getNumber(),
                    articleDTO.getPages());
        }
        else if(referenceDTO.getClass() == BookSectionReferenceDTO.class){

            BookSectionReferenceDTO sectionDTO = (BookSectionReferenceDTO) referenceDTO;
            return new BookSectionReference(sectionDTO.getAuthor(), sectionDTO.getTitle(),
                    sectionDTO.getLocalDate(), sectionDTO.getNote(), sectionDTO.getUser(),
                    sectionDTO.getPublisher(), sectionDTO.getVolume(), sectionDTO.getSeries(),
                    sectionDTO.getAddress(), sectionDTO.getEdition(), sectionDTO.getChapter(),
                    sectionDTO.getPages());
        }
        else if(referenceDTO.getClass() == BookReferenceDTO.class){

            BookReferenceDTO bookDTO = (BookReferenceDTO) referenceDTO;
            return new BookReference(bookDTO.getAuthor(), bookDTO.getTitle(), bookDTO.getLocalDate(),
                    bookDTO.getNote(), bookDTO.getUser(), bookDTO.getPublisher(),
                    bookDTO.getVolume(), bookDTO.getSeries(), bookDTO.getAddress(), bookDTO.getEdition());
        }
        else if(referenceDTO.getClass() == BookLetReferenceDTO.class){

            BookLetReferenceDTO letDTO = (BookLetReferenceDTO) referenceDTO;
            return new BookLetReference(letDTO.getAuthor(), letDTO.getTitle(), letDTO.getLocalDate(),
                    letDTO.getNote(), letDTO.getUser(), letDTO.getHowpublished(),
                    letDTO.getAddress());
        }
        else if(referenceDTO.getClass() == ConferenceProceedingsReferenceDTO.class){

            ConferenceProceedingsReferenceDTO proceedingsDTO = (ConferenceProceedingsReferenceDTO) referenceDTO;
            return new ConferenceProceedingReference(proceedingsDTO.getAuthor(), proceedingsDTO.getTitle(),
                    proceedingsDTO.getLocalDate(), proceedingsDTO.getNote(), proceedingsDTO.getUser(),
                    proceedingsDTO.getVolume(), proceedingsDTO.getSeries(), proceedingsDTO.getAddress());
        }
        else if(referenceDTO.getClass() == ConferencePaperReferenceDTO.class){

            ConferencePaperReferenceDTO paperDTO = (ConferencePaperReferenceDTO) referenceDTO;
            return new ConferencePaperReference(paperDTO.getAuthor(), paperDTO.getTitle(),
                    paperDTO.getLocalDate(), paperDTO.getNote(), paperDTO.getUser(),
                    paperDTO.getPublisher(), paperDTO.getVolume(), paperDTO.getAddress(),
                    paperDTO.getPages());
        }
        else if(referenceDTO.getClass() == WebPageReferenceDTO.class){

            WebPageReferenceDTO webDTO = (WebPageReferenceDTO) referenceDTO;
            return new WebPageReference(webDTO.getAuthor(), webDTO.getTitle(), webDTO.getLocalDate(),
                    webDTO.getNote(), webDTO.getUser(), webDTO.getUrl(), webDTO.getAccessDateLocal());
        }
        else{
            ThesisReferenceDTO thesisDTO = (ThesisReferenceDTO) referenceDTO;
            return new ThesisReference(thesisDTO.getAuthor(), thesisDTO.getTitle(), thesisDTO.getLocalDate(),
                    thesisDTO.getNote(), thesisDTO.getUser(), thesisDTO.getSchool(),
                    thesisDTO.getType(), thesisDTO.getAddress());
        }
    }


    //User
    public List<UserDTO> toUserDTOList(final List<User> userList) {

        List<UserDTO> userDTOList = new ArrayList<>();
        userList.forEach(user ->
                userDTOList.add(toUserDTO(user))
        );
        return userDTOList;
    }

    public UserDTO toUserDTO(final User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User toUser(final UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
