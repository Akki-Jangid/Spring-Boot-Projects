package com.HospitalApp.DTOs;

import com.HospitalApp.Model.Hospital;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Setter
@Getter
public class HospitalDto {

    @JsonIgnore
    private long id;

    @NotBlank(message = "Hospital name is Mandatory")
    private String name;

    @NotBlank(message = "Address is Mandatory")
    private String address;

    @NotBlank(message = "Phone number is mandatory")
    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits")
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Phone number is invalid")
    private String phoneNumber;

    @NotBlank(message = "Email is Mandatory")
    @Email(message = "Enter Valid Email")
    private String email;

    public  HospitalDto(Hospital hospital){
        this.id = hospital.getId();
        this.name = hospital.getName();
        this.address = hospital.getAddress();
        this.phoneNumber = hospital.getPhoneNumber();
        this.email = hospital.getEmail();
    }


}
