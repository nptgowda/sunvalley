package com.prashanth.sunvalley.Model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GradeDTO {
    private Long id;
    private String grade;
    private String section;
    private BigDecimal tuitionFee;
    private BigDecimal uniformFee;
    private BigDecimal bookFee;
}
