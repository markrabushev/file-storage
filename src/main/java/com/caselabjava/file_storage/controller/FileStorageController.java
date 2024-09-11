package com.caselabjava.file_storage.controller;

import com.caselabjava.file_storage.model.Storage;
import com.caselabjava.file_storage.repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @ResponseStatus(HttpStatus.CREATED)
    public Integer postTaco(@RequestBody Storage file) {
        return storageRepository.save(file).getId();
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
