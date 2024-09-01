package com.HospitalApp.ServiceImpl;

import com.HospitalApp.DTOs.StaffDto;
import com.HospitalApp.DTOs.StaffPaginationDto;
import com.HospitalApp.Model.AppUser;
import com.HospitalApp.Model.Hospital;
import com.HospitalApp.Model.ResourceNotFoundException;
import com.HospitalApp.Model.Staff;
import com.HospitalApp.Repository.AppUserRepository;
import com.HospitalApp.Repository.HospitalRepository;
import com.HospitalApp.Repository.StaffRepository;
import com.HospitalApp.Service.StaffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private HospitalServiceImpl hospitalService;

    @Autowired
    private AppUserServiceImpl appUserService;

    Logger logger = LoggerFactory.getLogger(StaffServiceImpl.class);

    @Override
    public void createStaff(StaffDto staffDto) {
        try {
            AppUser appUser = appUserRepository.findById(staffDto.getAppUserId()).orElseThrow(() -> new ResourceNotFoundException("AppUserId not found"));
            Hospital hospital = hospitalRepository.findById(staffDto.getHospitalId()).orElseThrow(() -> new ResourceNotFoundException("HospitalId not found"));
            Staff staff = new Staff(staffDto, appUser, hospital);
            staffRepository.save(staff);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Something is Wrong");
        }
    }

    @Override
    public StaffDto getStaffById(Long id) {
        Staff staff = staffRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Staff is not found with id : " + id));
        return mapToDto(staff);
    }

    @Override
    public Page<StaffDto> getAllStaff(Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Staff> staffPage = staffRepository.findAll(pageable);
        List<StaffDto> list = staffPage.stream().map(this::mapToDto).toList();

        return new PageImpl<>(list, pageable, staffPage.getTotalElements());
    }

    @Override
    public void updateStaff(int id, StaffDto staffDto) {

        Staff staff = staffRepository.findById((long) id).orElseThrow(
                () -> new ResourceNotFoundException("Staff is not found with id : " + id));
        AppUser appUser = appUserRepository.findById(staffDto.getAppUserId()).orElseThrow();
        Hospital hospital = hospitalRepository.findById(staffDto.getHospitalId()).orElseThrow();

        if (staffDto.getDateOfJoining() != null) staff.setDateOfJoining(staffDto.getDateOfJoining());
        if (staffDto.getSpecialization() != null) staff.setSpecialization(staffDto.getSpecialization());
//        if (staffDto.getPosition() != null) staff.setPosition(staffDto.getPosition());
        if (staffDto.getHospitalId() != null) staff.setHospital(hospital);
        if (staffDto.getAppUserId() != null) staff.setAppUser(appUser);

        staffRepository.save(staff);
    }

    @Override
    public void deleteStaffById(Long id) {
        Staff staff = staffRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Staff is not found with id : " + id));
        staffRepository.delete(staff);
    }

    public StaffDto mapToDto(Staff staff) {
        return new StaffDto(staff);
    }


    @Override
    public Page<StaffPaginationDto> findByHospitalNameOrStaffNameAndFilter(String searchValue, List<Long> hospitalId,
                                                                 List<String> staffRole, int pageNumber,
                                                                 int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Staff> staffDetails;
        if (searchValue == null && hospitalId == null && staffRole == null) {
            staffDetails = staffRepository.findAll(pageable);
        }
        else  {
            if (hospitalId == null) hospitalId = Collections.emptyList();
            if (staffRole == null) staffRole = Collections.emptyList();
            if(searchValue==null) searchValue="";

            staffDetails = staffRepository.findByHospitalNameOrStaffNameAndFilter(
                    searchValue, hospitalId, staffRole, pageable);
        }

        List<StaffPaginationDto> staffPaginationDtoList =
                staffDetails.stream()
                        .map(this::mapToDto)
                        .map(staffDto -> new StaffPaginationDto(staffDto,
                                appUserService.mapToDto(appUserRepository.findById(staffDto.getAppUserId()).orElseThrow()),
                                hospitalService.mapToDto(hospitalRepository.findById(staffDto.getHospitalId()).orElseThrow())))
                        .toList();

        return new PageImpl<>(staffPaginationDtoList, pageable, staffDetails.getTotalElements());
    }
}
