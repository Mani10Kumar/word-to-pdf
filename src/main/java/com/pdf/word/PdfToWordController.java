package com.pdf.word;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PdfToWordController {

	@PostMapping(
		    value = "/pdf-to-word",
		    consumes = "multipart/form-data",
		    produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
		)
		public byte[] convertPdfToWord(@RequestParam("file") MultipartFile file) throws Exception {

		    ByteArrayOutputStream out = new ByteArrayOutputStream();

		    // Read PDF
		    PDDocument pdfDoc = PDDocument.load(file.getInputStream());
		    PDFTextStripper stripper = new PDFTextStripper();
		    String text = stripper.getText(pdfDoc);
		    pdfDoc.close();

		    // Create Word doc
		    XWPFDocument wordDoc = new XWPFDocument();

		    // Split lines and add properly
		    for (String line : text.split("\n")) {
		        XWPFParagraph p = wordDoc.createParagraph();
		        XWPFRun run = p.createRun();
		        run.setText(line);
		    }

		    wordDoc.write(out);
		    wordDoc.close();

		    return out.toByteArray();
		}


}