package com.HospitalApp.ServiceImpl;

import com.HospitalApp.DTOs.PatientDto;
import com.HospitalApp.DTOs.PatientPaginationDto;
import com.HospitalApp.Model.*;
import com.HospitalApp.Repository.AppUserRepository;
import com.HospitalApp.Repository.HospitalRepository;
import com.HospitalApp.Repository.PatientRepository;
import com.HospitalApp.Repository.StaffRepository;
import com.HospitalApp.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private StaffServiceImpl staffService;

    @Autowired
    private HospitalServiceImpl hospitalService;

    @Autowired
    private AppUserServiceImpl appUserService;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Override
    public void createPatient(PatientDto patientDto) {
        try {
            AppUser appUser = appUserRepository.findById(patientDto.getAppUserId()).orElseThrow(() -> new ResourceNotFoundException("AppUserId not found "));
            Hospital hospital = hospitalRepository.findById(patientDto.getHospitalId()).orElseThrow(() -> new ResourceNotFoundException("HospitalId not found"));
            Staff staff = staffRepository.findById(patientDto.getStaffId()).orElseThrow(() -> new ResourceNotFoundException("StaffId not found"));

            Patient patient = new Patient(patientDto, appUser, hospital, staff);
            patientRepository.save(patient);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Something is Wrong");
        }
    }

    @Override
    public PatientDto getPatientById(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patient is not found with id : " + id));
        return mapToDto(patient);
    }

    @Override
    public Page<PatientPaginationDto> getAllPatient(int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Patient> patients = patientRepository.findAll(pageable);

        List<PatientPaginationDto> list = patients
                .stream().map(this::mapToDto)
                .map(patient -> new PatientPaginationDto(patient,
                        staffService.mapToDto(staffRepository.findById(patient.getStaffId()).orElseThrow()),
                        appUserService.mapToDto(appUserRepository.findById(patient.getAppUserId()).orElseThrow()),
                        hospitalService.mapToDto(hospitalRepository.findById(patient.getHospitalId()).orElseThrow())))
                .toList();

        return new PageImpl<>(list, pageable, patients.getTotalElements());
    }

    @Override
    public void updatePatient(int id, PatientDto patientDto) {

        Patient patient = patientRepository.findById((long) id).orElseThrow(() -> new ResourceNotFoundException("Patient is not found with id : " + id));

        AppUser appUser = appUserRepository.findById(patientDto.getAppUserId()).orElseThrow();
        Hospital hospital = hospitalRepository.findById(patientDto.getHospitalId()).orElseThrow();
        Staff staff = staffRepository.findById(patientDto.getStaffId()).orElseThrow();

        if (patientDto.getStatus() != null) patient.setStatus(patientDto.getStatus());
        if (patientDto.getMedicalHistory() != null) patient.setMedicalHistory(patientDto.getMedicalHistory());
        if (patientDto.getAdmittedDate() != null) patient.setAdmittedDate(patientDto.getAdmittedDate());
        if (patientDto.getHospitalId() != null) patient.setHospital(hospital);
        if (patientDto.getAppUserId() != null) patient.setAppUser(appUser);

        patientRepository.save(patient);
    }

    @Override
    public void deletePatientById(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patient is not found with id : " + id));
        patientRepository.delete(patient);
    }

    public PatientDto mapToDto(Patient patient) {
        return new PatientDto(patient);
    }


    // GET ALL PATIENT WITH THE PATIENT NAME OR DOCTOR NAME AND FILTER
    @Override
    public Page<PatientPaginationDto> getPatientByPatientOrDoctorNameAndFilter(String searchValue, List<Long> doctorId,
                                                                               List<Long> hospitalId, int pageNumber,
                                                                               int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Patient> patients;
        if (searchValue == null && doctorId == null && hospitalId == null) patients = patientRepository.findAll(pageable);
        else {
            if(searchValue==null) searchValue="";
            if(doctorId==null) doctorId = Collections.emptyList();
            if(hospitalId==null) hospitalId = Collections.emptyList();

            patients = patientRepository.findByPatientOrDoctorName(searchValue, doctorId, hospitalId, pageable);
        }

        List<PatientPaginationDto> patientDtoList = patients.stream()
                .map(this::mapToDto)
                .map(patient -> new PatientPaginationDto(patient
                        , staffService.mapToDto(staffRepository.findById(patient.getStaffId()).orElseThrow())
                        , appUserService.mapToDto(appUserRepository.findById(patient.getAppUserId()).orElseThrow())
                        , hospitalService.mapToDto(hospitalRepository.findById(patient.getHospitalId()).orElseThrow())))
                .toList();

        return new PageImpl<>(patientDtoList, pageable, patients.getTotalElements());
    }
}
