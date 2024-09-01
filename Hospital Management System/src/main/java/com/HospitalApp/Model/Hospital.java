package com.HospitalApp.Model;


import com.HospitalApp.DTOs.HospitalDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Hospital extends CommonBase {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String email;

    public  Hospital(HospitalDto hospitalDto){
        this.name = hospitalDto.getName();
        this.address = hospitalDto.getAddress();
        this.phoneNumber = hospitalDto.getPhoneNumber();
        this.email = hospitalDto.getEmail();
    }
}
