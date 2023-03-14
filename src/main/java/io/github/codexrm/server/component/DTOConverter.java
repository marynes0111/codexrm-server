package io.github.codexrm.server.component;

import io.github.codexrm.server.model.*;
import io.github.codexrm.server.dto.*;
import org.modelmapper.ModelMapper;
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
            return new ArticleReference(articleDTO.getTitle(),articleDTO.getYear(),articleDTO.getMonth(),articleDTO.getNote(),articleDTO.getUser(),
                    articleDTO.getAuthor(),articleDTO.getJournal(),articleDTO.getVolume(),articleDTO.getNumber(),articleDTO.getPages(), articleDTO.getIssn());
        }
        else if(referenceDTO.getClass() == BookSectionReferenceDTO.class){

            BookSectionReferenceDTO sectionDTO = (BookSectionReferenceDTO) referenceDTO;
            return new BookSectionReference(sectionDTO.getTitle(), sectionDTO.getYear(), sectionDTO.getMonth(), sectionDTO.getNote(), sectionDTO.getUser(),
                    sectionDTO.getAuthor(), sectionDTO.getEditor(), sectionDTO.getPublisher(), sectionDTO.getVolume(), sectionDTO.getNumber(), sectionDTO.getSeries(), sectionDTO.getAddress(), sectionDTO.getEdition(), sectionDTO.getIsbn(),
                    sectionDTO.getChapter(), sectionDTO.getPages(), sectionDTO.getType());
        }
        else if(referenceDTO.getClass() == BookReferenceDTO.class){

            BookReferenceDTO bookDTO = (BookReferenceDTO) referenceDTO;
            return new BookReference(bookDTO.getTitle(), bookDTO.getYear(), bookDTO.getMonth(), bookDTO.getNote(), bookDTO.getUser(),
                    bookDTO.getAuthor(), bookDTO.getEditor(), bookDTO.getPublisher(), bookDTO.getVolume(), bookDTO.getNumber(), bookDTO.getSeries(), bookDTO.getAddress(), bookDTO.getEdition(), bookDTO.getIsbn() );
        }
        else if(referenceDTO.getClass() == BookLetReferenceDTO.class){

            BookLetReferenceDTO letDTO = (BookLetReferenceDTO) referenceDTO;
            return new BookLetReference(letDTO.getTitle(), letDTO.getYear(), letDTO.getMonth(), letDTO.getNote(), letDTO.getUser(),
                    letDTO.getAuthor(), letDTO.getHowpublished(), letDTO.getAddress());
        }
        else if(referenceDTO.getClass() == ConferenceProceedingsReferenceDTO.class){

            ConferenceProceedingsReferenceDTO proceedingsDTO = (ConferenceProceedingsReferenceDTO) referenceDTO;
            return new ConferenceProceedingReference(proceedingsDTO.getTitle(), proceedingsDTO.getYear(), proceedingsDTO.getMonth(), proceedingsDTO.getNote(), proceedingsDTO.getUser(),
                    proceedingsDTO.getEditor(), proceedingsDTO.getVolume(), proceedingsDTO.getNumber(), proceedingsDTO.getSeries(), proceedingsDTO.getAddress(), proceedingsDTO.getPublisher(), proceedingsDTO.getIsbn(), proceedingsDTO.getOrganization());
        }
        else if(referenceDTO.getClass() == ConferencePaperReferenceDTO.class){

            ConferencePaperReferenceDTO paperDTO = (ConferencePaperReferenceDTO) referenceDTO;
            return new ConferencePaperReference(paperDTO.getTitle(), paperDTO.getYear(), paperDTO.getMonth(), paperDTO.getNote(), paperDTO.getUser(),
                    paperDTO.getAuthor(), paperDTO.getBookTitle(), paperDTO.getEditor(), paperDTO.getNumber(), paperDTO.getSeries(), paperDTO.getPublisher(), paperDTO.getVolume(), paperDTO.getAddress(), paperDTO.getPages(), paperDTO.getOrganization());
        }
        else if(referenceDTO.getClass() == WebPageReferenceDTO.class){

            WebPageReferenceDTO webDTO = (WebPageReferenceDTO) referenceDTO;
            return new WebPageReference(webDTO.getTitle(), webDTO.getYear(), webDTO.getMonth(), webDTO.getNote(), webDTO.getUser(),
                    webDTO.getAuthor(), webDTO.getHowpublished());
        }
        else{
            ThesisReferenceDTO thesisDTO = (ThesisReferenceDTO) referenceDTO;
            return new ThesisReference(thesisDTO.getTitle(), thesisDTO.getYear(), thesisDTO.getMonth(), thesisDTO.getNote(), thesisDTO.getUser(),
                    thesisDTO.getAuthor(), thesisDTO.getSchool(), thesisDTO.getType(), thesisDTO.getAddress());
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
