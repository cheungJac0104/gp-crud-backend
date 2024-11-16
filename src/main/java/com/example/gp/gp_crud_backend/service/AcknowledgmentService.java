package com.example.gp.gp_crud_backend.service;

import com.example.gp.gp_crud_backend.entity.entityEmperor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.gp.gp_crud_backend.entity.Acknowledgments;
import com.example.gp.gp_crud_backend.entity.Donations;

import jakarta.inject.Inject;

public class AcknowledgmentService {

    @Inject
    private entityEmperor emperor;

    public List<Acknowledgments> getAcknowledgments(List<Donations> donations) {
        try {
            var list = emperor.getAcknowledgments();
            var result = new ArrayList<Acknowledgments>(list.size());
            for (Donations don : donations) {
                result.addAll(list.stream().filter(ack -> ack.donation_id ==don.donation_id).collect(Collectors.toList()));
            }
            return result;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println("Error retrieving acknowledgments: " + e.getMessage());
            return null;
        }

    }
    
}
