package com.example.gp.gp_crud_backend.controller;

import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.example.gp.gp_crud_backend.apiDTO.ApiResponse;
import com.example.gp.gp_crud_backend.apiDTO.AppreciationRequest;
import com.example.gp.gp_crud_backend.entity.Acknowledgments;
import com.example.gp.gp_crud_backend.entity.Donation_Programs;
import com.example.gp.gp_crud_backend.service.AcknowledgmentService;
import com.example.gp.gp_crud_backend.service.DonationProgramService;
import com.example.gp.gp_crud_backend.service.DonationService;
import com.example.gp.gp_crud_backend.service.DonorService;
import com.example.gp.gp_crud_backend.service.EmailService;
import com.example.gp.gp_crud_backend.service.UserActivityService;

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

    @Inject
    private UserActivityService userActivityService;

    @Inject
    private DonorService donorService;

    @Inject
    private DonationService donationService;

    @Inject
    private AcknowledgmentService acknowledgmentService;

    @Inject
    private EmailService emailService;

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

    @GET
    @Path("/getUserActivity")
    public Response getUserActivity() {
        try {
            var userActivity = userActivityService.getUserActivities();
            
            return Response.ok(new ApiResponse("Retrieve User Activity Success", userActivity)).build();
            
        } catch (Exception e) 
        {
            return Response.status(Response.Status.BAD_REQUEST).build();

        }
    }

    @GET
    @Path("/getDonors")
    public Response getDonors() {
        try {
            var donors = donorService.getDonors();
            return Response.ok(new ApiResponse("Get Donor Profile Success", donors)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/getDonationRecords")
    public Response getDonationRecords() {
        try {
            var donations = donationService.getDonations();
            var acks = acknowledgmentService.getAcknowledgments(donations);

            if(donations == null || acks == null) return Response.status(Response.Status.BAD_REQUEST).build();

            var combinedRecords = IntStream
            .range(0, donations.size())
            .mapToObj(i -> {
                var donation_id = donations.get(i).donation_id;
                var amount = donations.get(i).amount;
                var date = donations.get(i).date;
                var donor_id = donations.get(i).donor_id;
                var program_id = donations.get(i).program_id;
                var ack_message = acks.get(i).message;
                var ack_id = acks.get(i).ack_id;
                return Map.of(
                    "donation_id", donation_id,
                    "amount", amount,
                    "date", date,
                    "donor_id", donor_id,
                    "program_id", program_id,
                    "ack_id", ack_id,
                    "ack_message", ack_message);
            })
            .collect(Collectors.toList());

            return Response.ok(new ApiResponse("Get Donations Record Success", combinedRecords)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/sendAppreciationEmail")
    public Response sendAppreciationEmail(AppreciationRequest request) {
        try {
            var ack = new Acknowledgments();
            ack.ack_id = request.ack_id;
            ack.date = new Date();
            ack.message = request.ack_message;

            if(acknowledgmentService.UpdateAcknowledgments(ack) == false) return Response.status(Response.Status.BAD_REQUEST).build();

            String to = request.donorEmail;
            String subject = "Appreciation from " + request.program_name;
            emailService.sendAppreciationEmail(to, subject, request);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
