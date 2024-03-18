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

import com.example.wordlyweek.model.Magazine;

@Entity
@Table(name = "Writer")
public class Writer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int writerId;
    @Column(name = "name")
    private String writerName;
    @Column(name = "bio")
    private String bio;
    @ManyToMany
    @JoinTable(name = "writer_magazine", joinColumns = @JoinColumn(name = "writerid"), inverseJoinColumns = @JoinColumn(name = "magazineid"))
    @JsonIgnoreProperties("writers")
    private List<Magazine> magazines = new ArrayList<>();

    public Writer() {

    }

    public Writer(int writerId, String writerName, String bio, List<Magazine> magazines) {
        this.writerId = writerId;
        this.writerName = writerName;
        this.bio = bio;
        this.magazines = magazines;
    }

    public void setWriterId(int writerId) {
        this.writerId = writerId;
    }

    public int getWriterId() {
        return this.writerId;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public String getWriterName() {
        return this.writerName;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBio() {
        return this.bio;
    }

    public void setMagazines(List<Magazine> magazines) {
        this.magazines = magazines;
    }

    public List<Magazine> getMagazines() {
        return this.magazines;
    }

}