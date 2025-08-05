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

/**
 * Сервис генерации PDF-отчета по документам.
 * Использует библиотеку iText для формирования PDF-файла на лету.
 */
@Service
public class ReportService {

    @Autowired
    private IDocumentService documentService;

    /**
     * Создает отчет в формате PDF, содержащий список документов.
     * Каждый документ включает ID и имя, представленные в виде абзацев.
     *
     * @return массив байт, представляющий сгенерированный PDF-файл
     */
    public byte[] createDocumentsReport() {
        Document pdfDocument = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(pdfDocument, byteArrayOutputStream);
            pdfDocument.open();

            // Получаем список документов (ID и имя)
            List<Object[]> documentData = documentService.getDocumentIdAndName();

            for (Object[] documentRow : documentData) {
                Long docId = (Long) documentRow[0];
                String docName = (String) documentRow[1];

                // Добавляем каждый документ в PDF в виде строки
                pdfDocument.add(new Paragraph("Document ID: " + docId + ", Document Name: " + docName));
            }

            pdfDocument.close();

        } catch (DocumentException e) {
            // Обработка ошибок при генерации PDF
            e.printStackTrace(); // Можно заменить на логгер
        }

        // Возвращаем PDF как массив байт
        return byteArrayOutputStream.toByteArray();
    }
}
