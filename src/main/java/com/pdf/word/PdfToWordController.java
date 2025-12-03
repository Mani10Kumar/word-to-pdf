//package com.pdf.word;
//
//import java.io.File;
//import java.nio.file.Files;
//
//import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
//import org.jodconverter.local.LocalConverter;
//import org.jodconverter.local.office.LocalOfficeManager;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@RequestMapping("/api")
//@CrossOrigin(origins = "*")
//public class PdfToWordController {
//
//    @Autowired
//    private LocalOfficeManager officeManager;
//
//    @PostMapping(
//            value = "/pdf-to-word",
//            consumes = "multipart/form-data",
//            produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
//    )
//    public ResponseEntity<byte[]> convertPdfToWord(@RequestParam("file") MultipartFile file) throws Exception {
//
//        // Temp PDF file
//        File inputFile = File.createTempFile("input", ".pdf");
//        file.transferTo(inputFile);
//
//        // Temp DOCX output file
//        File outputFile = File.createTempFile("output", ".docx");
//
//        try {
//            // Convert using LibreOffice (JODConverter)
//            LocalConverter
//                    .builder()
//                    .officeManager(officeManager)
//                    .build()
//                    .convert(inputFile)
//                    .to(outputFile)
//                    .execute();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("PDF to Word conversion failed: " + e.getMessage());
//        }
//
//        // Read bytes
//        byte[] wordBytes = Files.readAllBytes(outputFile.toPath());
//
//        // Delete temp files
//        inputFile.delete();
//        outputFile.delete();
//
//        return ResponseEntity.ok()
//                .header("Content-Disposition", "attachment; filename=converted.docx")
//                .contentType(MediaType.parseMediaType(
//                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
//                .body(wordBytes);
//    }
//
//}


package com.pdf.word;

import java.io.File;
import java.nio.file.Files;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.aspose.pdf.Document;
import com.aspose.pdf.SaveFormat;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PdfToWordController {

    @PostMapping(
        value = "/pdf-to-word",
        consumes = "multipart/form-data",
        produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    )
    public ResponseEntity<byte[]> convertPdfToWord(@RequestParam("file") MultipartFile file) throws Exception {

        // Save uploaded PDF to a temporary file
        File inputFile = File.createTempFile("input-", ".pdf");
        file.transferTo(inputFile);

        // Create temp output DOCX file
        File outputFile = File.createTempFile("output-", ".docx");

        // Convert PDF to Word using Aspose
        Document pdfDocument = new Document(inputFile.getAbsolutePath());
        pdfDocument.save(outputFile.getAbsolutePath(), SaveFormat.DocX);

        // Read output bytes
        byte[] docBytes = Files.readAllBytes(outputFile.toPath());

        // Clean up temp files
        inputFile.delete();
        outputFile.delete();

        // Return as response
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=converted.docx")
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
                .body(docBytes);
    }
}
