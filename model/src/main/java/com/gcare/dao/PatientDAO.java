package com.gcare.dao;

import com.gcare.model.Patient;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class PatientDAO extends GenericDAO {

    @Transactional
    public Patient getByUUID(String patientUUID) {
        return (Patient) em.createQuery(
                "SELECT c FROM Patient c WHERE c.uuid = '" + patientUUID + "'")
                .getResultList().stream().findFirst().orElse(null);
    }

    @Transactional
    public List<Patient> list() {
       return list(Patient.class);
    }

    @Transactional
    public Patient getByID(int id) {
        return (Patient) em.createQuery(
                "SELECT c FROM Patient c where id = " + id)
                .getResultList().stream().findFirst().orElse(null);
    }
}
