package com.example.gp.gp_crud_backend.controller;


import com.example.gp.gp_crud_backend.apiDTO.ApiResponse;
import com.example.gp.gp_crud_backend.service.DonationProgramService;
import com.example.gp.gp_crud_backend.service.DonationService;
import com.example.gp.gp_crud_backend.service.DonorService;

import jakarta.inject.Inject;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/user")
@ApplicationPath("api")
public class UserController {

    @Inject
    private DonationProgramService donationProgramService;

    @Inject
    private DonorService donorService;

    @Inject
    private DonationService donationService;


    @GET
    @Path("/getDonationProgram")
    public Response getDonationProgram() {
        try {

            var donationProgram = donationProgramService.getAllDonationPrograms();
            
            return Response.ok(new ApiResponse("Retrieve Donation Program Success", donationProgram)).build();
            
        } catch (Exception e) {}

            return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
}
