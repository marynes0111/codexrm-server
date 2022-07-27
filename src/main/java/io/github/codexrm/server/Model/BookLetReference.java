package io.github.codexrm.server.Model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "reference")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "BookLetReference.findAll", query = "select b from BookLetReference b"),
        @NamedQuery(name = "BookLetReference.findById", query = "select b from BookLetReference b where b.id = :id"),
        @NamedQuery(name = "BookLetReference.existsById", query = "select (count(b) > 0) from BookLetReference b where b.id = :id"),
        @NamedQuery(name = "BookLetReference.deleteById", query = "delete from BookLetReference b where b.id = :id"),
        @NamedQuery(name = "BookLetReference.updateById", query = "update BookLetReference b set  where b.id = :id")
})
public class BookLetReference extends Reference implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "referenceid", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "referenceid", nullable = false)
    private Reference reference;

    @Column(name = "howpublisher")
    private String howpublisher;

    @Column(name = "address")
    private String address;

    public BookLetReference() {}

    public BookLetReference(Integer id, String author, String title, LocalDate date, String note, User userId, Integer id1, Reference reference, String howpublisher, String address) {
        super(id, author, title, date, note, userId);
        this.id = id1;
        this.reference = reference;
        this.howpublisher = howpublisher;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHowpublisher() {
        return howpublisher;
    }

    public void setHowpublisher(String howpublisher) {
        this.howpublisher = howpublisher;
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
        return "BookLetReference{" +
                "id=" + id +
                ", reference=" + reference +
                ", howpublisher='" + howpublisher + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}