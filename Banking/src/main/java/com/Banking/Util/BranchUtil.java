package com.Banking.Util;

import com.Banking.Exception.Classes.ResourceNotFoundException;
import com.Banking.Model.Branch.Branch;
import com.Banking.Repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BranchUtil {

    @Autowired
    private BranchRepository branchRepository;

    public Branch findById(Long id){
        return branchRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No Branch found with this id : "+id));
    }

    public void deleteByIfscCode(String code){
        branchRepository.deleteByIfscCode(code).orElseThrow(
                () -> new ResourceNotFoundException("No Branch found with this IFSC Code : "+code));;
    }
}
