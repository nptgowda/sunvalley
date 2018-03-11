package com.prashanth.sunvalley.mapper;

import com.prashanth.sunvalley.Model.MiscFeeDTO;
import com.prashanth.sunvalley.domain.MiscFee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MiscFeeMapper {

    MiscFeeMapper INSTANCE = Mappers.getMapper(MiscFeeMapper.class);
    MiscFeeDTO miscFeeToMiscFeeDTO(MiscFee miscFee);
    MiscFee miscFeeDTOToMiscFee(MiscFeeDTO miscFeeDTO);
}
