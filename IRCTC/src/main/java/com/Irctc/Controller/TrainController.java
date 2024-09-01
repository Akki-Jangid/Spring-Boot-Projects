package com.Irctc.Controller;

import com.Irctc.Dto.ApiDto.ApiResponse;
import com.Irctc.Dto.ResponseDto.TrainResponseDto;
import com.Irctc.Dto.TrainDto.TrainDto;
import com.Irctc.ServiceImpl.TrainServiceImpl;
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

import java.time.LocalDate;
import java.util.List;

@RestController
@Validated
@Tag(name = "Train Management", description = "APIs for Managing Trains")
@PreAuthorize("hasRole('USER')")
@RequestMapping("/train")
public class TrainController {

    @Autowired
    private TrainServiceImpl trainService;


    @PostMapping("/Add-Train")
    public ResponseEntity<ApiResponse> addTrain(@RequestBody TrainDto trainDto) {
        return new ResponseEntity<>(trainService.addTrain(trainDto), HttpStatus.OK);
    }

    @GetMapping("/getById")
    public ResponseEntity<TrainDto> getTrainById(@Valid @RequestParam Long id){
        return ResponseEntity.ok(trainService.getTrainById(id));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateTrainDetails(@Valid @RequestParam long id,
                                                @RequestBody TrainDto trainDto){
        return ResponseEntity.ok(trainService.updateTrain(id, trainDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteTrain(@RequestParam(name = "id") int trainNumber){
        return ResponseEntity.ok(trainService.deleteTrain(trainNumber));
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<TrainResponseDto>> getAllTrains(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize){
        return ResponseEntity.ok(trainService.getAllTrain(pageNumber, pageSize));
    }

}
