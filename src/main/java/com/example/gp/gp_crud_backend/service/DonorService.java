package com.example.gp.gp_crud_backend.service;


import jakarta.ejb.Stateless;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.example.gp.gp_crud_backend.apiDTO.RegisterRequest;
import com.example.gp.gp_crud_backend.entity.Donors;
import com.example.gp.gp_crud_backend.entity.entityEmperor;
import com.example.gp.gp_crud_backend.utilities.JWTUtil;
import com.example.gp.gp_crud_backend.utilities.PasswordUtils;
import com.example.gp.gp_crud_backend.utilities.UserCache;

@Stateless
@Dependent
public class DonorService {

    @Inject
    private JWTUtil jwtUtil;

    @Inject
    private entityEmperor emperor;

    @Inject
    private EmailService emailService;
    
    public String login(String username, String password) throws InterruptedException, ExecutionException {
        entityEmperor.ColumnValuePair columnValuePair = new entityEmperor.ColumnValuePair("username", username);
        var columns = List.of(columnValuePair);
        List<Donors> donors = emperor.donorFindByColumns(columns);

        if(donors.isEmpty()) return "";
        for (Donors drs : donors) {
            String storedSalt = drs.salt; // Assuming you have a salt field in your Donor entity
            String storedEncryptedPassword = drs.password_hash; // Assuming you have an encrypted password field

            String encryptedPassword = PasswordUtils.encryptPassword(password, storedSalt);
            if (storedEncryptedPassword.equals(encryptedPassword)) {

                var token = jwtUtil.generateToken(drs.donor_id,encryptedPassword);
                return token;
            }
        }

        return "";
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
        return _rtn;
    }


    public Donors getDonor() throws InterruptedException, ExecutionException{
        var hash = UserCache.getCurrentHash().get();
            if (hash == null) return null;

            // System.out.println("password hash: " + hash);
            var donor = emperor.donorFindByColumns(List.of(new entityEmperor.ColumnValuePair("password_hash", hash)));
            if(donor.size() == 0) return null;
            return donor.get(0);
    }

    public boolean updateDonor(Donors donor, String new_password) {
        try {
            if(!new_password.isBlank() && !new_password.isEmpty())
            {
                donor.password_hash = PasswordUtils.encryptPassword(new_password, donor.salt);
            }      
            emperor.updateDonors(donor);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
}
