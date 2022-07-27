package io.github.codexrm.server.Model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "reference")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "ThesisReference.findAll", query = "select t from ThesisReference t"),
        @NamedQuery(name = "ThesisReference.findById", query = "select t from ThesisReference t where t.id = :id"),
        @NamedQuery(name = "ThesisReference.existsById", query = "select (count(t) > 0) from ThesisReference t where t.id = :id"),
        @NamedQuery(name = "ThesisReference.deleteById", query = "delete from ThesisReference t where t.id = :id"),
        @NamedQuery(name = "ThesisReference.updateById", query = "update ThesisReference t set  where t.id = :id")
})
public class ThesisReference extends Reference implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "referenceid", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "referenceid", nullable = false)
    private Reference reference;

    @Column(name = "school")
    private String school;

    @Column(name = "type")
    private String type;

    @Column(name = "address")
    private String address;

    public ThesisReference() {}

    public ThesisReference(Integer id, String author, String title, LocalDate date, String note, User userId, Integer id1, Reference reference, String school, String type, String address) {
        super(id, author, title, date, note, userId);
        this.id = id1;
        this.reference = reference;
        this.school = school;
        this.type = type;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Reference getReference() {
        return reference;
    }

    public void setReference(Reference reference) {
        this.reference = reference;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ThesisReference{" +
                "id=" + id +
                ", reference=" + reference +
                ", school='" + school + '\'' +
                ", type='" + type + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}