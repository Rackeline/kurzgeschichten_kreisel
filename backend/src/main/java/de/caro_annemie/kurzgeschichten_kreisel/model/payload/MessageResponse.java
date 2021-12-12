package de.caro_annemie.kurzgeschichten_kreisel.model.payload;

/**
 * a customizable response message
 */
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
