package com.Banking.Util;

import com.Banking.Exception.Classes.ResourceNotFoundException;
import com.Banking.Model.AppUser.AppUser;
import com.Banking.Repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppUserUtil {

    @Autowired
    private AppUserRepository appUserRepository;

    public AppUser findById(Long id){
        return appUserRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("No User found with this id : "+id));
    }

    public void deleteById(Long id){
        appUserRepository.deleteById(id);
    }

    public AppUser findByEmail(String email){
        return appUserRepository.findByEmail(email).orElseThrow(
                ()-> new ResourceNotFoundException("No User found with this email : "+email));
    }

    public boolean existsByEmail(String email){
        return appUserRepository.existsByEmail(email).orElseThrow(
                ()-> new ResourceNotFoundException("No User Exists with this email : "+email));
    }

    public void deleteByEmail(String email){
        appUserRepository.deleteByEmail(email).orElseThrow(
                ()-> new ResourceNotFoundException("No User found with this email : "+email));;
    }

}
