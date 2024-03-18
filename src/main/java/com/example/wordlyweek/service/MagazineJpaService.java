/*
 * You can use the following import statements
 *
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * 
 * import java.util.*;
 *
 */

// Write your code here
package com.example.wordlyweek.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import com.example.wordlyweek.repository.MagazineRepository;
import com.example.wordlyweek.repository.MagazineJpaRepository;
import com.example.wordlyweek.repository.WriterJpaRepository;
import com.example.wordlyweek.model.*;

@Service
public class MagazineJpaService implements MagazineRepository {
    @Autowired
    private MagazineJpaRepository magazineJpaRepository;
    @Autowired
    private WriterJpaRepository writerJpaRepository;

    public ArrayList<Magazine> getMagazinesList() {
        List<Magazine> magazineList = magazineJpaRepository.findAll();
        ArrayList<Magazine> magazines = new ArrayList<>(magazineList);
        return magazines;
    }

    public Magazine addMagazine(Magazine magazine) {
        List<Integer> writerIds = new ArrayList<>();
        for (Writer writer : magazine.getWriters()) {
            writerIds.add(writer.getWriterId());
        }
        List<Writer> writers = writerJpaRepository.findAllById(writerIds);
        if (writers.size() != writerIds.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some writers are not found");
        }

        magazine.setWriters(writers);
        for (Writer writer : writers) {
            writer.getMagazines().add(magazine);
        }
        magazineJpaRepository.save(magazine);
        writerJpaRepository.saveAll(writers);

        return magazine;

    }

    public Magazine getMagazineById(int magazineId) {
        try {
            Magazine magazine = magazineJpaRepository.findById(magazineId).get();
            return magazine;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public Magazine updateMagazine(int magazineId, Magazine magazine) {
        try {
            Magazine newMagazine = magazineJpaRepository.findById(magazineId).get();
            if (magazine.getMagazineName() != null) {
                newMagazine.setMagazineName(magazine.getMagazineName());
            }
            if (magazine.getPublicationDate() != null) {
                newMagazine.setPublicationDate(magazine.getPublicationDate());
            }
            if (magazine.getWriters() != null) {
                List<Writer> writers = newMagazine.getWriters();
                for (Writer writer : writers) {
                    writer.getMagazines().remove(newMagazine);
                }
                List<Integer> newWriterIds = new ArrayList<>();

                for (Writer writer : magazine.getWriters()) {
                    newWriterIds.add(writer.getWriterId());
                }
                List<Writer> newWriters = writerJpaRepository.findAllById(newWriterIds);
                if (newWriters.size() != newWriterIds.size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some writers are not found");
                }
                magazine.setWriters(newWriters);
                for (Writer writer : newWriters) {
                    writer.getMagazines().add(newMagazine);
                }
                writerJpaRepository.saveAll(newWriters);
                newMagazine.setWriters(newWriters);
            }
            magazineJpaRepository.save(newMagazine);
            return newMagazine;

        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    

    public void deleteMagazine(int magazineId) {
        try {
            Magazine magazine = magazineJpaRepository.findById(magazineId).get();
            List<Writer> writers = magazine.getWriters();
            for (Writer writer : writers) {
                writer.getMagazines().remove(magazine);
            }
            writerJpaRepository.saveAll(writers);
            magazineJpaRepository.deleteById(magazineId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    public List<Writer> getMagazineWriters(int magazineId) {
        try {
            Magazine magazine = magazineJpaRepository.findById(magazineId).get();
            List<Writer> writers = magazine.getWriters();
            return writers;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}