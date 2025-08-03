package ru.demo.wms.service.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import ru.demo.wms.service.IDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private IDocumentService documentService;

    public byte[] createDocumentsReport() {
        Document pdfDocument = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(pdfDocument, byteArrayOutputStream);
            pdfDocument.open();
            List<Object[]> documentData = documentService.getDocumentIdAndName();
            for (Object[] documentRow : documentData) {
                Long docId = (Long) documentRow[0];
                String docName = (String) documentRow[1];
                pdfDocument.add(new Paragraph("Document ID: " + docId + ", Document Name: " + docName));
            }
            pdfDocument.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }
}