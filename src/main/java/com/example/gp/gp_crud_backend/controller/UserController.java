package com.example.gp.gp_crud_backend.controller;


import com.example.gp.gp_crud_backend.apiDTO.ApiResponse;
import com.example.gp.gp_crud_backend.entity.Donations;
import com.example.gp.gp_crud_backend.service.AcknowledgmentService;
import com.example.gp.gp_crud_backend.service.DonationProgramService;
import com.example.gp.gp_crud_backend.service.DonationService;
import com.example.gp.gp_crud_backend.service.DonorService;
import com.example.gp.gp_crud_backend.utilities.Secured;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Secured
@Priority(Priorities.AUTHENTICATION)
@ApplicationPath("api")
public class UserController {

    @Inject
    private DonationProgramService donationProgramService;

    @Inject
    private DonorService donorService;

    @Inject
    private DonationService donationService;

    @Inject
    private AcknowledgmentService acknowledgmentService;


    @GET
    @Path("/getDonationProgram")
    public Response getDonationProgram() {
        try {

            var donationProgram = donationProgramService.getAllDonationPrograms();
            
            return Response.ok(new ApiResponse("Retrieve Donation Program Success", donationProgram)).build();
            
        } catch (Exception e) {}

            return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @Path("/donate")
    public Response donate(Donations donation) {
        try {

            if(donationService.donation(donation)){
                return Response.ok(new ApiResponse("Donation Success", donation)).build();
            }
            else
            {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

        } catch (Exception e) {}
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @GET
    @Path("/getDonationsByDonor")
    public Response getDonationsByDonor() {
        try {
            var donations = donationService.getDonationsByDonor();
            return Response.ok(new ApiResponse("Get Donations Record Success", donations)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/getAcknowledgmentsByDonor")
    public Response getAcknowledgmentsByDonor() {
        try {
            var donations = donationService.getDonationsByDonor();
            var acknowledgments = acknowledgmentService.getAcknowledgments(donations);
            return Response.ok(new ApiResponse("Get Acknowledgments Record Success", acknowledgments)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
