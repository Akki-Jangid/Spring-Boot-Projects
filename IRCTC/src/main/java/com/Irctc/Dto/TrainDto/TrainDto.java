package com.Irctc.Dto.TrainDto;

import com.Irctc.Model.TrainModels.Train;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TrainDto {

    @NotBlank(message = "Train Name cannot be Empty")
    private String trainName;

    @NotBlank(message = "Train number cannot be empty")
    @Pattern(regexp = "^{1}\\d{5}$", message = "Train number must be in the format '5 digits' Number")
    private int trainNumber;

    @JsonIgnore
    private List<TrainRouteDto> trainRouteDto;

    public TrainDto(Train train) {
        this.trainName = train.getTrainName();
        this.trainNumber = train.getTrainNumber();
    }
}
