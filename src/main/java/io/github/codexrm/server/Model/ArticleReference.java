package io.github.codexrm.server.Model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "reference")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "ArticleReference.findAll", query = "select a from ArticleReference a"),
        @NamedQuery(name = "ArticleReference.findById", query = "select a from ArticleReference a where a.id = :id"),
        @NamedQuery(name = "ArticleReference.existsById", query = "select (count(a) > 0) from ArticleReference a where a.id = :id"),
        @NamedQuery(name = "ArticleReference.deleteById", query = "delete from ArticleReference a where a.id = :id"),
        @NamedQuery(name = "ArticleReference.updateById", query = "update ArticleReference a set  where a.id = :id")
})
public class ArticleReference extends Reference implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "referenceid", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "referenceid", nullable = false)
    private Reference reference;

    @Column(name = "journal")
    private String journal;

    @Column(name = "volume")
    private String volume;

    @Column(name = "numbera")
    private String number;

    @Column(name = "pages")
    private String pages;

    public ArticleReference() {}

    public ArticleReference(Integer id, String author, String title, LocalDate date, String note, User userId, Integer id1, Reference reference, String journal, String volume, String number, String pages) {
        super(id, author, title, date, note, userId);
        this.id = id1;
        this.reference = reference;
        this.journal = journal;
        this.volume = volume;
        this.number = number;
        this.pages = pages;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
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
        return "ArticleReference{" +
                "id=" + id +
                ", reference=" + reference +
                ", journal='" + journal + '\'' +
                ", volume='" + volume + '\'' +
                ", number='" + number + '\'' +
                ", pages='" + pages + '\'' +
                '}';
    }
}