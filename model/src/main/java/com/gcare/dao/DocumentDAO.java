package com.gcare.dao;

import com.gcare.model.Doctor;
import com.gcare.model.Document;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class DocumentDAO extends GenericDAO {

    @Transactional
    public List<Document> list() {
       return list(Document.class);
    }

    @Transactional
    public Document getByID(int id) {
        return (Document) em.createQuery(
                "SELECT c FROM Document c WHERE c.id = " + id)
                .getResultList().stream().findFirst().orElse(null);
    }

    @Transactional
    public Document getByPatientAndName(Integer patientId, String documentName) {
        return (Document) em.createQuery(
                "SELECT c FROM Document c WHERE c.documentName = '" + documentName + "'" + " AND c.patient = " + patientId)
                .getResultList().stream().findFirst().orElse(null);
    }
}
