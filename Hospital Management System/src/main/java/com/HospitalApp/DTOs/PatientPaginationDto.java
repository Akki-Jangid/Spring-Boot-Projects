package com.HospitalApp.DTOs;


import com.HospitalApp.Model.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PatientPaginationDto {

    private Long id;

    private String diagnosis;

    private Date admittedDate;

    private  String medicalHistory;

    private Status status;

    private Long patientId;
    private Long hospitalId;
    private Long staffId;

    private StaffDto staffDetails;

    private HospitalDto hospitalDetails;

    private AppUserDto appUserDetails;

    public  PatientPaginationDto(PatientDto patient, StaffDto staffDto, AppUserDto appUserDto, HospitalDto hospitalDto){
        this.id = patient.getId();
        this.status = patient.getStatus();
        this.diagnosis = patient.getDiagnosis();
        this.admittedDate = patient.getAdmittedDate();
        this.medicalHistory = patient.getMedicalHistory();
        this.appUserDetails = appUserDto;
        this.hospitalDetails = hospitalDto;
        this.staffDetails = staffDto;
        this.patientId = patient.getAppUserId();
        this.hospitalId = patient.getHospitalId();
        this.staffId = patient.getStaffId();
    }
}
