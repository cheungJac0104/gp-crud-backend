package com.example.gp.gp_crud_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_activity")
public class UserActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    
    @Column
    public int user_id;
    public String activity;  
    public String timestamp;
    public String ip_address;
    
}
