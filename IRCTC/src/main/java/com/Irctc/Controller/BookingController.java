package com.Irctc.Controller;

import com.Irctc.Dto.ApiDto.ApiResponse;
import com.Irctc.Dto.BookingDto.BookingDto;
import com.Irctc.Dto.BookingDto.BookingRequestDto;
import com.Irctc.ServiceImpl.BookingServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@Tag(name = "Booking Management", description = "APIs for Managing Train Booking Service")
@PreAuthorize("hasRole('USER')")
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingServiceImpl bookingServiceImpl;


    @PostMapping("/bookTicket")
    public ResponseEntity<ApiResponse> createBooking(@RequestBody BookingRequestDto bookingRequestDto) {
        return ResponseEntity.ok(bookingServiceImpl.createBooking(bookingRequestDto));
    }

    @PostMapping("/getDetailsByTrain")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<BookingDto>> getAllBookingByTrain(@Valid @RequestParam(required = true) long trainId,
                                                                 @RequestParam(defaultValue = "0", required = false) Integer pageNumber,
                                                                 @RequestParam(defaultValue = "10", required = false) Integer pageSize) {
        return ResponseEntity.ok(bookingServiceImpl.getAllBookingByTrain(trainId, pageNumber, pageSize));
    }

    @GetMapping("/getById")
    public ResponseEntity<BookingDto> getBookingDetailsById(@Valid @RequestParam Long id) {
        return ResponseEntity.ok(bookingServiceImpl.getBookingById(id));
    }


    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateAppUser(@Valid @RequestParam long id,
                                                     @RequestBody BookingRequestDto bookingRequestDto) {
        return ResponseEntity.ok(bookingServiceImpl.updateBooking(id, bookingRequestDto));
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<ApiResponse> deleteAppUser(@RequestParam Long id) {
        return ResponseEntity.ok(bookingServiceImpl.deleteBookingById(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<BookingDto>> getAllAppUsers(
            @RequestParam(defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(defaultValue = "10", required = false) Integer pageSize) {
        return ResponseEntity.ok(bookingServiceImpl.getAllBooking(pageNumber, pageSize));
    }

}
