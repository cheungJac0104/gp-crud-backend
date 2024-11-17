package com.example.gp.gp_crud_backend.service;

import java.util.List;

import com.example.gp.gp_crud_backend.entity.Donation_Programs;
import com.example.gp.gp_crud_backend.entity.entityEmperor;

import jakarta.ejb.Stateless;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

@Stateless
@Dependent
public class DonationProgramService {

    @Inject
    private entityEmperor emperor;

    public List<Donation_Programs> getAllDonationPrograms() {
        return emperor.getDonation_Programs();
    }

    public boolean addDonationProgram(Donation_Programs donationProgram) {

        try {
                emperor.insertDonation_Programs(donationProgram);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error adding donation program: " + e.getMessage());
            return false;
        }
        
    }

    public boolean updateDonationProgram(Donation_Programs donationProgram) {
        try {
            Donation_Programs dp_rtn = emperor.getDonation_ProgramsById(donationProgram.program_id);
            if (dp_rtn == null) {
                System.out.println("No donation program found with id: " + donationProgram.program_id);
                return false;
            }
            emperor.updateDonation_Programs(donationProgram);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error updating donation program: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteDonationProgram(int program_id) {
        try {
            Donation_Programs donationProgram = emperor.getDonation_ProgramsById(program_id);
            if (donationProgram == null) {
                System.out.println("No donation program found with id: " + program_id);
                return false;
            }
            emperor.deleteDonation_Programs(donationProgram);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error deleting donation program: " + e.getMessage());
            return false;
        }
    }
}
