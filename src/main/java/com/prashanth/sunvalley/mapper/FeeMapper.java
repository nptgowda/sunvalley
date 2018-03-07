package com.prashanth.sunvalley.mapper;

import com.prashanth.sunvalley.Model.FeeDTO;
import com.prashanth.sunvalley.domain.Fee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FeeMapper {
    FeeMapper INSTANCE = Mappers.getMapper(FeeMapper.class);

    FeeDTO feeToFeeDTO(Fee fee);
    Fee feeDTOToFee(FeeDTO feeDTO);
}
