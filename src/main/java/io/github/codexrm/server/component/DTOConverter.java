package io.github.codexrm.server.component;

import io.github.codexrm.server.dto.*;
import io.github.codexrm.server.enums.ERole;
import io.github.codexrm.server.model.*;
import io.github.codexrm.server.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DTOConverter {

    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final ValidateReference validation;

    @Autowired
    public DTOConverter(ModelMapper modelMapper, RoleRepository roleRepository) {
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
        this.validation = new ValidateReference();
    }

    //Reference
    public ReferenceDTO toReferenceDTO(final Reference reference) {

        reference.getUser().setReferenceList(new ArrayList<>());

        if (reference.getClass() == ArticleReference.class) {
            return modelMapper.map(reference, ArticleReferenceDTO.class);
        } else if (reference.getClass() == BookSectionReference.class) {
            return modelMapper.map(reference, BookSectionReferenceDTO.class);
        } else if (reference.getClass() == BookReference.class) {
            return modelMapper.map(reference, BookReferenceDTO.class);
        } else if (reference.getClass() == BookLetReference.class) {
            return modelMapper.map(reference, BookLetReferenceDTO.class);
        } else if (reference.getClass() == ConferenceProceedingReference.class) {
            return modelMapper.map(reference, ConferenceProceedingsReferenceDTO.class);
        } else if (reference.getClass() == ConferencePaperReference.class) {
            return modelMapper.map(reference, ConferencePaperReferenceDTO.class);
        } else if (reference.getClass() == WebPageReference.class) {
            return modelMapper.map(reference, WebPageReferenceDTO.class);
        } else
            return modelMapper.map(reference, ThesisReferenceDTO.class);
    }

    public Reference toReference(final ReferenceDTO referenceDTO, User user) {

        Reference reference;

        if (referenceDTO.getClass() == ArticleReferenceDTO.class) {

            validation.validateArticleReferenceDTO((ArticleReferenceDTO) referenceDTO);
            ArticleReference article = modelMapper.map(referenceDTO, ArticleReference.class);
            reference = validation.validateRequiredArticle(article);

        } else if (referenceDTO.getClass() == BookSectionReferenceDTO.class) {

            validation.validateBookSectionReferenceDTO((BookSectionReferenceDTO) referenceDTO);
            BookSectionReference section = modelMapper.map(referenceDTO, BookSectionReference.class);
            reference = validation.validateRequiredBookSection(section);

        } else if (referenceDTO.getClass() == BookReferenceDTO.class) {

            validation.validateBookReferenceDTO((BookReferenceDTO) referenceDTO);
            BookReference book = modelMapper.map(referenceDTO, BookReference.class);
            reference = validation.validateRequiredBook(book);

        } else if (referenceDTO.getClass() == BookLetReferenceDTO.class) {

            validation.validateBookLetReferenceDTO((BookLetReferenceDTO) referenceDTO);
            BookLetReference let = modelMapper.map(referenceDTO, BookLetReference.class);
            reference = validation.validateRequiredBookLet(let);

        } else if (referenceDTO.getClass() == ConferenceProceedingsReferenceDTO.class) {

            validation.validateConferenceProceedingsReferenceDTO((ConferenceProceedingsReferenceDTO) referenceDTO);
            ConferenceProceedingReference proceedings = modelMapper.map(referenceDTO, ConferenceProceedingReference.class);
            reference = validation.validateRequiredConferenceProceedings(proceedings);

        } else if (referenceDTO.getClass() == ConferencePaperReferenceDTO.class) {

            validation.validateConferencePaperReferenceDTO((ConferencePaperReferenceDTO) referenceDTO);
            ConferencePaperReference paper = modelMapper.map(referenceDTO, ConferencePaperReference.class);
            reference = validation.validateRequiredConferencePaper(paper);

        } else if (referenceDTO.getClass() == WebPageReferenceDTO.class) {

            validation.validateWebPageReferenceDTO((WebPageReferenceDTO) referenceDTO);
            reference = modelMapper.map(referenceDTO, WebPageReference.class);

        } else {

            validation.validateThesisReferenceDTO((ThesisReferenceDTO) referenceDTO);
            ThesisReference thesis = modelMapper.map(referenceDTO, ThesisReference.class);
            reference = validation.validateRequiredThesis(thesis);
        }
        reference.setUser(user);
        return reference;
    }

    public List<ReferenceDTO> toReferenceDTOList(final List<Reference> referenceList) {

        List<ReferenceDTO> referenceDTOList = new ArrayList<>();
        referenceList.forEach(reference ->
                referenceDTOList.add(toReferenceDTO(reference))
        );
        return referenceDTOList;
    }

    public List<Reference> toReferenceList(final List<ReferenceDTO> referenceDTOList, User user) {

        List<Reference> referenceList = new ArrayList<>();
        for (ReferenceDTO referenceDTO : referenceDTOList) {
            Reference reference = toReference(referenceDTO, user);
            if (reference != null)
                referenceList.add(reference);
        }
        return referenceList;
    }

    public Reference createReference(final ReferenceDTO referenceDTO, User user) {

        if (referenceDTO.getClass() == ArticleReferenceDTO.class) {

            ArticleReferenceDTO articleDTO = (ArticleReferenceDTO) referenceDTO;
            validation.validateArticleReferenceDTO(articleDTO);
            ArticleReference article = new ArticleReference(articleDTO.getTitle(), articleDTO.getYear(), articleDTO.getMonth(), articleDTO.getNote(), user,
                    articleDTO.getAuthor(), articleDTO.getJournal(), articleDTO.getVolume(), articleDTO.getNumber(), articleDTO.getPages(), articleDTO.getIssn());
            return validation.validateRequiredArticle(article);

        } else if (referenceDTO.getClass() == BookSectionReferenceDTO.class) {

            BookSectionReferenceDTO sectionDTO = (BookSectionReferenceDTO) referenceDTO;
            validation.validateBookSectionReferenceDTO(sectionDTO);
            BookSectionReference section = new BookSectionReference(sectionDTO.getTitle(), sectionDTO.getYear(), sectionDTO.getMonth(), sectionDTO.getNote(), user,
                    sectionDTO.getAuthor(), sectionDTO.getEditor(), sectionDTO.getPublisher(), sectionDTO.getVolume(), sectionDTO.getNumber(), sectionDTO.getSeries(), sectionDTO.getAddress(), sectionDTO.getEdition(), sectionDTO.getIsbn(),
                    sectionDTO.getChapter(), sectionDTO.getPages(), sectionDTO.getType());
            return validation.validateRequiredBookSection(section);

        } else if (referenceDTO.getClass() == BookReferenceDTO.class) {

            BookReferenceDTO bookDTO = (BookReferenceDTO) referenceDTO;
            validation.validateBookReferenceDTO(bookDTO);
            BookReference book = new BookReference(bookDTO.getTitle(), bookDTO.getYear(), bookDTO.getMonth(), bookDTO.getNote(), user,
                    bookDTO.getAuthor(), bookDTO.getEditor(), bookDTO.getPublisher(), bookDTO.getVolume(), bookDTO.getNumber(), bookDTO.getSeries(), bookDTO.getAddress(), bookDTO.getEdition(), bookDTO.getIsbn());
            return validation.validateRequiredBook(book);

        } else if (referenceDTO.getClass() == BookLetReferenceDTO.class) {

            BookLetReferenceDTO letDTO = (BookLetReferenceDTO) referenceDTO;
            validation.validateBookLetReferenceDTO(letDTO);
            BookLetReference let = new BookLetReference(letDTO.getTitle(), letDTO.getYear(), letDTO.getMonth(), letDTO.getNote(), user,
                    letDTO.getAuthor(), letDTO.getHowpublished(), letDTO.getAddress());
            return validation.validateRequiredBookLet(let);

        } else if (referenceDTO.getClass() == ConferenceProceedingsReferenceDTO.class) {

            ConferenceProceedingsReferenceDTO proceedingsDTO = (ConferenceProceedingsReferenceDTO) referenceDTO;
            validation.validateConferenceProceedingsReferenceDTO(proceedingsDTO);
            ConferenceProceedingReference proceedings = new ConferenceProceedingReference(proceedingsDTO.getTitle(), proceedingsDTO.getYear(), proceedingsDTO.getMonth(), proceedingsDTO.getNote(), user,
                    proceedingsDTO.getEditor(), proceedingsDTO.getVolume(), proceedingsDTO.getNumber(), proceedingsDTO.getSeries(), proceedingsDTO.getAddress(), proceedingsDTO.getPublisher(), proceedingsDTO.getIsbn(), proceedingsDTO.getOrganization());
            return validation.validateRequiredConferenceProceedings(proceedings);

        } else if (referenceDTO.getClass() == ConferencePaperReferenceDTO.class) {

            ConferencePaperReferenceDTO paperDTO = (ConferencePaperReferenceDTO) referenceDTO;
            validation.validateConferencePaperReferenceDTO(paperDTO);
            ConferencePaperReference paper = new ConferencePaperReference(paperDTO.getTitle(), paperDTO.getYear(), paperDTO.getMonth(), paperDTO.getNote(), user,
                    paperDTO.getAuthor(), paperDTO.getBookTitle(), paperDTO.getEditor(), paperDTO.getNumber(), paperDTO.getSeries(), paperDTO.getPublisher(), paperDTO.getVolume(), paperDTO.getAddress(), paperDTO.getPages(), paperDTO.getOrganization());
            return validation.validateRequiredConferencePaper(paper);

        } else if (referenceDTO.getClass() == WebPageReferenceDTO.class) {

            WebPageReferenceDTO webDTO = (WebPageReferenceDTO) referenceDTO;
            validation.validateWebPageReferenceDTO(webDTO);
            return new WebPageReference(webDTO.getTitle(), webDTO.getYear(), webDTO.getMonth(), webDTO.getNote(), user,
                    webDTO.getAuthor(), webDTO.getUrl());

        } else {

            ThesisReferenceDTO thesisDTO = (ThesisReferenceDTO) referenceDTO;
            validation.validateThesisReferenceDTO(thesisDTO);
            ThesisReference thesis = new ThesisReference(thesisDTO.getTitle(), thesisDTO.getYear(), thesisDTO.getMonth(), thesisDTO.getNote(), user,
                    thesisDTO.getAuthor(), thesisDTO.getSchool(), thesisDTO.getType(), thesisDTO.getAddress());
            return validation.validateRequiredThesis(thesis);
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

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.getRoles().clear();
        for (Role r : user.getRoles()) {
            userDTO.setRol(r.getName().toString());
        }
        return userDTO;
    }

    public User toUser(final UserDTO userDTO) {

        User user = modelMapper.map(userDTO, User.class);
        user.getRoles().clear();
        for (String role : userDTO.getRoles()) {
            switch (role) {
                case "ROLE_ADMIN":
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    user.setRole(adminRole);

                    break;
                case "ROLE_MANAGER":
                    Role managerRole = roleRepository.findByName(ERole.ROLE_MANAGER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    user.setRole(managerRole);

                    break;

                case "ROLE_AUDITOR":
                    Role auditorRole = roleRepository.findByName(ERole.ROLE_AUDITOR)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    user.setRole(auditorRole);

                    break;
                default:
                    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    user.setRole(userRole);
            }
        }
        return user;
    }
}
