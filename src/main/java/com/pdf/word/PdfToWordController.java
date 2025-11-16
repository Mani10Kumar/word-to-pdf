package com.pdf.word;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class PdfToWordController {

    @PostMapping("/pdftoword")
    public byte[] convertPdfToWord(@RequestParam("file") MultipartFile pdfFile) throws IOException {
        // Load PDF document
        PDDocument document = PDDocument.load(pdfFile.getInputStream());

        // Extract text from PDF
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);

        // Create Word document
        XWPFDocument wordDocument = new XWPFDocument();
        XWPFParagraph paragraph = wordDocument.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(text);

        // Write Word document to output stream
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        wordDocument.write(out);

        // Clean up
        document.close();
        wordDocument.close();
        out.close();

        return out.toByteArray();
    }
}
//localhost:8085/api/convert