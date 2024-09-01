package com.Irctc.Dto.ResponseDto;

import com.Irctc.Dto.TrainDto.TrainDto;
import com.Irctc.Model.TrainModels.TrainRoute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class TrainRouteResponseDto {

    private String startingPoint;
    private String destination;
    private int totalSeats;
    private int availableSeats;
    private String journeyDate;
    private String time;
    private TrainDto trainDto;

    public TrainRouteResponseDto(TrainRoute trainRoute) {
        this.startingPoint = trainRoute.getStartingPoint();
        this.destination = trainRoute.getDestination();
        this.totalSeats = trainRoute.getTotalSeats();
        this.availableSeats = trainRoute.getAvailableSeats();
        this.journeyDate = trainRoute.getJourneyDate().toString();
        this.trainDto = new TrainDto(trainRoute.getTrain());
        this.time = trainRoute.getTime().toString();
    }
}
