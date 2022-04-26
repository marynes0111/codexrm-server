package io.github.codexrm.server.dto;

import java.time.LocalDate;

public class ConferenceProceedingsReferenceDTO extends ReferenceDTO{

    private String volume;
    private String serie;
    private String address;

    public ConferenceProceedingsReferenceDTO() {}

    public ConferenceProceedingsReferenceDTO(String author, String title, LocalDate date, String note, Integer id, String volume, String serie, String address) {
        super(author, title, date, note, id);
        this.volume = volume;
        this.serie = serie;
        this.address = address;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
