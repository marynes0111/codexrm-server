package io.github.codexrm.server.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "conferenceproceedingsreference")
public class ConferenceProceedingReference extends Reference {

    @Column(name = "volume")
    private String volume;

    @Column(name = "series")
    private String series;

    @Column(name = "address")
    private String address;

    public ConferenceProceedingReference() {}

    public ConferenceProceedingReference(String author, String title, LocalDate date, String note, User userid, String volume, String series, String address) {
        super(author, title, date, note, userid);
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
}