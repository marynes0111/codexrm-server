package io.github.codexrm.server.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "articlereference")
public class ArticleReference extends Reference {

    @Column(name = "journal")
    private String journal;

    @Column(name = "volume")
    private String volume;

    @Column(name = "numbera")
    private String number;

    @Column(name = "pages")
    private String pages;

    public ArticleReference() {}

    public ArticleReference(String author, String title, LocalDate date, String note, User userid, String journal, String volume, String number, String pages) {
        super(author, title, date, note, userid);
        this.journal = journal;
        this.volume = volume;
        this.number = number;
        this.pages = pages;
    }

    public String getPages() {return pages;}

    public void setPages(String pages) {this.pages = pages;}

    public String getNumber() {return number;}

    public void setNumber(String number) {this.number = number;}

    public String getVolume() {return volume;}

    public void setVolume(String volume) {this.volume = volume;}

    public String getJournal() {return journal;}

    public void setJournal(String journal) {this.journal = journal;}
}