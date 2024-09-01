package com.Irctc.ServiceImpl;

import com.Irctc.Dto.ApiDto.ApiResponse;
import com.Irctc.Dto.ResponseDto.TrainResponseDto;
import com.Irctc.Dto.ResponseDto.TrainRouteResponseDto;
import com.Irctc.Dto.TrainDto.TrainRouteDto;
import com.Irctc.ExceptionHandler.ResourceNotFoundException;
import com.Irctc.Model.TrainModels.Train;
import com.Irctc.Model.TrainModels.TrainRoute;
import com.Irctc.Repository.TrainRouteRepository;
import com.Irctc.Service.TrainRouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Slf4j
@Service
public class TrainRouteServiceImpl implements TrainRouteService {

    @Autowired
    private TrainRouteRepository trainRouteRepository;

    @Autowired
    private TrainServiceImpl trainService;

    public TrainRoute findTrainRouteById(Long id) {
        return trainRouteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Train Route with ID " + id + " not found"));
    }

    @Override
    public ApiResponse addTrainRoute(TrainRouteDto trainRouteDto) {
        Train train = trainService.findTrainById(trainRouteDto.getTrain_id());
        TrainRoute trainRoute = new TrainRoute(trainRouteDto, train);
        trainRouteRepository.save(trainRoute);
        return new ApiResponse(true, "Train Route Added Successfully...");
    }

    @Override
    public TrainRouteDto getTrainRouteById(long id) {
        TrainRoute trainRoute = findTrainRouteById(id);
        return new TrainRouteDto(trainRoute);
    }


    @Override
    public Page<TrainRouteResponseDto> getAllTrainRoute(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<TrainRoute> trainRoutePages = trainRouteRepository.findAll(pageable);
        List<TrainRouteResponseDto> trainRouteDtoList = trainRoutePages.stream().map(TrainRouteResponseDto::new).toList();
        return new PageImpl<>(trainRouteDtoList, pageable, trainRoutePages.getTotalElements());
    }

    @Override
    public Page<TrainRouteResponseDto> getAllTrainsBySearchAndFilter(String startingPoint, String endingPoint, String date, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<TrainRoute> trainDetails;
        trainDetails = validatingInput(startingPoint, endingPoint, date, pageable);
        List<TrainRouteResponseDto> trainDtoList = trainDetails.stream().map(TrainRouteResponseDto::new).toList();
        return new PageImpl<>(trainDtoList, pageable, trainDetails.getTotalElements());
    }

    private Page<TrainRoute> validatingInput(String startingPoint, String endingPoint, String date, Pageable pageable) {
        if (startingPoint == null && endingPoint == null && date == null) return trainRouteRepository.findAll(pageable);
        else {
            if (startingPoint==null) startingPoint="";
            if (endingPoint == null ) endingPoint="";
            if (date == null) date="";
            else validateDate(date);
            log.info("startingPoint = "+startingPoint+"  || EndingPoint = "+endingPoint+"  ||  Date = "+date);
            return trainRouteRepository.findBySearchAndFilter(
                    startingPoint, endingPoint, date, pageable);
        }
    }

    @Override
    public ApiResponse updateTrainRoute(Long id, TrainRouteDto trainRouteDto) {

        TrainRoute trainRoute = findTrainRouteById(id);

        if (!trainRouteDto.getJourneyDate().isEmpty()) trainRoute.setJourneyDate(LocalDate.parse(trainRouteDto.getJourneyDate()));
        if (!trainRouteDto.getTime().isEmpty()) trainRoute.setJourneyDate(LocalDate.parse(trainRouteDto.getJourneyDate()));
        if (!trainRouteDto.getStartingPoint().isEmpty()) trainRoute.setStartingPoint(trainRouteDto.getStartingPoint());
        if (trainRouteDto.getAvailableSeats() != 0) trainRoute.setAvailableSeats(trainRouteDto.getAvailableSeats());
        if (!trainRouteDto.getDestination().isEmpty()) trainRoute.setDestination(trainRouteDto.getDestination());
        if (trainRouteDto.getTotalSeats() != 0) trainRoute.setTotalSeats(trainRouteDto.getTotalSeats());

        return new ApiResponse(true, "TrainRoute Updated Successfully");
    }

    @Override
    public ApiResponse deleteTrainRouteByDate(String date) {
        trainRouteRepository.deleteByDate(date);
        return new ApiResponse(true, "Train Route Deleted Successfully");
    }

    private void validateDate(String date){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try{
            LocalDate localDate = LocalDate.parse(date, dateFormatter);
        }catch (DateTimeParseException e){
            throw new ResourceNotFoundException("Invalid Date... Date format should be [YYYY-MM-DD]");
        }
    }
}
