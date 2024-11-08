package com.example.gp.gp_crud_backend.service;


import jakarta.ejb.Stateless;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import java.util.List;

import com.example.gp.gp_crud_backend.entity.Donors;
import com.example.gp.gp_crud_backend.entity.entityEmperor;
import com.example.gp.gp_crud_backend.requestDTO.RegisterRequest;
import com.example.gp.gp_crud_backend.utilities.PasswordUtils;

@Stateless
@Dependent
public class DonorService {

    @Inject
    private entityEmperor emperor;

    @Inject
    private EmailService emailService;
    
    public boolean login(String username, String password) {
        entityEmperor.ColumnValuePair columnValuePair = new entityEmperor.ColumnValuePair("username", username);
        var columns = List.of(columnValuePair);
        List<Donors> donors = emperor.donorFindByColumns(columns);

        if(donors.isEmpty()) return false;
        for (Donors drs : donors) {
            String storedSalt = drs.salt; // Assuming you have a salt field in your Donor entity
            String storedEncryptedPassword = drs.password_hash; // Assuming you have an encrypted password field

            String encryptedPassword = PasswordUtils.encryptPassword(password, storedSalt);
            if (storedEncryptedPassword.equals(encryptedPassword)) {
                return true;
            }
        }

        return false;
    } 

    public boolean register(RegisterRequest request) {
        Donors donor = new Donors();
        donor.name = request.name;
        donor.email = request.email;
        donor.phone = request.phone;
        donor.address = request.address;
        donor.type = request.type;
        donor.username = request.username;
        String password = PasswordUtils.generatePassword();
        donor.salt = PasswordUtils.generateSalt();
        donor.password_hash = PasswordUtils.encryptPassword(password, donor.salt);
        

        // Send email to notify user of password
        String to = request.email;
        String subject = "Your password for our system";

        var _rtn = emperor.insertDonors(donor);

        if(_rtn)
        {
            emailService.sendAccountCreatedEmail(to, subject, password);
        }  
        return emperor.insertDonors(donor);
    }

    
}
