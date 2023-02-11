package io.github.codexrm.server.enums;

public enum SortReference {

    idDesc("id,desc"),
    idAsc("id,asc"),
    authorDesc("author,desc"),
    authorAsc("author,asc"),
    titleDesc("title,desc"),
    titleAsc("title,asc"),
    dateDesc("date,desc"),
    dateAsc("date,asc"),
    noteDesc("note,desc"),
    noteAsc("note,asc");

    private final String description;

    SortReference(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
