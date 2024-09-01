package com.HospitalApp.Controller;

import com.HospitalApp.DTOs.PatientDto;
import com.HospitalApp.DTOs.PatientPaginationDto;
import com.HospitalApp.Service.PatientService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
@PreAuthorize("hasRole('USER')")
@Validated
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/save")
    public ResponseEntity<String> createPatient(@Valid @RequestBody PatientDto patientDto) {
        patientService.createPatient(patientDto);
        return new ResponseEntity<>("Patient Created Successfully", HttpStatus.CREATED);
    }

    @GetMapping("/getById")
    public ResponseEntity<PatientDto> getPatientById(@Valid @RequestParam(name = "id") Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @PostMapping("/pagination/getBySearchAndFilter")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<PatientPaginationDto>> getPatientByPatientNameOrDoctorNameAndFilter(@Valid
                                                                                                   @Parameter(description = "Search by Patient or Doctor Name")
                                                                                                   @RequestParam(required = false) String searchValue,
                                                                                                   @RequestParam(required = false) List<Long> doctorId,
                                                                                                   @RequestParam(required = false) List<Long> hospitalId,
                                                                                                   @RequestParam(defaultValue = "0") int page,
                                                                                                   @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(patientService.getPatientByPatientOrDoctorNameAndFilter(searchValue, doctorId, hospitalId, page, size));

    }

    @PutMapping("/update")
    public ResponseEntity<String> updatePatient(@Valid @RequestParam int id,
                                                @RequestBody PatientDto patientDto) {
        patientService.updatePatient(id, patientDto);
        return new ResponseEntity<>("Patient Updated Successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePatient(@Valid @RequestParam Long id) {
        patientService.deletePatientById(id);
        return new ResponseEntity<>("Patient Deleted Successfully", HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<PatientPaginationDto>> getAllPatients(@Valid @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                           @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return ResponseEntity.ok(patientService.getAllPatient(pageNumber, pageSize));
    }
}
