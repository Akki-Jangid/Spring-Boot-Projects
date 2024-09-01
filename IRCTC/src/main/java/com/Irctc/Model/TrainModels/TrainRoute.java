package com.Irctc.Model.TrainModels;

import com.Irctc.Dto.TrainDto.TrainRouteDto;
import com.Irctc.Model.Booking;
import com.Irctc.Model.Common.CommonBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TrainRoute extends CommonBase {

    @Column(nullable = false)
    private String startingPoint;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private int totalSeats;

    @Column(nullable = false)
    private int availableSeats;

    @Column(nullable = false, name = "trainDate")
    private LocalDate journeyDate;

    @Column(nullable = false, name = "trainTime")
    private LocalTime time;

    @OneToMany(mappedBy = "trainRoute", cascade = CascadeType.ALL)
    private List<Booking> booking;

    @ManyToOne
    private Train train;

    public TrainRoute(TrainRouteDto trainRouteDto, Train train) {
        this.startingPoint = trainRouteDto.getStartingPoint();
        this.destination = trainRouteDto.getDestination();
        this.totalSeats = trainRouteDto.getTotalSeats();
        this.availableSeats = trainRouteDto.getAvailableSeats();
        this.journeyDate = LocalDate.parse(trainRouteDto.getJourneyDate());
        this.time = LocalTime.parse(trainRouteDto.getTime());
        this.train = train;
    }
}
