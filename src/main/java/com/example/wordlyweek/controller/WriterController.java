/*
 * You can use the following import statements
 *
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.web.bind.annotation.*;
 * 
 * import java.util.ArrayList;
 * import java.util.List;
 * 
 */

// Write your code here
package com.example.wordlyweek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import com.example.wordlyweek.service.WriterJpaService;
import com.example.wordlyweek.model.*;

@RestController
public class WriterController {
    @Autowired
    public WriterJpaService writerJpaService;

    @GetMapping("/magazines/writers")
    public ArrayList<Writer> getWritersList() {
        return writerJpaService.getWritersList();
    }

    @PostMapping("/magazines/writers")
    public Writer addWriter(@RequestBody Writer writer) {
        return writerJpaService.addWriter(writer);
    }

    @GetMapping("/magazines/writers/{writerId}")
    public Writer getWriterById(@PathVariable("writerId") int writerId) {
        return writerJpaService.getWriterById(writerId);
    }

    @PutMapping("/magazines/writers/{writerId}")
    public Writer updateWriter(@PathVariable("writerId") int writerId, @RequestBody Writer writer) {
        return writerJpaService.updateWriter(writerId, writer);
    }

    @DeleteMapping("/magazines/writers/{writerId}")
    public void deleteWriter(@PathVariable("writerId") int writerId) {
        writerJpaService.deleteWriter(writerId);
    }

    @GetMapping("/writers/{writerId}/magazines")
    public List<Magazine> getWriterMagazines(@PathVariable("writerId") int writerId) {
        return writerJpaService.getWriterMagazines(writerId);
    }
}