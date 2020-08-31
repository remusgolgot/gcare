package com.gcare.controllers;

import com.gcare.messages.Responses;
import com.gcare.model.Document;
import com.gcare.model.Patient;
import com.gcare.model.UploadFileResponse;
import com.gcare.model.dto.DocumentDto;
import com.gcare.services.DocumentService;
import com.gcare.services.FileStorageService;
import com.gcare.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/upload")
    public UploadFileResponse uploadDocument(@RequestParam("file") MultipartFile file, @RequestParam("patientId") Integer patientId) {
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
                    .path("/documents/download/" + patientId + "/")
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
    public ResponseEntity<Resource> downloadDocument(@Valid @PathVariable(value = "patientId") Integer patientId, @PathVariable String fileName, HttpServletRequest request) {
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

    @DeleteMapping(value = "/{patientId}/{documentName}")
    public ResponseEntity deleteDocument(@PathVariable String documentName, @PathVariable Integer patientId) {

        String documentPath = DOCUMENT_FOLDER_PREFIX + "\\" + patientId + "\\" + documentName;
        Document document = documentService.getByPatientAndName(patientId, documentName);
        if (document != null) {
            try {
                System.out.println(document.toString());
                fileStorageService.deleteDocument(documentPath);
                documentService.deleteDocument(document);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when deleting document " + documentPath + " " + e.getMessage());
            }

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Document " + documentName + " does not exist for patient " + patientId);
        }

        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted " + documentPath);
    }

}
