package io.github.codexrm.server.Model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "reference")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "BookReference.findAll", query = "select b from BookReference b"),
        @NamedQuery(name = "BookReference.findById", query = "select b from BookReference b where b.id = :id"),
        @NamedQuery(name = "BookReference.existsById", query = "select (count(b) > 0) from BookReference b where b.id = :id"),
        @NamedQuery(name = "BookReference.deleteById", query = "delete from BookReference b where b.id = :id"),
        @NamedQuery(name = "BookReference.updateById", query = "update BookReference b set  where b.id = :id")
})
public class BookReference extends Reference implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "referenceid", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "referenceid", nullable = false)
    private Reference reference;

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

    public BookReference(Integer id, String author, String title, LocalDate date, String note, User userId, Integer id1, Reference reference, String publisher, String volume, String series, String address, String edition) {
        super(id, author, title, date, note, userId);
        this.id = id1;
        this.reference = reference;
        this.publisher = publisher;
        this.volume = volume;
        this.series = series;
        this.address = address;
        this.edition = edition;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
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
        return "BookReference{" +
                "id=" + id +
                ", reference=" + reference +
                ", publisher='" + publisher + '\'' +
                ", volume='" + volume + '\'' +
                ", series='" + series + '\'' +
                ", address='" + address + '\'' +
                ", edition='" + edition + '\'' +
                '}';
    }
}