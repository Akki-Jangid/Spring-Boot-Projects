package com.Irctc.Service;

import com.Irctc.Dto.ApiDto.ApiResponse;
import com.Irctc.Dto.ResponseDto.TrainResponseDto;
import com.Irctc.Dto.TrainDto.TrainDto;
import com.Irctc.Model.TrainModels.Train;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface TrainService {

     Train findTrainById(Long id);

     void existsByTrainNumber(int trainNumber);

     ApiResponse addTrain(TrainDto trainDto);

     TrainDto getTrainById(long id);

     Page<TrainResponseDto> getAllTrain(int pageNumber, int pageSize);

     ApiResponse updateTrain(Long id, TrainDto trainDto);

     ApiResponse deleteTrain(int trainNumber);
}
