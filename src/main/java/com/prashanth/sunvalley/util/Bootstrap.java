package com.prashanth.sunvalley.util;

import com.prashanth.sunvalley.domain.*;
import com.prashanth.sunvalley.repository.GradeRepository;
import com.prashanth.sunvalley.repository.LocationRepository;
import com.prashanth.sunvalley.repository.StudentIdKeeperRepository;
import com.prashanth.sunvalley.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SuppressWarnings("ALL")
@Component
@Slf4j
public class Bootstrap implements CommandLineRunner{
    private final StudentRepository studentRepository;
    private final StudentIdKeeperRepository studentIdKeeperRepository;
    private final LocationRepository locationRepository;
    private final GradeRepository gradeRepository;

    public Bootstrap(StudentRepository studentRepository,
                     StudentIdKeeperRepository studentIdKeeperRepository,
                     LocationRepository locationRepository, GradeRepository gradeRepository) {
        this.studentRepository = studentRepository;
        this.studentIdKeeperRepository = studentIdKeeperRepository;
        this.locationRepository = locationRepository;
        this.gradeRepository = gradeRepository;
    }

    @Override
    public void run(String... args) {

        loadLocations();
        loadGrades();
        loadStudents();
    }

    private void loadGrades() {
        Grade g1 = new Grade();
        g1.setGrade("PreLKG");
        g1.setBookFee(new BigDecimal(3000));
        g1.setTuitionFee(new BigDecimal(12500));
        g1.setUniformFee(new BigDecimal(1500));
        gradeRepository.save(g1);

        Grade g2 = new Grade();
        g2.setGrade("LKG");
        g2.setBookFee(new BigDecimal(3000));
        g2.setTuitionFee(new BigDecimal(13500));
        g2.setUniformFee(new BigDecimal(1500));
        g2.setSection("A");
        gradeRepository.save(g2);

        Grade g3 = new Grade();
        g3.setGrade("LKG");
        g3.setBookFee(new BigDecimal(3000));
        g3.setTuitionFee(new BigDecimal(12500));
        g3.setUniformFee(new BigDecimal(1500));
        g3.setSection("B");
        gradeRepository.save(g3);
        Grade g4 = new Grade();
        g4.setGrade("1");
        g4.setBookFee(new BigDecimal(3500));
        g4.setTuitionFee(new BigDecimal(15500));
        g4.setUniformFee(new BigDecimal(1700));
        g4.setSection("A");
        gradeRepository.save(g4);
        Grade g5 = new Grade();
        g5.setGrade("10");
        g5.setBookFee(new BigDecimal(3500));
        g5.setTuitionFee(new BigDecimal(19500));
        g5.setUniformFee(new BigDecimal(2500));
        gradeRepository.save(g5);

    }

    private void loadLocations() {
        Location l1 = new Location();
        l1.setLocation("Thimmasandra");
        l1.setTransportFee(new BigDecimal(1250));
        locationRepository.save(l1);

        Location l2 = new Location();
        l2.setLocation("Vabsandra");
        l2.setTransportFee(new BigDecimal(1250));
        locationRepository.save(l2);
        Location l3 = new Location();
        l3.setLocation("Lakkondanahalli");
        l3.setTransportFee(new BigDecimal(2500));
        locationRepository.save(l3);
        Location l4 = new Location();
        l4.setLocation("Sulibele");
        l4.setTransportFee(new BigDecimal(4500));
        locationRepository.save(l4);
        Location l5 = new Location();
        l5.setLocation("Hoskote");
        l5.setTransportFee(new BigDecimal(4500));
        locationRepository.save(l5);

    }

    private void loadStudents() {
        StudentIdKeeper studentIdKeeper = new StudentIdKeeper();
        studentIdKeeperRepository.save(studentIdKeeper);

        Student student = new Student();
        student.setFirstName("Prashanth");
        student.setLastName("Gowda");
        student.setStudentId(studentIdKeeper);
        student.setDateCreated(LocalDateTime.now());
        student.setFathersName("Nanjunde Gowda KV");
        student.setFathersNumber("12345678");
        student.setMothersName("Nagarathna");
        student.setMothersNumber("213123213");
        student.setDateOfBirth(LocalDate.of(1988,4,27));

        student.setGrade(gradeRepository.findByGradeAndSection("LKG","A").get());
        student.setLocation(locationRepository.findByLocation("Hoskote").get());
        student.setFee(new Fee());
        student.getFee().setTuitionFee(new BigDecimal(15000));
        student.getFee().setBookFee(new BigDecimal(3500));
        student.getFee().setUniformFee(new BigDecimal(1500));
        student.getFee().setTransportFee(new BigDecimal(2500));
        student.getFee().setOldBalance(new BigDecimal(10000));
        student.getFee().setStudent(student);
        Payment payment = new Payment();
        payment.setFee(student.getFee());
        payment.setDate(LocalDate.now());
        payment.setAmount(new BigDecimal(10000));
        payment.setPaymentType(PaymentType.TUITION);
        student.getFee().getPayments().add(payment);

        studentRepository.save(student);
        log.info("Student Saved");

    }

}
