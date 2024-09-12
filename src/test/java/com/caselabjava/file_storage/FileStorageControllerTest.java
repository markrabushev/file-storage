package com.caselabjava.file_storage;

import com.caselabjava.file_storage.controller.FileStorageController;
import com.caselabjava.file_storage.model.Storage;
import com.caselabjava.file_storage.repository.StorageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static
        org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static
        org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static
        org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;


import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static org.mockito.Mockito.when;

@WebMvcTest(FileStorageController.class)
public class FileStorageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StorageRepository storageRepository;

    @Test
    void testPost() throws Exception {
        Storage storage = new Storage(1, "cXdxd3F3cXdxdw==", "file1",
                LocalDateTime.of(2024, Month.SEPTEMBER, 11, 23, 21, 35), "description1");

        when(storageRepository.save(any(Storage.class))).thenReturn(storage);

        mockMvc.perform(post("/storage")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"file\": \"cXdxd3F3cXdxdw==\", \"title\": \"file1\", \"creationDate\": \"2024-09-11T23:21:35\", \"description\": \"description1\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    void testGetById() throws Exception {
        Storage storage = new Storage(1, "cXdxd3F3cXdxdw==", "file1",
                LocalDateTime.of(2024, Month.SEPTEMBER, 11, 23, 21, 35), "description1");

        when(storageRepository.findById(1)).thenReturn(Optional.of(storage));

        mockMvc.perform(get("/storage/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.file").value("cXdxd3F3cXdxdw=="))
                .andExpect(jsonPath("$.title").value("file1"))
                .andExpect(jsonPath("$.creationDate").value("2024-09-11T23:21:35"))
                .andExpect(jsonPath("$.description").value("description1"));
    }

    @Test
    void testGetByIdNegative() throws Exception {
        when(storageRepository.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/storage/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    void testList() throws Exception {
        Storage storage1 = new Storage(1, "cXdxd3F3cXdxdw==", "file1",
                LocalDateTime.of(2024, Month.SEPTEMBER, 11, 5, 21, 35), "description1");
        Storage storage2 = new Storage(2, "cXdxd3F3cXdxdw==", "file2",
                LocalDateTime.of(2024, Month.SEPTEMBER, 11, 23, 21, 35), "description2");
        Storage storage3 = new Storage(3, "cXdxd3F3cXdxdw==", "file3",
                LocalDateTime.of(2024, Month.SEPTEMBER, 11, 11, 21, 35), "description3");


        List<Storage> storageList = Arrays.asList(storage1, storage2, storage3);
        storageList.sort(Comparator.comparing(Storage::getCreationDate).reversed());
        when(storageRepository.findAll(PageRequest.of(0, 5,
                Sort.by("creationDate").descending()))).thenReturn(new PageImpl<Storage>(storageList));

        mockMvc.perform(get("/storage"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[1].id").value(3))
                .andExpect(jsonPath("$[2].id").value(1));
    }

    @Test
    void testListEmpty() throws Exception {
        when(storageRepository.findAll(PageRequest.of(0, 5,
                Sort.by("creationDate").descending()))).thenReturn(new PageImpl<>(Collections.emptyList()));

        mockMvc.perform(get("/storage"))
                .andExpect(status().isNotFound());
    }

}
