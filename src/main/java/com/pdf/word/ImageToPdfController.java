package com.pdf.word;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ImageToPdfController {

	@PostMapping("/imagetopdf")
    public byte[] convertImageToPdf(@RequestParam("file") MultipartFile imageFile) throws IOException {

        // Create PDF document
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        // Load image into PDF
        PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, imageFile.getBytes(), null);

        // Add image to PDF page
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Fit the image to full page size
        float pageWidth = page.getMediaBox().getWidth();
        float pageHeight = page.getMediaBox().getHeight();

        contentStream.drawImage(pdImage, 0, 0, pageWidth, pageHeight);
        contentStream.close();

        // Output stream
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        document.save(out);

        // Cleanup
        document.close();
        out.close();

        return out.toByteArray();
    }
}
//localhost:8080/api/imagetopdf
//




