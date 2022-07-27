package io.github.codexrm.server.Model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "reference")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Reference.findAll", query = "select r from Reference r"),
        @NamedQuery(name = "Reference.findById", query = "select r from Reference r where r.id = :id"),
        @NamedQuery(name = "Reference.existsById", query = "select (count(r) > 0) from Reference r where r.id = :id"),
        @NamedQuery(name = "Reference.deleteById", query = "delete from Reference r where r.id = :id"),
        @NamedQuery(name = "Reference.updateById", query = "update Reference r set  where r.id = :id")
})
public class Reference implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "author")
    private String author;

    @Column(name = "title")
    private String title;

    @Column(name = "dater")
    private LocalDate date;

    @Column(name = "note")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userid", nullable = false)
    private User userId;

    public Reference() {}

    public Reference(Integer id, String author, String title, LocalDate date, String note, User userId) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.date = date;
        this.note = note;
        this.userId = userId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Reference{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", note='" + note + '\'' +
                ", userId=" + userId +
                '}';
    }
}