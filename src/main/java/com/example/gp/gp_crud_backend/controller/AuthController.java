package com.example.gp.gp_crud_backend.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import com.example.gp.gp_crud_backend.requestDTO.LoginRequest;
import com.example.gp.gp_crud_backend.requestDTO.RegisterRequest;
import com.example.gp.gp_crud_backend.service.DonorService;
import com.example.gp.gp_crud_backend.utilities.JWTUtil;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationPath("api")
public class AuthController {

    @Inject
    private JWTUtil jwtUtil;

    @Inject
    private DonorService donorService;


    @POST
    @Path("/login")
    public Response login(LoginRequest request) {
        
        try {
        
            if (donorService.login(request.username, request.password)) {
                String token = jwtUtil.generateToken(request.username);
                return Response.ok(token).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED)
                         .entity("Invalid credentials")
                         .build();
            }
        
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                         .entity("Login failed")
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
