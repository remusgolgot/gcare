package com.gcare.dao;

import com.gcare.model.Doctor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class DoctorDAO extends GenericDAO {

    @Transactional
    public Doctor getByUUID(String doctorUUID) {
        return (Doctor) em.createQuery(
                "SELECT c FROM Doctor c WHERE c.uuid = '" + doctorUUID + "'")
                .getResultList().stream().findFirst().orElse(null);
    }

    @Transactional
    public List<Doctor> list() {
       return list(Doctor.class);
    }

    @Transactional
    public Doctor getByID(int id) {
        return (Doctor) em.createQuery(
                "SELECT c FROM Doctor c WHERE c.id = " + id)
                .getResultList().stream().findFirst().orElse(null);
    }
}
