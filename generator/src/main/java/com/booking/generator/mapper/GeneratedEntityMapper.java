package com.booking.generator.mapper;

import com.booking.commondb.dto.BookingRequestDb;
import com.booking.commondb.entity.GeneratedEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GeneratedEntityMapper {

    BookingRequestDb toDb(GeneratedEntity entity);
}
