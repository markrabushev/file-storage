package com.caselabjava.file_storage.controller;

import com.caselabjava.file_storage.model.Storage;
import com.caselabjava.file_storage.repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping(path="storage", produces="application/json")
public class FileStorageController {

    private final StorageRepository storageRepository;

    @Autowired
    public FileStorageController(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    @PostMapping(consumes="application/json")
    public ResponseEntity<HashMap<String, String>> postTaco(@RequestBody Storage file) {
        try {
            HashMap<String, String> response = new HashMap<>();
            response.put("id", storageRepository.save(file).getId().toString());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DateTimeParseException e) {
            HashMap<String, String> response = new HashMap<>();
            response.put("error", "creationDate must be in format 2024-09-11T17:40:01");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Storage> getById(@PathVariable("id") Integer id) {
        Optional<Storage> file = storageRepository.findById(id);
        if (file.isPresent()) {
            return new ResponseEntity<>(file.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public Iterable<Storage> list() {
        return storageRepository.findAll(PageRequest.of(0, 10,
                Sort.by("creationDate").descending())).getContent();
    }


}
