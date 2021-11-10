package de.caro_annemie.kurzgeschichten_kreisel.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "`user`")
public class User {
    @Id @GeneratedValue 
    private long id;
    private String name;
    private String role;
    private String password;
    private String mail;

    //constructor   
    public User(String name, String role, String password, String mail) {
        this.name = name;
        this.role = role;
        this.password = password;
        this.mail = mail;
    }

    public User(){

    }

    //getter and setter
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
