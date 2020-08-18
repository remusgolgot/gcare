package com.gcare.dao;

import com.gcare.model.Doctor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class DoctorDAO extends GenericDAO {

    @Transactional
    public List<Doctor> getByUUID(String doctorUUID) {
        return em.createQuery(
                "SELECT c FROM Doctor c WHERE c.uuid = '" + doctorUUID + "'")
                .getResultList();
    }

    @Transactional
    public List<Doctor> list() {
       return list(Doctor.class);
    }
}
