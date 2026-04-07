package com.booking.registrator.mapper;

import com.booking.commondb.dto.BookingRequestDb;
import com.booking.feignclients.dto.BookingRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingRequestMapper {

    BookingRequestDb toDb(BookingRequest feign);

    BookingRequest toFeign(BookingRequestDb db);
}
