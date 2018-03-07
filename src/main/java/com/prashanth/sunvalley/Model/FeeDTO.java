package com.prashanth.sunvalley.Model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FeeDTO {
    private Long id;
    private BigDecimal tuitionFee;
    private BigDecimal bookFee;
    private BigDecimal uniformFee;
    private BigDecimal transportFee;
    private BigDecimal oldBalance;
}
