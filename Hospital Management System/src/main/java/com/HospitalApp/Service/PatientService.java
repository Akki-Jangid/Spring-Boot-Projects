package com.HospitalApp.Service;

import com.HospitalApp.DTOs.PatientDto;
import com.HospitalApp.DTOs.PatientPaginationDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PatientService {

    public void createPatient(PatientDto patientDto);


    public PatientDto getPatientById(Long id);

    public Page<PatientPaginationDto> getAllPatient(int pageNumber, int pageSize);

    public Page<PatientPaginationDto> getPatientByPatientOrDoctorNameAndFilter(String name, List<Long> doctorId, List<Long> hospitalId, int pageNumber, int pageSize);

    public void updatePatient(int id, PatientDto patientDto);

    public void deletePatientById(Long id);

}
