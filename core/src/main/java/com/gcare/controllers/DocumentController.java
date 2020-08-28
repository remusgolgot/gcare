package com.gcare.controllers;

import com.gcare.messages.Responses;
import com.gcare.model.*;
import com.gcare.services.*;
import com.gcare.utils.ClassUtils;
import com.gcare.utils.GsonUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private FileStorageService fileStorageService;
//
//    @GetMapping(value = "/patient/{patientId}", produces = "application/json")
//    public ResponseEntity getDocumentsForPatient(@Valid @PathVariable(value = "patientId") Integer patientId) {
//        Patient patient = patientService.getPatientByID(patientId);
//        JsonObject jsonResponse = new JsonObject();
//        if (patient == null) {
//            jsonResponse.addProperty("error", Responses.PATIENT_NOT_FOUND_FOR_ID);
//        } else {
//            List<Document> patientDocuments = documentService.getDocumentsForPatient(patientId);
//            jsonResponse.add("patientDocuments", new JsonParser().parse(GsonUtils.gson.toJson(patientDocuments)).getAsJsonArray());
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse.toString());
//    }
//
//    @GetMapping(value = "/{documentId}", produces = "application/json")
//    public ResponseEntity getDocumentByID(@Valid @PathVariable(value = "documentId") Integer documentId) {
//        Document document = documentService.getDocumentByID(documentId);
//        JsonObject jsonResponse = new JsonObject();
//        if (document == null) {
//            jsonResponse.addProperty("error", Responses.DOCUMENT_NOT_FOUND_FOR_ID);
//        } else {
//            jsonResponse.add("document", new JsonParser().parse(GsonUtils.gson.toJson(document)).getAsJsonArray());
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse.toString());
//    }

    @PostMapping("/upload")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

//    @GetMapping("/downloadFile/{fileName:.+}")
//    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
//        // Load file as Resource
//        Resource resource = fileStorageService.loadFileAsResource(fileName);
//
//        // Try to determine file's content type
//        String contentType = null;
//        try {
//            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//        } catch (IOException ex) {
//            logger.info("Could not determine file type.");
//        }
//
//        // Fallback to the default content type if type could not be determined
//        if(contentType == null) {
//            contentType = "application/octet-stream";
//        }
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(contentType))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                .body(resource);
//    }

//    @DeleteMapping(value = "/{consultationID}")
//    public ResponseEntity deleteDocument(@PathVariable(value = "consultationID") Integer consultationID) {
//        String errorString = null;
//        JsonObject jsonResponse = new JsonObject();
//        try {
//            Consultation consultation = consultationService.getConsultationByID(consultationID);
//            if (consultation == null) {
//                errorString = Responses.FAILED_TO_DELETE_CONSULTATION + " : " + Responses.CONSULTATION_NOT_FOUND;
//            } else {
//                consultationService.deleteConsultationByID(consultationID);
//                jsonResponse.addProperty("response", Responses.SUCCESSFULLY_DELETED_CONSULTATION);
//            }
//        } catch (Exception e) {
//            errorString = Responses.FAILED_TO_DELETE_CONSULTATION + " : " + e.getMessage();
//        } finally {
//            if (errorString != null) {
//                jsonResponse.addProperty("error", errorString);
//            }
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse.toString());
//    }


}
