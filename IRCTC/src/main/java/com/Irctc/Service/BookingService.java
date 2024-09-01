package com.Irctc.Service;

import com.Irctc.Dto.ApiDto.ApiResponse;
import com.Irctc.Dto.BookingDto.BookingDto;
import com.Irctc.Dto.BookingDto.BookingRequestDto;
import com.Irctc.Model.Booking;
import org.springframework.data.domain.Page;

public interface BookingService {

     Booking findBookingById(Long id);

     ApiResponse createBooking(BookingRequestDto bookingRequestDto);

     BookingDto getBookingById(long id);

     Page<BookingDto> getAllBooking(int pageNumber, int pageSize);

     Page<BookingDto> getAllBookingByTrain(long trainId, int pageNumber, int pageSize);

     ApiResponse updateBooking(Long id, BookingRequestDto bookingRequestDto);

     ApiResponse deleteBookingById(long id);
}
