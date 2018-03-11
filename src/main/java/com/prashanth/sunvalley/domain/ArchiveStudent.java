package com.prashanth.sunvalley.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class ArchiveStudent {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentId;
    @Lob
    private Byte[] studentData;
}
