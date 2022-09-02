package io.github.codexrm.server.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "bookletreference")
public class BookLetReference extends Reference{


    @Column(name = "howpublished")
    private String howpublished;

    @Column(name = "address")
    private String address;

    public BookLetReference() {}

    public BookLetReference(String author, String title, LocalDate date, String note, User userId, String howpublished, String address) {
        super(author, title, date, note, userId);
        this.howpublished = howpublished;
        this.address = address;
    }

    public String getAddress() {return address;}

    public void setAddress(String address) {this.address = address;}

    public String getHowpublished() {return howpublished;}

    public void setHowpublished(String howpublished) {this.howpublished = howpublished;}
}