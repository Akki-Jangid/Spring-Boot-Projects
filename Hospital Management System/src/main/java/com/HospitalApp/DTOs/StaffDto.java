package com.HospitalApp.DTOs;

import com.HospitalApp.Model.Staff;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class StaffDto {

    @JsonIgnore
    private long id;

    @JsonIgnore
    @NotBlank(message = "Name is Mandatory")
    private String name;

    @NotNull(message = "Joining Date is Mandatory")
    private Date dateOfJoining;

    @NotBlank(message = "specialization is Mandatory")
    private String specialization;

    @NotNull(message = "AppUser_ID is Mandatory")
    private Long appUserId;

    @NotNull(message = "Hospital_ID is Mandatory")
    private Long hospitalId;

    public StaffDto(Staff staff){
        this.id = staff.getId();
        this.name = staff.getAppUser().getUsername();
        this.dateOfJoining = staff.getDateOfJoining();
        this.specialization = staff.getSpecialization();
        this.appUserId = staff.getAppUser().getId();
        this.hospitalId = staff.getHospital().getId();
    }
}
