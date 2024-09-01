package com.Irctc.Model;

import com.Irctc.Dto.BookingDto.BookingRequestDto;
import com.Irctc.Model.Common.CommonBase;
import com.Irctc.Model.TrainModels.TrainRoute;
import com.Irctc.Model.User.AppUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Booking extends CommonBase {

    @Column(nullable = false)
    @Min(value = 1, message = "Booking Seats must be greater than 1")
    private int seatsBooked;

    @Column(nullable = false)
    private Date bookingDate;

    @ManyToOne
    @JoinColumn(name = "train_route_id", nullable = false)
    private TrainRoute trainRoute;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser appUser;

    public Booking(BookingRequestDto bookingRequestDto){
        this.seatsBooked = bookingRequestDto.getSeatsBooked();
        this.bookingDate =  bookingRequestDto.getBookingDate();
    }

    public Booking(BookingRequestDto bookingRequestDto, TrainRoute trainRoute, AppUser appUser){
        this.seatsBooked = bookingRequestDto.getSeatsBooked();
        this.bookingDate =  bookingRequestDto.getBookingDate();
        this.trainRoute = trainRoute;
        this.appUser = appUser;
    }
}
