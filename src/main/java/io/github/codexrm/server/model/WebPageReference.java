package io.github.codexrm.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "webpagereference")
public class WebPageReference extends Reference {

    @Column(name = "url")
    private String url;

    @Column(name = "accessDate")
    private LocalDate accessDate;

    public WebPageReference() {}

    public WebPageReference(String author, String title, LocalDate date, String note, User userid, String url, LocalDate accessDate) {
        super(author, title, date, note, userid);
        this.url = url;
        this.accessDate = accessDate;
    }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public LocalDate getAccessDate() { return accessDate; }

    public void setAccessDate(LocalDate accessDate) { this.accessDate = accessDate; }
}
