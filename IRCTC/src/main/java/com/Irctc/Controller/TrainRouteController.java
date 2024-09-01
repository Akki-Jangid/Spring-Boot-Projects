package com.Irctc.Controller;

import com.Irctc.Dto.ApiDto.ApiResponse;
import com.Irctc.Dto.ResponseDto.TrainRouteResponseDto;
import com.Irctc.Dto.TrainDto.TrainRouteDto;
import com.Irctc.ServiceImpl.TrainRouteServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@Tag(name = "TrainRoute Management", description = "APIs for Managing Train Routes")
@PreAuthorize("hasRole('USER')")
@RequestMapping("/trainRoute")
public class TrainRouteController {

    @Autowired
    private TrainRouteServiceImpl trainRouteService;


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addTrainRoute(@RequestBody TrainRouteDto trainRouteDto) {
        return new ResponseEntity<>(trainRouteService.addTrainRoute(trainRouteDto), HttpStatus.OK);
    }

    @GetMapping("/getById")
    public ResponseEntity<TrainRouteDto> getTrainRouteById(@Valid @RequestParam Long id){
        return ResponseEntity.ok(trainRouteService.getTrainRouteById(id));
    }

    @PostMapping("/getBySearchAndFilter")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<TrainRouteResponseDto>> getAllTrainsBySearchAndFilter(
                                                                                @Parameter(description = "Search Train by Starting or Destination")
                                                                                @RequestParam(required = false) String startingPoint,
                                                                                @RequestParam(required = false) String destination,
                                                                                @RequestParam(required = false) String date,
                                                                                @RequestParam(defaultValue = "0") int page,
                                                                                @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(trainRouteService.getAllTrainsBySearchAndFilter(startingPoint, destination, date, page, size));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateTrainRoute(@Valid @RequestParam long id,
                                                @RequestBody TrainRouteDto trainRouteDto){
        return ResponseEntity.ok(trainRouteService.updateTrainRoute(id, trainRouteDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteTrainRouteByDate(@RequestParam String date){
        return ResponseEntity.ok(trainRouteService.deleteTrainRouteByDate(date));
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<TrainRouteResponseDto>> getAllTrainRoute(
            @RequestParam(defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(defaultValue = "10", required = false) Integer pageSize){
        return ResponseEntity.ok(trainRouteService.getAllTrainRoute(pageNumber, pageSize));
    }

}
