package io.github.codexrm.server.Model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "booksectionreference")
public class BookSectionReference extends BookReference{

    @Column(name = "chapter")
    private String chapter;

    @Column(name = "pages")
    private String pages;

    public BookSectionReference() {}

    public BookSectionReference(String author, String title, LocalDate date, String note, User userId, String publisher, String volume, String series, String address, String edition, String chapter, String pages) {
        super( author, title, date, note, userId, publisher, volume, series, address, edition);
        this.chapter = chapter;
        this.pages = pages;
    }

    public String getPages() {return pages;}

    public void setPages(String pages) {this.pages = pages;}

    public String getChapter() {return chapter;}

    public void setChapter(String chapter) {this.chapter = chapter;}

}