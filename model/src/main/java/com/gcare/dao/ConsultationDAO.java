package com.gcare.dao;

import com.gcare.model.Consultation;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ConsultationDAO extends GenericDAO {

    @Transactional
    public List<Consultation> list() {
        return list(Consultation.class);
    }

    @Transactional
    public Consultation getByID(int id) {
        return (Consultation) em.createQuery(
                "SELECT c FROM Consultation c WHERE c.id = " + id)
                .getResultList().stream().findFirst().orElse(null);
    }
}
