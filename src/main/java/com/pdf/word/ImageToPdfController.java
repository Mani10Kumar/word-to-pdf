package com.pdf.word;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ImageToPdfController {

    @PostMapping(
        value = "/image-to-pdf",
        consumes = "multipart/form-data",
        produces = "application/pdf"
    )
    public byte[] convertImagesToPdf(@RequestParam("files") List<MultipartFile> files) throws Exception {

        if (files == null || files.isEmpty()) {
            throw new RuntimeException("Please upload at least one image.");
        }

        PDDocument document = new PDDocument();

        for (MultipartFile file : files) {

            PDImageXObject image = PDImageXObject.createFromByteArray(
                    document,
                    file.getBytes(),
                    file.getOriginalFilename()
            );

            // Create new page for each image
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream content = new PDPageContentStream(document, page);

            float pageWidth = page.getMediaBox().getWidth();
            float pageHeight = page.getMediaBox().getHeight();

            float imgWidth = image.getWidth();
            float imgHeight = image.getHeight();

            // Maintain aspect ratio (fit image to page)
            float scale = Math.min(pageWidth / imgWidth, pageHeight / imgHeight);

            float finalWidth = imgWidth * scale;
            float finalHeight = imgHeight * scale;

            // Center the image
            float x = (pageWidth - finalWidth) / 2;
            float y = (pageHeight - finalHeight) / 2;

            // Draw image on page
            content.drawImage(image, x, y, finalWidth, finalHeight);
            content.close();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        document.save(out);
        document.close();

        return out.toByteArray();
    }
}
