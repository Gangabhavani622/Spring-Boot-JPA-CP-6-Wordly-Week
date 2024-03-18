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

import com.example.wordlyweek.repository.WriterRepository;
import com.example.wordlyweek.repository.MagazineJpaRepository;
import com.example.wordlyweek.repository.WriterJpaRepository;
import com.example.wordlyweek.model.*;

@Service
public class WriterJpaService implements WriterRepository {
    @Autowired
    private WriterJpaRepository writerJpaRepository;
    @Autowired
    private MagazineJpaRepository magazineJpaRepository;

    @Override
    public ArrayList<Writer> getWritersList() {
        List<Writer> writerList = writerJpaRepository.findAll();
        ArrayList<Writer> writers = new ArrayList<>(writerList);
        return writers;
    }

    @Override
    public Writer addWriter(Writer writer) {
        List<Integer> magazineIds = new ArrayList<>();
        for (Magazine magazine : writer.getMagazines()) {
            magazineIds.add(magazine.getMagazineId());
        }
        List<Magazine> magazines = magazineJpaRepository.findAllById(magazineIds);
        if (magazines.size() != magazineIds.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some magazines are not found");
        }
        writer.setMagazines(magazines);
        for (Magazine magazine : magazines) {
            magazine.getWriters().add(writer);
        }
        writerJpaRepository.save(writer);
        magazineJpaRepository.saveAll(magazines);

        return writer;
    }

    @Override
    public Writer getWriterById(int writerId) {
        try {
            Writer writer = writerJpaRepository.findById(writerId).get();
            return writer;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Writer updateWriter(int writerId, Writer writer) {
        try {
            Writer newWriter = writerJpaRepository.findById(writerId).get();
            if (writer.getWriterName() != null) {
                newWriter.setWriterName(writer.getWriterName());
            }
            if (writer.getBio() != null) {
                newWriter.setBio(writer.getBio());
            }
            if (writer.getMagazines() != null) {
                List<Magazine> magazines = newWriter.getMagazines();
                for (Magazine magazine : magazines) {
                    magazine.getWriters().remove(newWriter);
                }
                List<Integer> newMagazineIds = new ArrayList<>();

                for (Magazine magazine : writer.getMagazines()) {
                    newMagazineIds.add(magazine.getMagazineId());
                }
                List<Magazine> newMagazines = magazineJpaRepository.findAllById(newMagazineIds);
                if (newMagazines.size() != newMagazineIds.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some magazines are not found");
                }
                writer.setMagazines(newMagazines);
                for (Magazine magazine : newMagazines) {
                    magazine.getWriters().add(writer);
                }
                magazineJpaRepository.saveAll(newMagazines);
                newWriter.setMagazines(newMagazines);
            }
            writerJpaRepository.save(newWriter);
            return newWriter;

        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteWriter(int writerId) {
        try {
            Writer writer = writerJpaRepository.findById(writerId).get();
            List<Magazine> magazines = writer.getMagazines();
            for (Magazine magazine : magazines) {
                magazine.getWriters().remove(writer);
            }
            magazineJpaRepository.saveAll(magazines);
            writerJpaRepository.deleteById(writerId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Magazine> getWriterMagazines(int writerId) {
        try {
            Writer writer = writerJpaRepository.findById(writerId).get();
            List<Magazine> magazines = writer.getMagazines();
            return magazines;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}