package com.pdf.word;

import java.io.ByteArrayOutputStream;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
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
public class FileConversionController {

    @PostMapping("/word-to-pdf")
    public byte[] convertWordToPdf(@RequestParam("file") MultipartFile file) throws Exception {
        XWPFDocument doc = new XWPFDocument(file.getInputStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document pdfDoc = new Document(pdf);
        
        for (XWPFParagraph paragraph : doc.getParagraphs()) {
            pdfDoc.add(new Paragraph(paragraph.getText()));
        }
        
        pdfDoc.close();
        doc.close();
        return out.toByteArray();
    }
}
