package com.prashanth.sunvalley.mapper;

import com.prashanth.sunvalley.Model.PaymentDTO;
import com.prashanth.sunvalley.domain.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentMapper {

    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    PaymentDTO paymentToPaymentDTO(Payment payment);
    Payment paymentDTOToPayment(PaymentDTO paymentDTO);
}
