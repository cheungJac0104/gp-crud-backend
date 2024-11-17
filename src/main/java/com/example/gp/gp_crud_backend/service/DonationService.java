package com.example.gp.gp_crud_backend.service;

import java.text.SimpleDateFormat;
import java.util.List;

import com.example.gp.gp_crud_backend.entity.Donations;
import com.example.gp.gp_crud_backend.entity.Acknowledgments;
import com.example.gp.gp_crud_backend.entity.entityEmperor;

import jakarta.ejb.Stateless;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

@Stateless
@Dependent
public class DonationService {


    @Inject
    private entityEmperor emperor;

    @Inject
    private DonorService donorServices;

    public boolean donation(Donations donation) {
        try {

            // System.out.println("password hash: " + hash);
            var donor = donorServices.getDonor();
            if(donor == null) return false;
            donation.donor_id = donor.donor_id;
            emperor.insertDonations(donation);

            var donation_program = emperor.getDonation_ProgramsById(donation.program_id);
            donation_program.amount += donation.amount;
            emperor.updateDonation_Programs(donation_program);

            Acknowledgments ack = new Acknowledgments();
            ack.donation_id = donation.donation_id;
            ack.date = new SimpleDateFormat("yyyy-MM-dd").parse(donation.date);
            ack.message = "";
            emperor.insertAcknowledgments(ack);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error adding donation: " + e.getMessage());
            return false;
        }
    }

    public List<Donations> getDonationsByDonor() {
        try {

            var donor = donorServices.getDonor();
            if(donor == null) return null;
            var donor_id = donor.donor_id;

            return emperor.getDonationsById(donor_id);
            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
        
    }

    public List<Donations> getDonations() {
        try {
            return emperor.getDonations();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
        
    }

    
}
