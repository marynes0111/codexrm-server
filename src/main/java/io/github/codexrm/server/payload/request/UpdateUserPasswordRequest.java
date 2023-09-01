package io.github.codexrm.server.payload.request;

public class UpdateUserPasswordRequest {

    private String currentPassword;
    private String newPassword;
    private String confirmationPassword;

    public UpdateUserPasswordRequest() {}

    public UpdateUserPasswordRequest(String newPassword, String currentPassword, String confirmationPassword) {
        this.newPassword = newPassword;
        this.currentPassword = currentPassword;
        this.confirmationPassword = confirmationPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getCurrentPassword() { return currentPassword; }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getConfirmationPassword() {
        return confirmationPassword;
    }

    public void setConfirmationPassword(String confirmationPassword) { this.confirmationPassword = confirmationPassword; }
}
