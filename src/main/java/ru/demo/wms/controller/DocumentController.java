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
 * Предоставляет функции:
 * <ul>
 *   <li>Отображение страницы со списком документов</li>
 *   <li>Загрузка документов</li>
 *   <li>Скачивание документов</li>
 *   <li>Удаление документов</li>
 *   <li>Генерация PDF-отчёта по документам</li>
 * </ul>
 *
 * <h3>TODO</h3>
 * <ul>
 *   <li>Заменить e.printStackTrace() на логирование через логгер</li>
 *   <li>Проверять MIME-типы и имена файлов для безопасности</li>
 *   <li>Применить DTO вместо прямой работы с сущностями</li>
 * </ul>
 */
@Controller
@RequestMapping("/doc")
public class DocumentController {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    private IDocumentService service;

    @Autowired
    private ReportService reportService;

    // 1. Отобразить страницу со всеми документами
    @GetMapping("/all")
    public String showDocs(Model model) {
        model.addAttribute("idVal", System.currentTimeMillis());
        List<Object[]> list = service.getDocumentIdAndName();
        model.addAttribute("list", list);
        return "Documents";
    }

    // 2. Загрузить новый документ
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

    // 3. Скачать документ по ID
    @GetMapping("/download")
    public void downloadDoc(@RequestParam Long id, HttpServletResponse response) {
        try {
            // Получить объект из базы данных
            Document doc = service.getDocumentById(id);

            // Установить заголовок с именем файла
            response.setHeader("Content-Disposition", "attachment;filename=" + doc.getDocName());

            // Скопировать данные в выходной поток
            FileCopyUtils.copy(doc.getDocData(), response.getOutputStream());
        } catch (Exception e) {
            LOG.error("Ошибка при скачивании документа", e);
        }
    }

    // 4. Удалить документ по ID
    @GetMapping("/delete")
    public String deleteDoc(@RequestParam Long id) {
        try {
            service.deleteDocumentById(id);
        } catch (RuntimeException e) {
            LOG.error("Ошибка при удалении документа", e);
        }
        return "redirect:all";
    }

    // 5. Сгенерировать PDF-отчёт по документам
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
