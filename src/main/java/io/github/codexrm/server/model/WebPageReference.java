package io.github.codexrm.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "webpagereference")
public class WebPageReference extends Reference {

    @Column(name = "author")
    private String author;

    @Column(name = "howpublished")
    private String howpublished;


    public WebPageReference() {}

    public WebPageReference(String title, String year, String month, String note, User user, String author, String howpublished) {
        super(title, year, month, note, user);
        this.author = author;
        this.howpublished = howpublished;
    }

    public String getAuthor() { return author; }

    public void setAuthor(String author) { this.author = author; }

    public String getHowpublished() { return howpublished; }

    public void setHowpublished(String howpublished) { this.howpublished = howpublished; }
}
