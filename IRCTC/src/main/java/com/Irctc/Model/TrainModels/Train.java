package com.Irctc.Model.TrainModels;

import com.Irctc.Dto.TrainDto.TrainDto;
import com.Irctc.Model.Common.CommonBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Train extends CommonBase {

    @Column(nullable = false)
    private String trainName;

    @Column(nullable = false)
    private int trainNumber;

    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL)
    private List<TrainRoute> trainRoute;

    public Train(TrainDto trainDto) {
        this.trainName = trainDto.getTrainName();
        this.trainNumber = trainDto.getTrainNumber();
    }

    public Train(TrainDto trainDto, List<TrainRoute> trainRoute) {
        this.trainName = trainDto.getTrainName();
        this.trainNumber = trainDto.getTrainNumber();
        this.trainRoute = trainRoute;
    }
}
