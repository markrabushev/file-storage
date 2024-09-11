package com.caselabjava.file_storage.service;

import com.caselabjava.file_storage.model.Storage;
import com.caselabjava.file_storage.repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
public class StorageService {

    private final StorageRepository storageRepository;

    @Autowired
    public StorageService(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    public Integer store(Storage fileStorage) {
        return storageRepository.save(fileStorage).getId();
    }

    public List<Storage> loadAll() {
        return storageRepository.findAll();
    }

    Storage load(Integer id) {
        return storageRepository.findById(id).get();
    }

}
