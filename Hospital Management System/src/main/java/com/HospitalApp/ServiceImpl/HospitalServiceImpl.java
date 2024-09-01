package com.HospitalApp.ServiceImpl;

import com.HospitalApp.DTOs.HospitalDto;
import com.HospitalApp.Model.Hospital;
import com.HospitalApp.Model.ResourceNotFoundException;
import com.HospitalApp.Repository.AppUserRepository;
import com.HospitalApp.Repository.HospitalRepository;
import com.HospitalApp.Repository.PatientRepository;
import com.HospitalApp.Repository.StaffRepository;
import com.HospitalApp.Service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Override
    public void createHospital(HospitalDto hospitalDto){
        try{
            Hospital hospital = new Hospital(hospitalDto);
            hospitalRepository.save(hospital);
        }
        catch (Exception e){
            throw new ResourceNotFoundException("Hospital is already Registered with this Email or PhoneNumber!");
        }
    }

    @Override
    public HospitalDto getHospitalById(Long id){
        Hospital hospital = hospitalRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Hospital is not found with id : "+id));
        return mapToDto(hospital);
    }

    @Override
    public Page<HospitalDto> getAllHospitals(Integer pageNumber, Integer pageSize){

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Hospital> hospitalPage = hospitalRepository.findAll(pageable);

        List<HospitalDto> list = hospitalPage.stream().map(this::mapToDto).toList();

        return new PageImpl<>(list, pageable, hospitalPage.getTotalElements());
    }

    @Override
    public void updateHospital(int id, HospitalDto hospitalDto){
        Hospital hospital = hospitalRepository.findById((long)id).orElseThrow(
                ()-> new ResourceNotFoundException("Hospital is not found with id : "+id));

        if(hospitalDto.getName()!=null) hospital.setName((hospitalDto.getName()));
        if(hospitalDto.getEmail()!=null) hospital.setEmail(hospitalDto.getEmail());
        if(hospitalDto.getAddress()!=null)  hospital.setAddress(hospitalDto.getAddress());
        if(hospitalDto.getPhoneNumber()!=null)  hospital.setPhoneNumber(hospitalDto.getPhoneNumber());

        hospitalRepository.save(hospital);
    }

    @Override
    public void deleteHospitalById(Long id){
        Hospital hospital = hospitalRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Hospital is not found with id : "+id));
        hospitalRepository.delete(hospital);
    }

    public HospitalDto mapToDto(Hospital hospital) {
        return new HospitalDto(hospital);
    }


    // GET HOSPITAL DETAILS BY NAME OR ADDRESS
    @Override
    public Page<HospitalDto> getHospitalByNameOrAddress(String searchValue, int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Hospital> hospitalPage;

        if(searchValue==null) hospitalPage = hospitalRepository.findAll(pageable);
        else hospitalPage =  hospitalRepository.findByNameAndAddess(searchValue, pageable);

        List<HospitalDto> hospitalDtoList = hospitalPage.stream().map(HospitalDto::new).toList();
        return new PageImpl<>(hospitalDtoList, pageable, hospitalPage.getTotalElements());
    }
}
