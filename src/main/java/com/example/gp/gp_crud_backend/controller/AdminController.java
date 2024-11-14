package com.example.gp.gp_crud_backend.controller;

import com.example.gp.gp_crud_backend.apiDTO.ApiResponse;
import com.example.gp.gp_crud_backend.entity.Donation_Programs;
import com.example.gp.gp_crud_backend.service.DonationProgramService;

import jakarta.inject.Inject;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/admin")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationPath("api")
public class AdminController {
    @Inject
    private DonationProgramService donationProgramService;

    @GET
    @Path("/retrieveDonationProgram")
    public Response retrieveDonationProgram() {
        try {

            var donationProgram = donationProgramService.getAllDonationPrograms();
            
            return Response.ok(new ApiResponse("Retrieve Donation Program Success", donationProgram)).build();
            
        } catch (Exception e) {}

            return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @Path("/addDonationProgram")
    public Response addDonationProgram(Donation_Programs donationProgram) {

        System.out.println("Processing addDonationProgram");
        
        try {
            if (donationProgramService.addDonationProgram(donationProgram)) {
                return Response.status(Response.Status.OK).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/updateDonationProgram")
    public Response updateDonationProgram(Donation_Programs donationProgram) {
        try {
            if (donationProgramService.updateDonationProgram(donationProgram)) {
                return Response.status(Response.Status.OK).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
