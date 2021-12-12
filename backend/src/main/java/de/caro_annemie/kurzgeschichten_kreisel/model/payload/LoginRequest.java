package de.caro_annemie.kurzgeschichten_kreisel.model.payload;

/**
 * a client login request
 */
public class LoginRequest {
    private String username, password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
