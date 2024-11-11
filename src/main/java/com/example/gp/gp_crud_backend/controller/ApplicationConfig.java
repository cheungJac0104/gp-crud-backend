package com.example.gp.gp_crud_backend.controller;
// ApplicationConfig.java
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import jakarta.annotation.PostConstruct;
import java.util.Set;

import com.example.gp.gp_crud_backend.utilities.CORSFilter;
import com.example.gp.gp_crud_backend.utilities.JWTAuthenticationFilter;

import java.util.HashSet;

@ApplicationScoped
@ApplicationPath("/api")
public class ApplicationConfig extends Application {
    
    @PostConstruct
    public void init() {
        System.out.println("Application initialized with SSH and JWT support");
    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(AuthController.class);
        classes.add(UserController.class);
        classes.add(AdminController.class);
        classes.add(JWTAuthenticationFilter.class);
        classes.add(CORSFilter.class);
        return classes;
    }
}
