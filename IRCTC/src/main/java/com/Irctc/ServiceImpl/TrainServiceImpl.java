package com.Irctc.ServiceImpl;

import com.Irctc.Dto.ApiDto.ApiResponse;
import com.Irctc.Dto.ResponseDto.TrainResponseDto;
import com.Irctc.Dto.TrainDto.TrainDto;
import com.Irctc.ExceptionHandler.ResourceNotFoundException;
import com.Irctc.Model.TrainModels.Train;
import com.Irctc.Model.TrainModels.TrainRoute;
import com.Irctc.Repository.TrainRepository;
import com.Irctc.Service.TrainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TrainServiceImpl implements TrainService {

    @Autowired
    private TrainRepository trainRepository;

    public Train findTrainById(Long id) {
        return trainRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Train with ID " + id + " not found"));
    }

    public void existsByTrainNumber(int trainNumber) {
        trainRepository.existsByTrainNumber(trainNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Train is already registered with number " + trainNumber));
    }

    @Override
    public ApiResponse addTrain(TrainDto trainDto) {
        existsByTrainNumber(trainDto.getTrainNumber());
        Train train = new Train(trainDto);
        trainRepository.save(train);
        return new ApiResponse(true, "Train Details Added Successfully");
    }

    @Override
    public TrainDto getTrainById(long id) {
        Train train = findTrainById(id);
        return new TrainDto(train);
    }

    @Override
    public Page<TrainResponseDto> getAllTrain(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Train> trainPage = trainRepository.findAll(pageable);
        List<TrainResponseDto> trainDtoList = trainPage.stream().map(TrainResponseDto::new).toList();

        return new PageImpl<>(trainDtoList, pageable, trainPage.getTotalElements());
    }

    @Override
    public ApiResponse updateTrain(Long id, TrainDto trainDto) {
        Train train = findTrainById(id);
        if (!trainDto.getTrainName().isEmpty()) train.setTrainName(trainDto.getTrainName());
        if (trainDto.getTrainNumber() != 0) train.setTrainNumber(trainDto.getTrainNumber());
        trainRepository.save(train);
        return new ApiResponse(true, "Train Updated Successfully");
    }

    @Override
    public ApiResponse deleteTrain(int trainNumber) {
        trainRepository.deleteByTrainNumber(trainNumber);
        return new ApiResponse(true, "Train Deleted Successfully");
    }
}
