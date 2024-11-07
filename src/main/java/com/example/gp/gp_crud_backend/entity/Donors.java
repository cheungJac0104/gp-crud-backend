package com.example.gp.gp_crud_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "donors")
public class Donors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int donor_id;

    @Column
    public String name;
    public String email;
    public String phone;
    public String address;
    public String type;
    public String username;
    public String password_hash;
    public String salt;
}
