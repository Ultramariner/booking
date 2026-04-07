package com.booking.payment.mapper;

import com.booking.commondb.dto.PaymentCheckResponseDb;
import com.booking.feignclients.dto.PaymentCheckResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentResponseMapper {

    PaymentCheckResponseDb toDb(PaymentCheckResponse feign);

    PaymentCheckResponse toFeign(PaymentCheckResponseDb db);
}
