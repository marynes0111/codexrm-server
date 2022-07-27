package io.github.codexrm.server.Model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "reference")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "BookSectionReference.findAll", query = "select b from BookSectionReference b"),
        @NamedQuery(name = "BookSectionReference.findById", query = "select b from BookSectionReference b where b.id = :id"),
        @NamedQuery(name = "BookSectionReference.existsById", query = "select (count(b) > 0) from BookSectionReference b where b.id = :id"),
        @NamedQuery(name = "BookSectionReference.deleteById", query = "delete from BookSectionReference b where b.id = :id"),
        @NamedQuery(name = "BookSectionReference.updateById", query = "update BookSectionReference b set  where b.id = :id")
})

public class BookSectionReference extends BookReference implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "bookreferencereferenceid", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bookreferencereferenceid", nullable = false)
    private BookReference bookReference;

    @Column(name = "chapter")
    private String chapter;

    @Column(name = "pages")
    private String pages;

    public BookSectionReference() {}

    public BookSectionReference(Integer id, String author, String title, LocalDate date, String note, User userId, Integer id1, Reference reference, String publisher, String volume, String series, String address, String edition, Integer id2, BookReference bookReference, String chapter, String pages) {
        super(id, author, title, date, note, userId, id1, reference, publisher, volume, series, address, edition);
        this.id = id2;
        this.bookReference = bookReference;
        this.chapter = chapter;
        this.pages = pages;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public BookReference getBookReference() {
        return bookReference;
    }

    public void setBookReference(BookReference bookReference) {
        this.bookReference = bookReference;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BookSectionReference{" +
                "id=" + id +
                ", bookReference=" + bookReference +
                ", chapter='" + chapter + '\'' +
                ", pages='" + pages + '\'' +
                '}';
    }
}