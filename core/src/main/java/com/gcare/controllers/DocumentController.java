package com.gcare.controllers;

import com.gcare.messages.Responses;
import com.gcare.model.Patient;
import com.gcare.model.UploadFileResponse;
import com.gcare.model.dto.DocumentDto;
import com.gcare.services.DocumentService;
import com.gcare.services.FileStorageService;
import com.gcare.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;

import static com.gcare.utils.Constants.DOCUMENT_FOLDER_PREFIX;

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
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("patientId") Integer patientId) {
        Patient patient = patientService.getPatientByID(patientId);
        if (patient == null) {
            UploadFileResponse response = new UploadFileResponse();
            response.setError(Responses.PATIENT_NOT_FOUND_FOR_ID);
            return response;
        }
        try {
            String fileName = fileStorageService.storeFile(file, DOCUMENT_FOLDER_PREFIX + "\\" + patientId);
            documentService.addDocument(new DocumentDto(Date.from(Instant.now()), patient, fileName));
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(fileName)
                    .toUriString();

            return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
        } catch (Exception e) {
            UploadFileResponse response = new UploadFileResponse();
            response.setError(e.getMessage());
            return response;
        }
    }

    @GetMapping("/download/{patientId}/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@Valid @PathVariable(value = "patientId") Integer patientId, @PathVariable String fileName, HttpServletRequest request) {
        try {
            // Load file as Resource
            Resource resource = fileStorageService.loadFileAsResource(DOCUMENT_FOLDER_PREFIX + "\\" + patientId + "\\" + fileName);

            // Try to determine file's content type
            String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

            // Fallback to the default content type if type could not be determined
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (IOException ex) {
            return ResponseEntity.notFound().build();
        }
    }

//    @DeleteMapping(value = "/document/patient/{patientId}/{name}")
//    public ResponseEntity<Long> deleteDocument(@PathVariable String name) {
//
//        String path = documentService.get
//        boolean isRemoved = documentService.deleteDocument(path);
//
//        if (!isRemoved) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        return new ResponseEntity<>(id, HttpStatus.OK);
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
