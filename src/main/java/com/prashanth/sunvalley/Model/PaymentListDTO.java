package com.prashanth.sunvalley.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PaymentListDTO {
    private List<PaymentDTO> payments;
}
