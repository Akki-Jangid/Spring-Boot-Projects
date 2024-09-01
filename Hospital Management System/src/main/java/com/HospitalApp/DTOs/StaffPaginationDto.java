package com.HospitalApp.DTOs;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class StaffPaginationDto {

    private long id;
    private Date dateOfJoining;

    private String specialization;
    private Long appUserId;
    private Long hospitalId;

    private HospitalDto hospitalDetails;
    private AppUserDto appUserDetails;


    public StaffPaginationDto(StaffDto staff, AppUserDto appUserDto, HospitalDto hospitalDto){
        this.id = staff.getId();
        this.dateOfJoining = staff.getDateOfJoining();
        this.specialization = staff.getSpecialization();
        this.appUserId = staff.getAppUserId();
        this.hospitalId = staff.getHospitalId();
        this.appUserDetails = appUserDto;
        this.hospitalDetails = hospitalDto;
    }
}
