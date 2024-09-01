package com.Irctc.Dto.BookingDto;

import com.Irctc.Dto.TrainDto.TrainRouteDto;
import com.Irctc.Dto.UserDto.AppUserDto;
import com.Irctc.Model.Booking;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
public class BookingDto {

    private long trainId;
    private int seatsBooked;
    private String bookingDate;
    private TrainRouteDto trainRouteDto;
    private AppUserDto appUserDto;

    public BookingDto(Booking booking){
        this.trainId = booking.getTrainRoute().getTrain().getId();
        this.seatsBooked = booking.getSeatsBooked();
        this.bookingDate = booking.getBookingDate().toString();
        this.trainRouteDto = new TrainRouteDto(booking.getTrainRoute());
        this.appUserDto = new AppUserDto(booking.getAppUser());
    }
}
