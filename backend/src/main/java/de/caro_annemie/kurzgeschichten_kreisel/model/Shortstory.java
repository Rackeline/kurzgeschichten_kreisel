package de.caro_annemie.kurzgeschichten_kreisel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * model for shortstory entity
 */
@Entity
public class Shortstory {
    @Id 
    @GeneratedValue 
    private long id;

    @NotBlank
    @Column(length = 50)
    private String title;

    @NotBlank
    @Column(length = 20)
    private String author;

    @NotBlank
    @Column(length = 50)
    private String genre;

    @NotNull
    private Date creationDate;

    @NotBlank
    @Column(length = 20000)
    private String text;

    //constructors
    public Shortstory(String title, String author, Date creationDate, String text, String genre) {
        this.title = title;
        this.author = author;
        this.creationDate = creationDate;
        this.text = text;
        this.genre = genre;
    }

    public Shortstory() {
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
