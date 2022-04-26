package io.github.codexrm.server.service;

import io.github.codexrm.server.dto.ReferenceDTO;

import java.util.ArrayList;
import java.util.Hashtable;

public class Library {

    private Hashtable<Integer,ReferenceDTO> referenceTable;

    public Library() {
        this.referenceTable = new Hashtable<>();
    }

    public Hashtable<Integer,ReferenceDTO> getReferenceTable() {
        return referenceTable;
    }

    public void setReference(ReferenceDTO reference) {
        this.referenceTable.put(reference.getId(),reference);
    }
}
