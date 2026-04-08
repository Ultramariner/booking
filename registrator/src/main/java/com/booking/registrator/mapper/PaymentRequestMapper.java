package com.booking.registrator.mapper;

import com.booking.commondb.dto.PaymentCheckRequestDb;
import com.booking.feignclients.dto.PaymentCheckRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentRequestMapper {

    PaymentCheckRequest toFeign(PaymentCheckRequestDb db);

    PaymentCheckRequestDb toDb(PaymentCheckRequest feign);
}