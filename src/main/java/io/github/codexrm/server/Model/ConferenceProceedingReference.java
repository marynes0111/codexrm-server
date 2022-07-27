package io.github.codexrm.server.Model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "reference")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "ConferenceProceedingReference.findAll", query = "select c from ConferenceProceedingReference c"),
        @NamedQuery(name = "ConferenceProceedingReference.findById", query = "select c from ConferenceProceedingReference c where c.id = :id"),
        @NamedQuery(name = "ConferenceProceedingReference.existsById", query = "select (count(c) > 0) from ConferenceProceedingReference c where c.id = :id"),
        @NamedQuery(name = "ConferenceProceedingReference.deleteById", query = "delete from ConferenceProceedingReference c where c.id = :id"),
        @NamedQuery(name = "ConferenceProceedingReference.updateById", query = "update ConferenceProceedingReference c set  where c.id = :id")
})
public class ConferenceProceedingReference extends Reference implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "referenceid", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "referenceid", nullable = false)
    private Reference reference;

    @Column(name = "volume")
    private String volume;

    @Column(name = "series")
    private String series;

    @Column(name = "address")
    private String address;

    public ConferenceProceedingReference() {}

    public ConferenceProceedingReference(Integer id, String author, String title, LocalDate date, String note, User userId, Integer id1, Reference reference, String volume, String series, String address) {
        super(id, author, title, date, note, userId);
        this.id = id1;
        this.reference = reference;
        this.volume = volume;
        this.series = series;
        this.address = address;
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
        return "ConferenceProceedingReference{" +
                "id=" + id +
                ", reference=" + reference +
                ", volume='" + volume + '\'' +
                ", series='" + series + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}