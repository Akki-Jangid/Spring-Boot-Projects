package com.HospitalApp.DTOs;

import com.HospitalApp.Model.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@Setter
@Getter
public class PatientDto {

    @JsonIgnore
    private long id;

    @NotBlank(message = "Diagnosis is Mandatory")
    private String diagnosis;

    @NotNull(message = "Admitted Date is Mandatory")
    private  Date admittedDate;

    @NotBlank(message = "Medical History Mandatory")
    private  String medicalHistory;

    @NotNull(message = "Status is Mandatory")
    private  Status status;

    @NotNull(message = "AppUser_ID is Mandatory")
    private Long appUserId;
    @NotNull(message = "Hospital_ID is Mandatory")
    private Long hospitalId;
    @NotNull(message = "Staff_ID is Mandatory")
    private Long staffId;

    @JsonIgnore
    private StaffDto staffDto;
    @JsonIgnore
    private HospitalDto hospitalDto;
    @JsonIgnore
    private AppUserDto appUserDto;

    public  PatientDto(Patient patient){
        this.id = patient.getId();
        this.status = patient.getStatus();
        this.diagnosis = patient.getDiagnosis();
        this.admittedDate = patient.getAdmittedDate();
        this.medicalHistory = patient.getMedicalHistory();
        this.appUserId = patient.getAppUser().getId();
        this.hospitalId = patient.getHospital().getId();
        this.staffId = patient.getStaff().getId();
    }

}
