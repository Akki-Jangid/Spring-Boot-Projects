package com.Irctc.Dto.BookingDto;

import com.Irctc.Model.User.AppUser;
import com.Irctc.Model.Booking;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
public class UsersBookingDto {

    private int totalSeats;
    private int seatsBooked;
    private int availableSeats;
    private Date bookingDate;

    private int trainNumber;
    private String trainName;

    private  AppUser appUser;

    public UsersBookingDto(Booking booking){
        this.totalSeats = booking.getTrainRoute().getTotalSeats();
        this.availableSeats = booking.getTrainRoute().getAvailableSeats();
        this.seatsBooked = booking.getSeatsBooked();
        this.bookingDate = booking.getBookingDate();
        this.trainName = booking.getTrainRoute().getTrain().getTrainName();
        this.trainNumber = booking.getTrainRoute().getTrain().getTrainNumber();
        this.appUser = booking.getAppUser();
    }
}
