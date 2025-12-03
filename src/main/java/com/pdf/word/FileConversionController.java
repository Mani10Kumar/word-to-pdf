package com.pdf.word;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import org.jodconverter.core.office.OfficeManager; 
import org.jodconverter.local.LocalConverter;
import org.apache.poi.xwpf.usermodel.XWPFDocument; 
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType; 
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam; 
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.itextpdf.kernel.pdf.PdfDocument; 
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class FileConversionController
{
@Autowired
private OfficeManager officeManager;
@PostMapping( value = "/word-to-pdf", consumes = "multipart/form-data", produces = "application/pdf" )
public ResponseEntity<byte[]> convertWordToPdf(@RequestParam("file") MultipartFile file) throws Exception
{
File inputFile = File.createTempFile("input", ".docx");
file.transferTo(inputFile);
File outputFile = File.createTempFile("output", ".pdf");
// Convert using LibreOffice
LocalConverter .builder() 
                    .officeManager(officeManager)
                     .build()
                     .convert(inputFile) 
                   .to(outputFile)
                   .execute();
byte[] pdfBytes = Files.readAllBytes(outputFile.toPath());
inputFile.delete();
outputFile.delete(); 
return ResponseEntity.ok()
		.header("Content-Disposition", "attachment; filename=converted.pdf") 
		.contentType(MediaType.APPLICATION_PDF) .body(pdfBytes);

} 
}