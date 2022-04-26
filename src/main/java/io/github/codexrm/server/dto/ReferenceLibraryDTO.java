package io.github.codexrm.server.dto;

import java.util.ArrayList;

public class ReferenceLibraryDTO {

    private ArrayList<ReferenceDTO> newReferencesList;
    private ArrayList<ReferenceDTO> updatedReferencesList;
    private ArrayList<Integer> deletedReferencesList;
    private Integer UserId;

    public ReferenceLibraryDTO() {
        this.newReferencesList = new ArrayList<>();
        this.updatedReferencesList = new ArrayList<>();
        this.deletedReferencesList = new ArrayList<>();
    }

    public ReferenceLibraryDTO(ArrayList<ReferenceDTO> newReferencesList, ArrayList<ReferenceDTO> updatedReferencesList, ArrayList<Integer> deletedReferencesList, Integer userId) {
        this.newReferencesList = newReferencesList;
        this.updatedReferencesList = updatedReferencesList;
        this.deletedReferencesList = deletedReferencesList;
        UserId = userId;
    }

    public ArrayList<ReferenceDTO> getNewReferencesList() {
        return newReferencesList;
    }

    public void setNewReference(ReferenceDTO newReference) {
        this.newReferencesList.add(newReference);
    }

    public ArrayList<ReferenceDTO> getUpdatedReferencesList() {
        return updatedReferencesList;
    }

    public void setUpdatedReference(ReferenceDTO updatedReference) {
        this.updatedReferencesList.add(updatedReference);
    }

    public ArrayList<Integer> getDeletedReferencesList() {
        return deletedReferencesList;
    }

    public void setDeletedReference(Integer deletedReference) {
        this.deletedReferencesList.add(deletedReference);
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }
}
