package com.HospitalApp.Controller;

import com.HospitalApp.DTOs.StaffDto;
import com.HospitalApp.DTOs.StaffPaginationDto;
import com.HospitalApp.Service.StaffService;
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
@RequestMapping("/staff")
@PreAuthorize("hasRole('USER')")
@Validated
public class StaffController {

    @Autowired
    private StaffService staffService;

    @PostMapping("/save")
    public ResponseEntity<String> createStaff(@Valid @RequestBody StaffDto staffDto){
        staffService.createStaff(staffDto);
        return new ResponseEntity<>("Staff Created Successfully", HttpStatus.CREATED);
    }

    @GetMapping("/getById")
    public ResponseEntity<StaffDto> getStaffDetailsById(@Valid @RequestParam(name = "id") Long id){
        return ResponseEntity.ok(staffService.getStaffById(id));
    }

    @PostMapping("/pagination/getBySearchAndFilter")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<StaffPaginationDto>> findByHospitalNameOrStaffNameAndFilter(@Valid
                                                                                           @Parameter(description = "Search by Hospital or Staff Name")
                                                                                           @RequestParam(required = false) String searchValue,
                                                                                           @RequestParam(required = false) List<Long> hospitalIds,
                                                                                           @RequestParam(required = false) List<String> staffRole,
                                                                                           @RequestParam(defaultValue = "0") int page,
                                                                                           @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(staffService.findByHospitalNameOrStaffNameAndFilter(searchValue, hospitalIds, staffRole, page, size));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateStaffInfo( @Valid @RequestParam int id,
                                                       @RequestBody StaffDto staffDto){
        staffService.updateStaff(id, staffDto);
        return new ResponseEntity<>("Staff Updated Successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteStaff(@Valid @RequestParam(name = "id") Long id){
        staffService.deleteStaffById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<StaffDto>> getAllStaff(@Valid @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                      @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize){
        return ResponseEntity.ok(staffService.getAllStaff(pageNumber, pageSize));
    }
}
