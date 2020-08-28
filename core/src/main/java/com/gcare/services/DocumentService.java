package com.gcare.services;

import com.gcare.dao.DocumentDAO;
import com.gcare.model.*;
import com.gcare.model.dto.DocumentDto;
import com.gcare.utils.ClassUtils;
import com.gcare.utils.ConstraintViolationsErrorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    @Autowired
    private DocumentDAO documentDAO;

    public List<Document> getDocumentsForPatient(Integer patientID) {
        List<Document> list = documentDAO.get(Document.class);
        return list.stream().filter(c -> c.getPatient().getId().equals(patientID)).collect(Collectors.toList());
    }

    public List listDocuments() {
        return documentDAO.list();
    }

    public Document addDocument(DocumentDto documentDto) throws Exception {
        Document entity = (Document) ClassUtils.copyPropertiesFromDTO(Document.class, documentDto);
        try {
            Document dto = documentDAO.getByPath(documentDto.getDocumentPath());
            if (dto != null) {
                entity.setId(dto.getId());
                return documentDAO.merge(entity);
            } else {
                return documentDAO.insert(entity);
            }
        } catch (ConstraintViolationException e) {
            throw new Exception(ConstraintViolationsErrorBuilder.buildErrorMessageFromException(e));
        }
    }

//    public void deleteDocument(String documentPath) {
//        Document document = documentDAO.getByID(documentID);
//        if (document != null) {
//            documentDAO.delete(document);
//        }
//    }

    public Document getDocumentByID(Integer id) {
        return documentDAO.getByID(id);
    }

    public Document updateDocument(DocumentDto documentDto) throws Exception {
        Document entity = (Document) ClassUtils.copyPropertiesFromDTO(Document.class, documentDto);
        try {
            return documentDAO.update(entity);
        } catch (ConstraintViolationException e) {
            throw new Exception(ConstraintViolationsErrorBuilder.buildErrorMessageFromException(e));
        } catch (Exception e) {
            throw e;
        }
    }
}
