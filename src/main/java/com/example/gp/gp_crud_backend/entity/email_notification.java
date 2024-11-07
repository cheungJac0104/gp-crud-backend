package com.example.gp.gp_crud_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "email_notification")
public class email_notification {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        public int notification_id;

        @Column
        public int donor_id;
        public String email_type;
        public String email_address;
        public String subject;
        public String body;
        public String status;
        public String timestamp;
}
