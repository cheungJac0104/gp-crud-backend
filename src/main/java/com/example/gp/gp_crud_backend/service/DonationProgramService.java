package com.example.gp.gp_crud_backend.service;

import java.util.List;

import com.example.gp.gp_crud_backend.entity.Donation_Programs;
import com.example.gp.gp_crud_backend.entity.entityEmperor;
import com.example.gp.gp_crud_backend.utilities.JWTUtil;

import jakarta.inject.Inject;

public class DonationProgramService {
    @Inject
    private JWTUtil jwtUtil;

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

    public boolean updateDonationProgram(int program_id) {
        try {
            Donation_Programs donationProgram = emperor.getDonation_ProgramsById(program_id);
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
            emperor.deleteDonation_Programs(donationProgram);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error deleting donation program: " + e.getMessage());
            return false;
        }
    }
}
