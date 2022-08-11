package io.github.codexrm.server.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "bookletreference")
public class BookLetReference extends Reference{


    @Column(name = "howpublisher")
    private String howpublisher;

    @Column(name = "address")
    private String address;

    public BookLetReference() {}

    public BookLetReference(String author, String title, LocalDate date, String note, User userId, String howpublisher, String address) {
        super(author, title, date, note, userId);
        this.howpublisher = howpublisher;
        this.address = address;
    }

    public String getAddress() {return address;}

    public void setAddress(String address) {this.address = address;}

    public String getHowpublisher() {return howpublisher;}

    public void setHowpublisher(String howpublisher) {this.howpublisher = howpublisher;}
}