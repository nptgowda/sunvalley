package com.prashanth.sunvalley.Model;

import lombok.Data;

import java.math.BigDecimal;

@Data
class MiscFeeDTO {
    private String feeName;
    private BigDecimal feeAmount;
    private FeeDTO feeDTO;
}
