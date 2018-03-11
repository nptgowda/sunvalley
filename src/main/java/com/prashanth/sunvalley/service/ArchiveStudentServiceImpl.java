package com.prashanth.sunvalley.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prashanth.sunvalley.domain.ArchiveStudent;
import com.prashanth.sunvalley.domain.Student;
import com.prashanth.sunvalley.exception.NotFoundException;
import com.prashanth.sunvalley.repository.ArchiveStudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ArchiveStudentServiceImpl implements ArchiveStudentService {

    private final ArchiveStudentRepository repository;

    public ArchiveStudentServiceImpl(ArchiveStudentRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Boolean archiveStudent(Student student) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String fileName = student.getStudentId().getStudentId()+".json";
            Path path = Paths.get(fileName);
            File file = new File(fileName);
            objectMapper.writeValue(file,student);
            byte[] content = Files.readAllBytes(path);
            int i = 0;
            Byte[] data = new Byte[content.length];
            for(Byte b : content)
                data[i++] = b.byteValue();
            ArchiveStudent archiveStudent = new ArchiveStudent();
            archiveStudent.setStudentId(student.getStudentId().getStudentId());
            archiveStudent.setStudentData(data);
            repository.save(archiveStudent);
            file.delete();
            return true;
        } catch (Exception e) {
            throw new NotFoundException("Try Again");

        }
    }

    @Override
    public void getArchivedStudent(String studentId) {
        ArchiveStudent archiveStudent = repository.findByStudentId(studentId).get();

        try {
            FileOutputStream stream = new FileOutputStream(studentId + ".txt");
            int i = 0;
            byte[] content = new byte[archiveStudent.getStudentData().length];
            for(byte b : archiveStudent.getStudentData())
                content[i++] = b;
            stream.write(content);
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
