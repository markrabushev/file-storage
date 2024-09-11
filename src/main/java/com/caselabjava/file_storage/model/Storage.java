package com.caselabjava.file_storage.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PRIVATE, force=true)
@Table(name = "file_storage")
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "file")
    @Lob
    @NotNull
    private String file;

    @NotNull
    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @NotNull
    @Column(name = "description")
    private String description;

}
