package io.github.codexrm.server.Model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "bookreference")
@Inheritance(strategy = InheritanceType.JOINED)
public class BookReference extends Reference{

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "volume")
    private String volume;

    @Column(name = "series")
    private String series;

    @Column(name = "address")
    private String address;

    @Column(name = "edition")
    private String edition;

    public BookReference() {}

    public BookReference( String author, String title, LocalDate date, String note, User userId, String publisher, String volume, String series, String address, String edition) {
        super( author, title, date, note, userId);
        this.publisher = publisher;
        this.volume = volume;
        this.series = series;
        this.address = address;
        this.edition = edition;
    }

    public String getEdition() {return edition;}

    public void setEdition(String edition) {this.edition = edition;}

    public String getAddress() {return address;}

    public void setAddress(String address) {this.address = address;}

    public String getSeries() {return series;}

    public void setSeries(String series) {this.series = series;}

    public String getVolume() {return volume;}

    public void setVolume(String volume) {this.volume = volume;}

    public String getPublisher() {return publisher;}

    public void setPublisher(String publisher) {this.publisher = publisher;}
}