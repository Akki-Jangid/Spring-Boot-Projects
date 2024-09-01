package com.HospitalApp.Service;

import com.HospitalApp.DTOs.StaffDto;
import com.HospitalApp.DTOs.StaffPaginationDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StaffService {

    public void createStaff(StaffDto staffDto);

    public StaffDto getStaffById(Long id);

    public Page<StaffDto> getAllStaff(Integer pageNumber, Integer pageSize);

    public Page<StaffPaginationDto> findByHospitalNameOrStaffNameAndFilter(String searchValue, List<Long> hospitalIds, List<String> staffRole, int pageNumber, int pageSize);

    public void updateStaff(int id, StaffDto staffDto);

    public void deleteStaffById(Long id);

}
