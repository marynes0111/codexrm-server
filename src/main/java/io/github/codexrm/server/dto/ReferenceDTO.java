package io.github.codexrm.server.dto;

import java.time.LocalDate;

public class ReferenceDTO {

    protected String author;
    protected String title;
    protected LocalDate date;
    protected String note;
    protected Integer id;

    public ReferenceDTO() {}

    public ReferenceDTO(String author, String title, LocalDate date, String note, Integer id) {
        this.author = author;
        this.title = title;
        this.date = date;
        this.note = note;
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
