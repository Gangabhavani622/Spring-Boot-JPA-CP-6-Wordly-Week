/*
 * You can use the following import statements
 *
 * import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
 * 
 * import javax.persistence.*;
 * import java.util.List;
 * 
 */

// Write your code here
package com.example.wordlyweek.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import com.example.wordlyweek.model.Writer;

@Entity
@Table(name = "magazine")
public class Magazine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int magazineId;
    @Column(name = "title")
    private String magazineName;
    @Column(name = "publicationdate")
    private String publicationDate;

    @ManyToMany(mappedBy = "magazines")

    @JsonIgnoreProperties("magazines")
    private List<Writer> writers = new ArrayList<>();

    public Magazine() {

    }

    public Magazine(int magazineId, String magazineName, String publicationDate, List<Writer> writers) {
        this.magazineId = magazineId;
        this.magazineName = magazineName;
        this.publicationDate = publicationDate;
        this.writers = writers;
    }

    public void setMagazineId(int magazineId) {
        this.magazineId = magazineId;
    }

    public int getMagazineId() {
        return this.magazineId;
    }

    public void setMagazineName(String magazineName) {
        this.magazineName = magazineName;
    }

    public String getMagazineName() {
        return this.magazineName;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getPublicationDate() {
        return this.publicationDate;
    }

    public void setWriters(List<Writer> writers) {
        this.writers = writers;
    }

    public List<Writer> getWriters() {
        return this.writers;
    }

}