package com.example.gp.gp_crud_backend.entity;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "Acknowledgments")
public class Acknowledgments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int ack_id;

    @Column
    public int donation_id;
    public String message;
    public Date date;
}
