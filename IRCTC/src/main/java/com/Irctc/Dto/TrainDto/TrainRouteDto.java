package com.Irctc.Dto.TrainDto;

import com.Irctc.Model.TrainModels.TrainRoute;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TrainRouteDto {

    @NotBlank(message = "Starting Point Details cannot be Empty")
    private String startingPoint;

    @NotBlank(message = "Destination Details cannot be Empty")
    private String destination;

    @NotBlank(message = "Total Seats Information cannot be Empty")
    @Min(value = 1, message = "Capacity must be at least 1")
    private int totalSeats;

    @NotBlank(message = "Available Seats Information cannot be Empty")
    private int availableSeats;

    @NotBlank(message = "Date cannot be Empty")
    private String journeyDate;

    @NotBlank(message = "Time is Mandatory")
    private String time;

    @JsonIgnore
    private TrainDto trainDto;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Train ID cannot be empty")
    private long train_id;

    public TrainRouteDto(TrainRoute trainRoute) {
        this.startingPoint = trainRoute.getStartingPoint();
        this.destination = trainRoute.getDestination();
        this.totalSeats = trainRoute.getTotalSeats();
        this.availableSeats = trainRoute.getAvailableSeats();
        this.journeyDate = trainRoute.getJourneyDate().toString();
        this.trainDto = new TrainDto(trainRoute.getTrain());
        this.train_id = trainRoute.getTrain().getId();
        this.time = trainRoute.getTime().toString();
    }
}
