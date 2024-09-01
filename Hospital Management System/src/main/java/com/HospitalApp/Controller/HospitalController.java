package com.HospitalApp.Controller;

import com.HospitalApp.DTOs.HospitalDto;
import com.HospitalApp.Service.HospitalService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/hospital")
@PreAuthorize("hasRole('USER')")
@Validated
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @PostMapping("/save")
    public ResponseEntity<String> createHospital(@Valid @RequestBody HospitalDto hospitalDto) {
        hospitalService.createHospital(hospitalDto);
        return new ResponseEntity<>("Hospital Created Successfully", HttpStatus.CREATED);
    }

    @GetMapping("/getById")
    public ResponseEntity<HospitalDto> getHospitalInfoById(@Valid @RequestParam(name = "id") Long id) {
        return ResponseEntity.ok(hospitalService.getHospitalById(id));
    }

    @PostMapping("/pagination/getByNameOrAddress")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<HospitalDto>> getHospitalByNameAndAddress(@Valid
                                                                         @Parameter(description = "Search by hospital name or address")
                                                                         @RequestParam(required = false) String searchValue,
                                                                         @RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(hospitalService.getHospitalByNameOrAddress(searchValue, page, size));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateHospitalInfo(@Valid @RequestParam int id,
                                                     @RequestBody HospitalDto hospitalDto) {
        hospitalService.updateHospital(id, hospitalDto);
        return new ResponseEntity<>("Hospital Updated Created Successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteHospitalInfo(@Valid @RequestParam(name = "id") Long id) {
        hospitalService.deleteHospitalById(id);
        return new ResponseEntity<>("Hospital Deleted Successfully", HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<HospitalDto>> getAllAppUsers(@Valid @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        return ResponseEntity.ok(hospitalService.getAllHospitals(pageNumber, pageSize));
    }
}
