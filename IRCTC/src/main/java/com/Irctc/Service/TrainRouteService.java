package com.Irctc.Service;

import com.Irctc.Dto.ApiDto.ApiResponse;
import com.Irctc.Dto.ResponseDto.TrainRouteResponseDto;
import com.Irctc.Dto.TrainDto.TrainRouteDto;
import com.Irctc.Model.TrainModels.TrainRoute;
import org.springframework.data.domain.Page;

public interface TrainRouteService {

    TrainRoute findTrainRouteById(Long id);

    ApiResponse addTrainRoute(TrainRouteDto trainRouteDto);

    public Page<TrainRouteResponseDto> getAllTrainsBySearchAndFilter(String startingPoint, String endingPoint, String date, int pageNumber, int pageSize);

    TrainRouteDto getTrainRouteById(long id);

    Page<TrainRouteResponseDto> getAllTrainRoute(int pageNumber, int pageSize);

    ApiResponse updateTrainRoute(Long id, TrainRouteDto trainRouteDto);

    ApiResponse deleteTrainRouteByDate(String date);
}
