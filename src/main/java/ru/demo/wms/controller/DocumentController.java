package ru.demo.wms.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ru.demo.wms.model.Document;
import ru.demo.wms.service.IDocumentService;
import ru.demo.wms.service.impl.ReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

/**
 * Контроллер для управления документами.
 * <p>
 * Реализует загрузку, скачивание, удаление документов и генерацию PDF-отчёта.
 */
@Controller
@RequestMapping("/doc")
public class DocumentController {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    private IDocumentService service;

    @Autowired
    private ReportService reportService;

    /**
     * Отображает страницу со списком всех документов.
     *
     * @param model модель для передачи атрибутов в представление
     * @return имя шаблона отображения
     */
    @GetMapping("/all")
    public String showDocs(Model model) {
        model.addAttribute("idVal", System.currentTimeMillis());
        List<Object[]> list = service.getDocumentIdAndName();
        model.addAttribute("list", list);
        return "Documents";
    }

    /**
     * Загружает документ в базу данных.
     *
     * @param docId идентификатор документа
     * @param docOb объект загружаемого файла
     * @return редирект на страницу со списком документов
     */
    @PostMapping("/upload")
    public String uploadDoc(@RequestParam Long docId, @RequestParam MultipartFile docOb) {
        try {
            Document doc = new Document();
            doc.setDocId(docId);
            doc.setDocName(docOb.getOriginalFilename());
            doc.setDocData(docOb.getBytes());
            service.saveDocument(doc);
        } catch (Exception e) {
            LOG.error("Ошибка при загрузке документа", e);
        }
        return "redirect:all";
    }

    /**
     * Скачивает документ по его ID.
     *
     * @param id       идентификатор документа
     * @param response HTTP-ответ с бинарными данными
     */
    @GetMapping("/download")
    public void downloadDoc(@RequestParam Long id, HttpServletResponse response) {
        try {
            Document doc = service.getDocumentById(id);
            response.setHeader("Content-Disposition", "attachment;filename=" + doc.getDocName());
            FileCopyUtils.copy(doc.getDocData(), response.getOutputStream());
        } catch (Exception e) {
            LOG.error("Ошибка при скачивании документа", e);
        }
    }

    /**
     * Удаляет документ по его ID.
     *
     * @param id идентификатор документа
     * @return редирект на страницу со списком документов
     */
    @GetMapping("/delete")
    public String deleteDoc(@RequestParam Long id) {
        try {
            service.deleteDocumentById(id);
        } catch (RuntimeException e) {
            LOG.error("Ошибка при удалении документа", e);
        }
        return "redirect:all";
    }

    /**
     * Генерирует PDF-отчёт по документам.
     *
     * @return ответ с готовым PDF-файлом
     */
    @GetMapping("/generateReport")
    public ResponseEntity<byte[]> generateDocumentsReport() {
        byte[] data = reportService.createDocumentsReport();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=documents-report.pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(data);
    }
}
