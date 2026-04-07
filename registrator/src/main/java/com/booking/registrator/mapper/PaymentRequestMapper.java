package com.booking.registrator.mapper;

import com.booking.commondb.dto.PaymentCheckRequestDb;
import com.booking.feignclients.dto.PaymentCheckRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentRequestMapper {

    @Mapping(target = "bookingId", source = "id")
    @Mapping(target = "person", source = "resident.name")
    @Mapping(target = "amount", expression = "java(java.math.BigDecimal.valueOf(1000))")
    PaymentCheckRequest toFeign(PaymentCheckRequestDb db);

    PaymentCheckRequestDb toDb(PaymentCheckRequest feign);
}