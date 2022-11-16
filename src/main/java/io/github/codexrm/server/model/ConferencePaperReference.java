package io.github.codexrm.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "conferencepaperreference")
public class ConferencePaperReference extends Reference {

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "volume")
    private String volume;

    @Column(name = "address")
    private String address;

    @Column(name = "pages")
    private String pages;

    public ConferencePaperReference() {}

    public ConferencePaperReference(String author, String title, LocalDate date, String note, User userid, String publisher, String volume, String address, String pages) {
        super(author, title, date, note, userid);
        this.publisher = publisher;
        this.volume = volume;
        this.address = address;
        this.pages = pages;
    }

    public String getPublisher() { return publisher; }

    public void setPublisher(String publisher) { this.publisher = publisher; }

    public String getVolume() { return volume; }

    public void setVolume(String volume) { this.volume = volume; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getPages() { return pages; }

    public void setPages(String pages) { this.pages = pages; }
}
