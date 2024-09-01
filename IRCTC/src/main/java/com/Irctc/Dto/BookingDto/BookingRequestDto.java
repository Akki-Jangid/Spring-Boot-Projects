package com.Irctc.Dto.BookingDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
public class BookingRequestDto {

    @NotBlank(message = "No of Seat Booking cannot be empty")
    @Min(value = 1, message = "At least one seat must be booked ")
    private int seatsBooked;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date bookingDate;

    @NotBlank(message = "TrainRoute_Id is mandatory")
    private long trainRouteId;
}
