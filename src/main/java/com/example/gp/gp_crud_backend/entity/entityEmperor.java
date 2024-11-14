package com.example.gp.gp_crud_backend.entity;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.util.Collections;
import java.util.List;

@RequestScoped
public class entityEmperor {

    @PersistenceContext
    private EntityManager entityManager;

    //region GetOne
    public List<Donors> donorFindByColumns(List<ColumnValuePair> columnValuePairs) {
        try {
            StringBuilder queryBuilder = new StringBuilder("select d from Donors d where ");
            for (int i = 0; i < columnValuePairs.size(); i++) {
                ColumnValuePair pair = columnValuePairs.get(i);
                queryBuilder.append("d.").append(pair.getColumnName()).append(" = :value_" + i);
                if (i < columnValuePairs.size() - 1) {
                    queryBuilder.append(" and ");
                }
            }
            // Create the query
            TypedQuery<Donors> query = entityManager.createQuery(queryBuilder.toString(), Donors.class);
            // Set the parameters
            for (int i = 0; i < columnValuePairs.size(); i++) {
                ColumnValuePair pair = columnValuePairs.get(i);
                query.setParameter("value_" + i, pair.getValue());
            }


            // Execute the query
            List<Donors> donors = query.getResultList();
            return donors;
        } catch (PersistenceException e) {
            System.out.println("Persistence Exception: Error retrieving donors: " + e.getMessage());
            // Optionally, log the stack trace or handle the exception further
            e.printStackTrace();
            return Collections.emptyList();
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException: Error retrieving donors: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }       
        catch (Exception e) {
            System.out.println("Exception: Error retrieving donors: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


    public Donation_Programs getDonation_ProgramsById(int program_id) {
        try {
            TypedQuery<Donation_Programs> query = entityManager.createQuery("select d from Donation_Programs d where d.program_id = :id", Donation_Programs.class);
            query.setParameter("id", program_id);
            Donation_Programs donation_Programs = query.getSingleResult();
            return donation_Programs;
        } catch (PersistenceException e) {
            System.out.println("Error retrieving donation program: " + e.getMessage());
            // Optionally, log the stack trace or handle the exception further
            e.printStackTrace();
            return null;
        }
    }


    //endregion

    //region Get
    public List<Donors> getDonors() {
        try {
            TypedQuery<Donors> query = entityManager.createQuery("select d from Donors d", Donors.class);
            List<Donors> donors = query.getResultList();
            return donors;
        } catch (PersistenceException e) {
            System.out.println("Error retrieving donors: " + e.getMessage());
            // Optionally, log the stack trace or handle the exception further
            e.printStackTrace();
            return null;
        }
    }

    public List<Donation_Programs> getDonation_Programs() {
        try {
            TypedQuery<Donation_Programs> query = entityManager.createQuery("select d from Donation_Programs d", Donation_Programs.class);
            List<Donation_Programs> donation_Programs = query.getResultList();
            return donation_Programs;
        } catch (PersistenceException e) {
            System.out.println("Error retrieving donation programs: " + e.getMessage());
            // Optionally, log the stack trace or handle the exception further
            e.printStackTrace();
            return null;
        }
    }

    public List<Donations> getDonations() {
        try {
            TypedQuery<Donations> query = entityManager.createQuery("select d from Donations d", Donations.class);
            List<Donations> donations = query.getResultList();
            return donations;
        } catch (PersistenceException e) {
            System.out.println("Error retrieving donations: " + e.getMessage());
            // Optionally, log the stack trace or handle the exception further
            e.printStackTrace();
            return null;
        }
    }

    public List<Acknowledgments> getAcknowledgments() {
        try {
            TypedQuery<Acknowledgments> query = entityManager.createQuery("select d from Acknowledgments d", Acknowledgments.class);
            List<Acknowledgments> acknowledgments = query.getResultList();
            return acknowledgments;
        } catch (PersistenceException e) {
            System.out.println("Error retrieving acknowledgments: " + e.getMessage());
            // Optionally, log the stack trace or handle the exception further
            e.printStackTrace();
            return null;
        }
    }

    public List<email_notification> getemail_notification() {
        try {
            TypedQuery<email_notification> query = entityManager.createQuery("select d from email_notification d", email_notification.class);
            List<email_notification> emailNotification = query.getResultList();
            return emailNotification;
        } catch (PersistenceException e) {
            System.out.println("Error retrieving email notification: " + e.getMessage());
            // Optionally, log the stack trace or handle the exception further
            e.printStackTrace();
            return null;
        }
    }

    //endregion

    //region Insert

    @Transactional
    public boolean insertDonors(Donors donors) {
        try {
            entityManager.persist(donors);
            System.out.println("Donor inserted successfully.");
            return true;
        } catch (PersistenceException e) {
            System.out.println("Error inserting donor: " + e.getMessage());
            // Optionally, log the stack trace or handle the exception further
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.out.println("Error inserting donor: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public void insertDonation_Programs(Donation_Programs donation_Programs) {
        try {
            entityManager.persist(donation_Programs);
            System.out.println("Donation program inserted successfully.");
        } catch (PersistenceException e) {
            System.out.println("Error inserting donation program: " + e.getMessage());
            // Optionally, log the stack trace or handle the exception further
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error inserting donation program: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Transactional
    public void insertDonations(Donations donations) {
        try {
            entityManager.persist(donations);
            System.out.println("Donation inserted successfully.");
        } catch (PersistenceException e) {
            System.out.println("Error inserting donation: " + e.getMessage());
            // Optionally, log the stack trace or handle the exception further
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error inserting donation: " + e.getMessage());
            e.printStackTrace();
        }
        
    }

    @Transactional
    public void insertAcknowledgments(Acknowledgments acknowledgments) {  
        try {
            entityManager.persist(acknowledgments);
            System.out.println("Acknowledgment inserted successfully.");
        } catch (PersistenceException e) {
            System.out.println("Error inserting acknowledgment: " + e.getMessage());
            // Optionally, log the stack trace or handle the exception further
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error inserting acknowledgment: " + e.getMessage());
            e.printStackTrace();
        }  
        
    }

    @Transactional
    public void insertemail_notification(email_notification emailNotification) {
        try {
            entityManager.persist(emailNotification);
            System.out.println("Email notification inserted successfully.");
        } catch (PersistenceException e) {
            System.err.println("Error inserting email notification: " + e.getMessage());
            // Optionally, log the stack trace or handle the exception further
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //endregion

    //region Update

    @Transactional
    public void updateDonors(Donors donors) {
        try {
            entityManager.merge(donors);
            System.out.println("Donor updated successfully.");
        } catch (PersistenceException e) {
            System.out.println("Error updating donor: " + e.getMessage());
            // Optionally, log the stack trace or handle the exception further
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error updating donor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Transactional
    public void updateDonation_Programs(Donation_Programs donation_Programs) {
        try {
            entityManager.merge(donation_Programs);
            System.out.println("Donation program updated successfully.");
        } catch (PersistenceException e) {
            System.out.println("Error updating donation program: " + e.getMessage());
            // Optionally, log the stack trace or handle the exception further
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error updating donation program: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Transactional
    public void updateDonations(Donations donations) {
        try {
            entityManager.merge(donations);
            System.out.println("Donation updated successfully.");
        } catch (PersistenceException e) {
            System.out.println("Error updating donation: " + e.getMessage());
            // Optionally, log the stack trace or handle the exception further
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error updating donation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Transactional
    public void updateAcknowledgments(Acknowledgments acknowledgments) {
        try {
            entityManager.merge(acknowledgments);
            System.out.println("Acknowledgment updated successfully.");
        } catch (PersistenceException e) {
            System.out.println("Error updating acknowledgment: " + e.getMessage());
            // Optionally, log the stack trace or handle the exception further
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error updating acknowledgment: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Transactional
    public void updateemail_notification(email_notification emailNotification) {
        try {
            entityManager.merge(emailNotification);
            System.out.println("Email notification updated successfully.");
        } catch (PersistenceException e) {
            System.out.println("Error updating email notification: " + e.getMessage());
            // Optionally, log the stack trace or handle the exception further
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error updating email notification: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //endregion
    
    // region Delete

    @Transactional
    public void deleteDonors(Donors donors) {
        try {
            entityManager.remove(donors);
            System.out.println("Donor deleted successfully.");
        } catch (PersistenceException e) {
            System.out.println("Error deleting donor: " + e.getMessage());
            // Optionally, log the stack trace or handle the exception further
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error deleting donor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Transactional
    public void deleteDonation_Programs(Donation_Programs donation_Programs) {
        try {
            entityManager.remove(donation_Programs);
            System.out.println("Donation program deleted successfully.");
        } catch (PersistenceException e) {
            System.out.println("Error deleting donation program: " + e.getMessage());
            // Optionally, log the stack trace or handle the exception further
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error deleting donation program: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Transactional
    public void deleteDonations(Donations donations) {
        try {
            entityManager.remove(donations);
            System.out.println("Donation deleted successfully.");
        } catch (PersistenceException e) {
            System.out.println("Error deleting donation: " + e.getMessage());
            // Optionally, log the stack trace or handle the exception further
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error deleting donation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Transactional
    public void deleteAcknowledgments(Acknowledgments acknowledgments) {
        try {
            entityManager.remove(acknowledgments);
            System.out.println("Acknowledgment deleted successfully.");
        } catch (PersistenceException e) {
            System.out.println("Error deleting acknowledgment: " + e.getMessage());
            // Optionally, log the stack trace or handle the exception further
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error deleting acknowledgment: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Transactional
    public void deleteemail_notification(email_notification emailNotification) {
        try {
            entityManager.remove(emailNotification);
            System.out.println("Email notification deleted successfully.");
        } catch (PersistenceException e) {
            System.out.println("Error deleting email notification: " + e.getMessage());
            // Optionally, log the stack trace or handle the exception further
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error deleting email notification: " + e.getMessage());
            e.printStackTrace();
        }
    }
    //endregion


    public static class ColumnValuePair {
        private String columnName;
        private Object value;
    
        public ColumnValuePair(String columnName, Object value) {
            this.columnName = columnName;
            this.value = value;
        }
    
        public String getColumnName() {
            return columnName;
        }
    
        public Object getValue() {
            return value;
        }
    }
}


