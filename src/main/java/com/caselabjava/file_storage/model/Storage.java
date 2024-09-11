package com.caselabjava.file_storage.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PRIVATE, force=true)
@Table(name = "file-storage")
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "file")
    @Lob
    private String file;

    @Column(name = "title")
    private String title;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "description")
    private String description;

}
