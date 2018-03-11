package com.prashanth.sunvalley.service;

import com.prashanth.sunvalley.domain.Student;

import java.io.IOException;

public interface ArchiveStudentService {
    Boolean archiveStudent(Student student);

    void getArchivedStudent(String studentId);
}
