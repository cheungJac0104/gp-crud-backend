package com.example.gp.gp_crud_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "donations")
public class Donations {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int donation_id;

    @Column
    public int donor_id;
    public int program_id;
    public int amount;
    public String date;
}
