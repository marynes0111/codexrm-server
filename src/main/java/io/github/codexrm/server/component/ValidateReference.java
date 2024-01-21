package io.github.codexrm.server.component;

import io.github.codexrm.server.model.*;

public class ValidateReference {

    private final FieldValidations validations;

    public ValidateReference() {
        this.validations = new FieldValidations();
    }

    public void validateArticleReference(ArticleReference article) {

        if (validations.isInvalidateYear(article.getYear()))
            article.setYear(null);

        if (validations.isInvalidateAuthorOrEditor(article.getAuthor()))
            article.setAuthor(null);

        if (validations.isInvalidateChapterOrVolume(article.getVolume()))
            article.setVolume(null);

        if (validations.isInvalidateNumber(article.getNumber()))
            article.setNumber(null);

        if (validations.isInvalidatePages(article.getPages()))
            article.setPages(null);

        if (validations.isInvalidateIssn(article.getIssn()))
            article.setIssn(null);
    }

    public void validateBookLetReference(BookLetReference let) {

        if (validations.isInvalidateYear(let.getYear()))
            let.setYear(null);

        if (validations.isInvalidateAuthorOrEditor(let.getAuthor()))
            let.setAuthor(null);

        if (validations.isInvalidateAddress(let.getAddress()))
            let.setAddress(null);
    }

    public void validateBookReference(BookReference book) {

        if (validations.isInvalidateYear(book.getYear()))
            book.setYear(null);

        if (validations.isInvalidateAuthorOrEditor(book.getAuthor()))
            book.setAuthor(null);

        if (validations.isInvalidateAuthorOrEditor(book.getEditor()))
            book.setEditor(null);

        if (validations.isInvalidateChapterOrVolume(book.getVolume()))
            book.setVolume(null);

        if (validations.isInvalidateNumber(book.getNumber()))
            book.setNumber(null);

        if (validations.isInvalidateSeries(book.getSeries()))
            book.setSeries(null);

        if (validations.isInvalidateAddress(book.getAddress()))
            book.setAddress(null);

        if (validations.isInvalidateEdition(book.getEdition()))
            book.setEdition(null);

        if (validations.isInvalidateIsbn(book.getIsbn()))
            book.setIsbn(null);
    }

    public void validateBookSectionReference(BookSectionReference section) {

        validateBookReference(section);

        if (validations.isInvalidateChapterOrVolume(section.getChapter()))
            section.setChapter(null);

        if (validations.isInvalidatePages(section.getPages()))
            section.setPages(null);
    }

    public void validateConferencePaperReference(ConferencePaperReference paper) {

        if (validations.isInvalidateYear(paper.getYear()))
            paper.setYear(null);

        if (validations.isInvalidateAuthorOrEditor(paper.getAuthor()))
            paper.setAuthor(null);

        if (validations.isInvalidateAuthorOrEditor(paper.getEditor()))
            paper.setEditor(null);

        if (validations.isInvalidateNumber(paper.getNumber()))
            paper.setNumber(null);

        if (validations.isInvalidateSeries(paper.getSeries()))
            paper.setSeries(null);

        if (validations.isInvalidateChapterOrVolume(paper.getVolume()))
            paper.setVolume(null);

        if (validations.isInvalidateAddress(paper.getAddress()))
            paper.setAddress(null);

        if (validations.isInvalidatePages(paper.getPages()))
            paper.setPages(null);
    }

    public void validateConferenceProceedingsReference(ConferenceProceedingReference proceedings) {

        if (validations.isInvalidateYear(proceedings.getYear()))
            proceedings.setYear(null);

        if (validations.isInvalidateAuthorOrEditor(proceedings.getEditor()))
            proceedings.setEditor(null);

        if (validations.isInvalidateChapterOrVolume(proceedings.getVolume()))
            proceedings.setVolume(null);

        if (validations.isInvalidateNumber(proceedings.getNumber()))
            proceedings.setNumber(null);

        if (validations.isInvalidateSeries(proceedings.getSeries()))
            proceedings.setSeries(null);

        if (validations.isInvalidateAddress(proceedings.getAddress()))
            proceedings.setAddress(null);

        if (validations.isInvalidateIsbn(proceedings.getIsbn()))
            proceedings.setIsbn(null);
    }

    public void validateThesisReference(ThesisReference thesis) {

        if (validations.isInvalidateYear(thesis.getYear()))
            thesis.setYear(null);

        if (validations.isInvalidateAuthorOrEditor(thesis.getAuthor()))
            thesis.setAuthor(null);

        if (validations.isInvalidateAddress(thesis.getAddress()))
            thesis.setAddress(null);
    }

    public void validateWebPageReference(WebPageReference webPage) {

        if (validations.isInvalidateYear(webPage.getYear()))
            webPage.setYear(null);

        if (validations.isInvalidateAuthorOrEditor(webPage.getAuthor()))
            webPage.setAuthor(null);

        if (validations.isInvalidateUrl(webPage.getUrl()))
            webPage.setUrl(null);
    }

    public Reference validateRequiredArticle(ArticleReference article) {
        if (article.getAuthor() == null || article.getTitle() == null || article.getJournal() == null || article.getYear() == null ||
                article.getAuthor().equals("") || article.getTitle().equals("") || article.getJournal().equals("") || article.getYear().equals("")) {
            return null;
        } else {
            return article;
        }
    }

    public Reference validateRequiredBookSection(BookSectionReference section) {
        if (section.getChapter() == null || section.getPages() == null || section.getAuthor() == null || section.getEditor() == null || section.getTitle() == null || section.getPublisher() == null || section.getYear() == null ||
                section.getChapter().equals("") || section.getPages().equals("") || section.getAuthor().equals("") || section.getEditor().equals("") || section.getTitle().equals("") || section.getPublisher().equals("") || section.getYear().equals("")) {
            return null;
        } else {
            return section;
        }
    }

    public Reference validateRequiredBook(BookReference book) {
        if (book.getAuthor() == null || book.getEditor() == null || book.getTitle() == null || book.getPublisher() == null || book.getYear() == null ||
                book.getAuthor().equals("") || book.getEditor().equals("") || book.getTitle().equals("") || book.getPublisher().equals("") || book.getYear().equals("")) {
            return null;
        } else {
            return book;
        }
    }

    public Reference validateRequiredBookLet(BookLetReference let) {
        if (let.getTitle() == null || let.getAuthor() == null || let.getTitle().equals("") || let.getAuthor().equals("")) {
            return null;
        } else {
            return let;
        }
    }

    public Reference validateRequiredConferenceProceedings(ConferenceProceedingReference proceedings) {
        if (proceedings.getTitle() == null || proceedings.getYear() == null || proceedings.getTitle().equals("") || proceedings.getYear().equals("")) {
            return null;
        } else {
            return proceedings;
        }
    }

    public Reference validateRequiredConferencePaper(ConferencePaperReference paper) {
        if (paper.getAuthor() == null || paper.getTitle() == null || paper.getBookTitle() == null || paper.getYear() == null ||
                paper.getAuthor().equals("") || paper.getTitle().equals("") || paper.getBookTitle().equals("") || paper.getYear().equals("")) {
            return null;
        } else {
            return paper;
        }
    }

    public Reference validateRequiredThesis(ThesisReference thesis) {
        if (thesis.getAuthor() == null || thesis.getTitle() == null || thesis.getSchool() == null || thesis.getYear() == null ||
                thesis.getAuthor().equals("") || thesis.getTitle().equals("") || thesis.getSchool().equals("") || thesis.getYear().equals("")) {
            return null;
        } else {
            return thesis;
        }
    }
}

