package de.caro_annemie.kurzgeschichten_kreisel.model.payload;

public class MessageResponse {
    private String message;

    // constructor
    public MessageResponse(String message) {
        this.message = message;
    }

    // getter and setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
