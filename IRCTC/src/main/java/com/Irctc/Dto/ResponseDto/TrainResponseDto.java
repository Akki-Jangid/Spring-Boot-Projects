package com.Irctc.Dto.ResponseDto;

import com.Irctc.Dto.TrainDto.TrainDto;
import com.Irctc.Dto.TrainDto.TrainRouteDto;
import com.Irctc.Model.TrainModels.Train;
import com.Irctc.Model.TrainModels.TrainRoute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TrainResponseDto {

    private String trainName;
    private int trainNumber;
    private List<TrainRouteDto> trainRouteDto;

    public TrainResponseDto(Train train) {
        this.trainName = train.getTrainName();
        this.trainNumber = train.getTrainNumber();
        this.trainRouteDto = train.getTrainRoute().stream().map(TrainRouteDto::new).toList();
    }
}
