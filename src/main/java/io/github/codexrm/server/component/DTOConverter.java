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

            ArticleReference article = modelMapper.map(referenceDTO, ArticleReference.class);
            validation.validateArticleReference(article);
            reference = validation.validateRequiredArticle(article);

        } else if (referenceDTO.getClass() == BookSectionReferenceDTO.class) {

            BookSectionReference section = modelMapper.map(referenceDTO, BookSectionReference.class);
            validation.validateBookSectionReference(section);
            reference = validation.validateRequiredBookSection(section);

        } else if (referenceDTO.getClass() == BookReferenceDTO.class) {

            BookReference book = modelMapper.map(referenceDTO, BookReference.class);
            validation.validateBookReference(book);
            reference = validation.validateRequiredBook(book);

        } else if (referenceDTO.getClass() == BookLetReferenceDTO.class) {

            BookLetReference let = modelMapper.map(referenceDTO, BookLetReference.class);
            validation.validateBookLetReference(let);
            reference = validation.validateRequiredBookLet(let);

        } else if (referenceDTO.getClass() == ConferenceProceedingsReferenceDTO.class) {

            ConferenceProceedingReference proceedings = modelMapper.map(referenceDTO, ConferenceProceedingReference.class);
            validation.validateConferenceProceedingsReference(proceedings);
            reference = validation.validateRequiredConferenceProceedings(proceedings);

        } else if (referenceDTO.getClass() == ConferencePaperReferenceDTO.class) {

            ConferencePaperReference paper = modelMapper.map(referenceDTO, ConferencePaperReference.class);
            validation.validateConferencePaperReference(paper);
            reference = validation.validateRequiredConferencePaper(paper);

        } else if (referenceDTO.getClass() == WebPageReferenceDTO.class) {

            WebPageReference webPage = modelMapper.map(referenceDTO, WebPageReference.class);
            validation.validateWebPageReference(webPage);
            reference = webPage;

        } else {

            ThesisReference thesis = modelMapper.map(referenceDTO, ThesisReference.class);
            validation.validateThesisReference(thesis);
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
            ArticleReference article = new ArticleReference(articleDTO.getTitle(), articleDTO.getYear(), articleDTO.getMonth(), articleDTO.getNote(), user,
                    articleDTO.getAuthor(), articleDTO.getJournal(), articleDTO.getVolume(), articleDTO.getNumber(), articleDTO.getPages(), articleDTO.getIssn());

            validation.validateArticleReference(article);
            return validation.validateRequiredArticle(article);

        } else if (referenceDTO.getClass() == BookSectionReferenceDTO.class) {

            BookSectionReferenceDTO sectionDTO = (BookSectionReferenceDTO) referenceDTO;
            BookSectionReference section = new BookSectionReference(sectionDTO.getTitle(), sectionDTO.getYear(), sectionDTO.getMonth(), sectionDTO.getNote(), user,
                    sectionDTO.getAuthor(), sectionDTO.getEditor(), sectionDTO.getPublisher(), sectionDTO.getVolume(), sectionDTO.getNumber(), sectionDTO.getSeries(), sectionDTO.getAddress(), sectionDTO.getEdition(), sectionDTO.getIsbn(),
                    sectionDTO.getChapter(), sectionDTO.getPages(), sectionDTO.getType());

            validation.validateBookSectionReference(section);
            return validation.validateRequiredBookSection(section);

        } else if (referenceDTO.getClass() == BookReferenceDTO.class) {

            BookReferenceDTO bookDTO = (BookReferenceDTO) referenceDTO;
            BookReference book = new BookReference(bookDTO.getTitle(), bookDTO.getYear(), bookDTO.getMonth(), bookDTO.getNote(), user,
                    bookDTO.getAuthor(), bookDTO.getEditor(), bookDTO.getPublisher(), bookDTO.getVolume(), bookDTO.getNumber(), bookDTO.getSeries(), bookDTO.getAddress(), bookDTO.getEdition(), bookDTO.getIsbn());

            validation.validateBookReference(book);
            return validation.validateRequiredBook(book);

        } else if (referenceDTO.getClass() == BookLetReferenceDTO.class) {

            BookLetReferenceDTO letDTO = (BookLetReferenceDTO) referenceDTO;
            BookLetReference let = new BookLetReference(letDTO.getTitle(), letDTO.getYear(), letDTO.getMonth(), letDTO.getNote(), user,
                    letDTO.getAuthor(), letDTO.getHowpublished(), letDTO.getAddress());

            validation.validateBookLetReference(let);
            return validation.validateRequiredBookLet(let);

        } else if (referenceDTO.getClass() == ConferenceProceedingsReferenceDTO.class) {

            ConferenceProceedingsReferenceDTO proceedingsDTO = (ConferenceProceedingsReferenceDTO) referenceDTO;
            ConferenceProceedingReference proceedings = new ConferenceProceedingReference(proceedingsDTO.getTitle(), proceedingsDTO.getYear(), proceedingsDTO.getMonth(), proceedingsDTO.getNote(), user,
                    proceedingsDTO.getEditor(), proceedingsDTO.getVolume(), proceedingsDTO.getNumber(), proceedingsDTO.getSeries(), proceedingsDTO.getAddress(), proceedingsDTO.getPublisher(), proceedingsDTO.getIsbn(), proceedingsDTO.getOrganization());

            validation.validateConferenceProceedingsReference(proceedings);
            return validation.validateRequiredConferenceProceedings(proceedings);

        } else if (referenceDTO.getClass() == ConferencePaperReferenceDTO.class) {

            ConferencePaperReferenceDTO paperDTO = (ConferencePaperReferenceDTO) referenceDTO;
            ConferencePaperReference paper = new ConferencePaperReference(paperDTO.getTitle(), paperDTO.getYear(), paperDTO.getMonth(), paperDTO.getNote(), user,
                    paperDTO.getAuthor(), paperDTO.getBookTitle(), paperDTO.getEditor(), paperDTO.getNumber(), paperDTO.getSeries(), paperDTO.getPublisher(), paperDTO.getVolume(), paperDTO.getAddress(), paperDTO.getPages(), paperDTO.getOrganization());

            validation.validateConferencePaperReference(paper);
            return validation.validateRequiredConferencePaper(paper);

        } else if (referenceDTO.getClass() == WebPageReferenceDTO.class) {

            WebPageReferenceDTO webDTO = (WebPageReferenceDTO) referenceDTO;
            WebPageReference webPage = new WebPageReference(webDTO.getTitle(), webDTO.getYear(), webDTO.getMonth(), webDTO.getNote(), user, webDTO.getAuthor(), webDTO.getUrl());

            validation.validateWebPageReference(webPage);
            return webPage;

        } else {

            ThesisReferenceDTO thesisDTO = (ThesisReferenceDTO) referenceDTO;
            ThesisReference thesis = new ThesisReference(thesisDTO.getTitle(), thesisDTO.getYear(), thesisDTO.getMonth(), thesisDTO.getNote(), user,
                    thesisDTO.getAuthor(), thesisDTO.getSchool(), thesisDTO.getType(), thesisDTO.getAddress());
            validation.validateThesisReference(thesis);
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
