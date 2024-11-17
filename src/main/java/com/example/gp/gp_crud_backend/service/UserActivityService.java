package com.example.gp.gp_crud_backend.service;

import java.util.List;

import com.example.gp.gp_crud_backend.entity.Donors;
import com.example.gp.gp_crud_backend.entity.entityEmperor;
import com.example.gp.gp_crud_backend.utilities.UserActivity;
import com.example.gp.gp_crud_backend.utilities.UserCache;

import jakarta.ejb.Stateless;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

@Stateless
@Dependent
public class UserActivityService {
    @Inject
    private entityEmperor emperor;


    public void logUserActivity(Donors user, UserActivity activity) {

        try {
            var act_desc = UserActivity.getDescription(activity);
            var ip_address = UserCache.getCurrentIpAddress().orElse(null);

            var userActivity = new com.example.gp.gp_crud_backend.entity.UserActivity();
            userActivity.user_id = user.donor_id;
            userActivity.activity = act_desc;
            userActivity.timestamp = java.time.LocalDateTime.now().toString();
            userActivity.ip_address = ip_address;
            emperor.insertUserActivities(userActivity);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println("Error logging user activity: " + e.getMessage());
        }
               
    }

    public List<com.example.gp.gp_crud_backend.entity.UserActivity> getUserActivities() {
        try {
            var userActivities = emperor.getUserActivities();
            return userActivities;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println("Error retrieving user activities: " + e.getMessage());
            return null;
    }
    
    }
}
