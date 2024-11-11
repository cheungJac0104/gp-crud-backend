package com.example.gp.gp_crud_backend.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import com.example.gp.gp_crud_backend.apiDTO.LoginRequest;
import com.example.gp.gp_crud_backend.apiDTO.RegisterRequest;
import com.example.gp.gp_crud_backend.apiDTO.ApiResponse;
import com.example.gp.gp_crud_backend.service.DonorService;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationPath("api")
public class AuthController {

    @Inject
    private DonorService donorService;


    @POST
    @Path("/login")
    public Response login(LoginRequest request) {
        
        try {

            var token = donorService.login(request.username, request.password);
        
            if (!token.isEmpty()) {
                return Response.ok(new ApiResponse("Login successful", token)).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED)
                         .entity("Invalid credentials")
                         .build();
            }
        
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                         .entity(new ApiResponse("Error", e.getMessage()))
                         .build();
        }

        
    }

    @POST
    @Path("/register")
    public Response register(RegisterRequest request) {
        try {
            // Add validation logic here
            if(donorService.register(request))
            {
                return Response.status(Response.Status.OK).build();
            }
            else
            {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                         .entity(e.getMessage())
                         .build();
        }
    }



}
