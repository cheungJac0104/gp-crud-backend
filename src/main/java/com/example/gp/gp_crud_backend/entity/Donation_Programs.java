package com.example.gp.gp_crud_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "donation_programs")
public class Donation_Programs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int program_id;

    @Column
    public String program_name;
    public String description;
    public String start_date;
    public String end_date;
    public String status;
    public int amount;
}
