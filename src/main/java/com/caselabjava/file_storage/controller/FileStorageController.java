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
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<Map<String, String>> postTaco(@RequestBody Storage file) {
        Map<String, String> response = new HashMap<>();
        response.put("id", storageRepository.save(file).getId().toString());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
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
    public ResponseEntity<List<Storage>> list() {
        List<Storage> list = storageRepository.findAll(PageRequest.of(0, 5,
                Sort.by("creationDate").descending())).getContent();
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


}
