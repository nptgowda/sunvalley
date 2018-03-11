package com.prashanth.sunvalley.service;

import com.prashanth.sunvalley.Model.PaymentDTO;
import com.prashanth.sunvalley.domain.*;
import com.prashanth.sunvalley.exception.NegativeFeeException;
import com.prashanth.sunvalley.exception.NotFoundException;
import com.prashanth.sunvalley.mapper.PaymentMapper;
import com.prashanth.sunvalley.repository.PaymentRepository;
import com.prashanth.sunvalley.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final StudentRepository studentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentServiceImpl(PaymentRepository paymentRepository, StudentRepository studentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.studentRepository = studentRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public List<PaymentDTO> getAllPaymentsOfStudent(String studentId) {
        StudentIdKeeper studentIdKeeper = new StudentIdKeeper(studentId);
        Optional<Student> studentOptional = studentRepository.findByStudentId(studentIdKeeper);
        if(studentOptional.isPresent()) {
            Student student = studentOptional.get();
            return paymentRepository.findByFeeStudent(student).stream()
                    .map(paymentMapper::paymentToPaymentDTO)
                    .collect(Collectors.toList());
        }else{
            throw new NotFoundException("Student with id: " + studentId + " Not found");
        }
    }

    @Override
    @Transactional
    public PaymentDTO createPayment(String studentId, PaymentDTO paymentDTO) {
        Payment payment =  paymentMapper.paymentDTOToPayment(paymentDTO);
        payment.setDate(LocalDate.now());
        StudentIdKeeper studentIdKeeper = new StudentIdKeeper(studentId);

        Optional<Student> studentOptional = studentRepository.findByStudentId(studentIdKeeper);
        if(studentOptional.isPresent()) {
            Student student = studentOptional.get();
            Fee fee = student.getFee();
            payment.setFee(fee);
            fee.getPayments().add(payment);
            BigDecimal finalAmount;
            switch (payment.getPaymentType()) {
                case TUITION:
                    finalAmount = fee.getTuitionFee().subtract(payment.getAmount());
                    if (finalAmount.compareTo(BigDecimal.ZERO) < 0)
                        throw new NegativeFeeException("Tuition Fee cannot be less than zero");
                    fee.setTuitionFee(finalAmount);
                    break;
                case BOOK:
                    finalAmount = fee.getBookFee().subtract(payment.getAmount());
                    if (finalAmount.compareTo(BigDecimal.ZERO) < 0)
                        throw new NegativeFeeException("Tuition Fee cannot be less than zero");
                    fee.setBookFee(finalAmount);
                    break;
                case UNIFORM:
                    finalAmount = fee.getUniformFee().subtract(payment.getAmount());
                    if (finalAmount.compareTo(BigDecimal.ZERO) < 0)
                        throw new NegativeFeeException("Tuition Fee cannot be less than zero");
                    fee.setUniformFee(finalAmount);
                    break;
                case TRANSPORT:
                    finalAmount = fee.getTransportFee().subtract(payment.getAmount());
                    if (finalAmount.compareTo(BigDecimal.ZERO) < 0)
                        throw new NegativeFeeException("Tuition Fee cannot be less than zero");
                    fee.setTransportFee(finalAmount);
                    break;
                case OLD_BALANCE:
                    finalAmount = fee.getOldBalance().subtract(payment.getAmount());
                    if (finalAmount.compareTo(BigDecimal.ZERO) < 0)
                        throw new NegativeFeeException("Tuition Fee cannot be less than zero");
                    fee.setOldBalance(finalAmount);
                    break;
                 case MISC:
                     if(fee.getMiscFee().size() == 0)
                         throw new NotFoundException(payment.getMiscPaymentName() + " not found");
                     Optional<MiscFee> requiredFeeOptional =
                             fee.getMiscFee().stream()
                             .filter(miscFee -> miscFee.getFeeName().equalsIgnoreCase(payment.getMiscPaymentName()))
                             .findFirst();
                     if(!requiredFeeOptional.isPresent())
                         throw new NotFoundException(payment.getMiscPaymentName() + " not found");
                     MiscFee requiredFee = requiredFeeOptional.get();
                     requiredFee.setFeeAmount(requiredFee.getFeeAmount().subtract(payment.getAmount()));
                     if(requiredFee.getFeeAmount().compareTo(BigDecimal.ZERO) < 0 )
                         throw new NegativeFeeException(requiredFee.getFeeName() +" cannot be less than zero");
                     if(requiredFee.getFeeAmount().compareTo(BigDecimal.ZERO) == 0) {
                         requiredFee.setFee(null);
                         fee.getMiscFee().remove(requiredFee);
                     }

                     break;
            }

            student.setFee(fee);
            studentRepository.save(student);
            return paymentMapper.paymentToPaymentDTO(payment);
        }
        else{
            throw new NotFoundException("Student with id: " + studentId + " Not found");
        }
    }

    @Override
    @Transactional
    public void deletePaymentById(String studentId, long paymentId) {
        StudentIdKeeper studentIdKeeper = new StudentIdKeeper(studentId);

        Optional<Student> studentOptional = studentRepository.findByStudentId(studentIdKeeper);
        if(studentOptional.isPresent()) {
            Student student = studentOptional.get();

            Fee fee = student.getFee();
            Optional<Payment> paymentOptional = fee.getPayments().stream()
                    .filter(payment1 -> payment1.getId().equals(paymentId))
                    .findFirst();
            if(!paymentOptional.isPresent()) {
                throw new NotFoundException("Payment with id: " + paymentId + " Not found");
            }
            Payment payment = paymentOptional.get();
            switch (payment.getPaymentType()) {
                case TUITION:
                    fee.setTuitionFee(fee.getTuitionFee().add(payment.getAmount()));
                    break;
                case BOOK:
                    fee.setBookFee(fee.getBookFee().add(payment.getAmount()));
                    break;
                case UNIFORM:
                    fee.setUniformFee(fee.getUniformFee().add(payment.getAmount()));
                    break;
                case TRANSPORT:
                    fee.setTransportFee(fee.getTransportFee().add(payment.getAmount()));
                    break;
                case OLD_BALANCE:
                    fee.setOldBalance(fee.getOldBalance().add(payment.getAmount()));
                    break;
                case MISC:
                    Optional<MiscFee> requiredFeeOptional =
                            fee.getMiscFee().stream()
                                    .filter(miscFee -> miscFee.getFeeName().equalsIgnoreCase(payment.getMiscPaymentName()))
                                    .findFirst();
                    if(requiredFeeOptional.isPresent()) {
                        MiscFee requiredFee = requiredFeeOptional.get();
                        requiredFee.setFeeAmount(requiredFee.getFeeAmount().add(payment.getAmount()));
                    }else{
                        MiscFee newFee = new MiscFee();
                        newFee.setFeeName(payment.getMiscPaymentName());
                        newFee.setFeeAmount(payment.getAmount());
                        newFee.setFee(fee);
                        fee.getMiscFee().add(newFee);
                    }
                    break;
            }
            payment.setFee(null);
            fee.getPayments().remove(payment);
            student.setFee(fee);
            studentRepository.save(student);
        }else {
            throw new NotFoundException("Student with id: " + studentId + " Not found");
        }
    }
}
