package de.caro_annemie.kurzgeschichten_kreisel.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Kurzgeschichte {
    @Id @GeneratedValue 
    private long id;
    @NotNull
    private String title;
    @NotNull
    private String author;
    @NotNull
    private String genre;
    @NotNull
    private Date creationDate;
    @NotNull
    private String text;

    //constructor
    public Kurzgeschichte(String title, String author, Date creationDate, String text, String genre) {
        this.title = title;
        this.author = author;
        this.creationDate = creationDate;
        this.text = text;
        this.genre = genre;
    }

    public Kurzgeschichte() {

    }

    //getter and setter
    public long getId() {
        return id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }



    
    
}
