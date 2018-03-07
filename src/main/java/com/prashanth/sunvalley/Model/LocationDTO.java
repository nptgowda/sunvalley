package com.prashanth.sunvalley.Model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LocationDTO {
    private Long id;
    private String location;
    private BigDecimal transportFee;
}
