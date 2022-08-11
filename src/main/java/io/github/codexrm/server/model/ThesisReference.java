package io.github.codexrm.server.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "thesisreference")
public class ThesisReference extends Reference{

    @Column(name = "school")
    private String school;

    @Column(name = "type")
    private String type;

    @Column(name = "address")
    private String address;

    public ThesisReference() {}

    public ThesisReference(String author, String title, LocalDate date, String note, User userid, String school, String type, String address) {
        super(author, title, date, note, userid);
        this.school = school;
        this.type = type;
        this.address = address;
    }

    public String getAddress() {return address;}

    public void setAddress(String address) {this.address = address;}

    public String getType() {return type;}

    public void setType(String type) {this.type = type;}

    public String getSchool() {return school;}

    public void setSchool(String school) {this.school = school;}
}