package com.prashanth.sunvalley.mapper;

import com.prashanth.sunvalley.Model.FeeDTO;
import com.prashanth.sunvalley.domain.Fee;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class FeeMapperTest {

    private static final long ID = 1L;

    private FeeMapper feeMapper;

    @Before
    public void setUp() throws Exception {
        feeMapper = FeeMapper.INSTANCE;
    }

    @Test
    public void feeToFeeDTO() throws Exception {
        Fee fee = new Fee();
        fee.setId(ID);
        fee.setUniformFee(new BigDecimal(3500));
        fee.setTuitionFee(new BigDecimal(1500));
        fee.setOldBalance(new BigDecimal(10000));
        fee.setBookFee(new BigDecimal(3500));
        fee.setTransportFee(new BigDecimal(3500));

        FeeDTO feeDTO = feeMapper.feeToFeeDTO(fee);

        assertNotNull(feeDTO);
        assertThat(feeDTO.getId(),is(equalTo(Long.valueOf(ID))));
        assertThat(feeDTO.getTuitionFee(),is(equalTo(fee.getTuitionFee())));

        assertThat(feeDTO.getTransportFee(),is(equalTo(fee.getTransportFee())));
        assertThat(feeDTO.getBookFee(),is(equalTo(fee.getBookFee())));
        assertThat(feeDTO.getUniformFee(),is(equalTo(fee.getUniformFee())));

        assertThat(feeDTO.getOldBalance(),is(equalTo(fee.getOldBalance())));

    }
}