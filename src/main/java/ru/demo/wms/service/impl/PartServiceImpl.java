package ru.demo.wms.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.demo.wms.exception.PartNotFoundException;
import ru.demo.wms.model.Part;
import ru.demo.wms.repo.PartRepository;
import ru.demo.wms.service.IPartService;
import ru.demo.wms.util.MyAppUtil;

@Service
public class PartServiceImpl implements IPartService {
	@Autowired
	private MyAppUtil myAppUtil;
	@Autowired
	private PartRepository repo;

	public Integer savePart(Part part) {
		return repo.save(part).getId();
	}

	public void updatePart(Part part) {
		repo.save(part);
	}

	public void deletePart(Integer id) {
		repo.delete(getOnePart(id));
	}

	
	public Part getOnePart(Integer id) {
		return repo.findById(id).orElseThrow(
				()->new PartNotFoundException("not exist")
				);
	}

	
	public List<Part> getAllParts() {
		return repo.findAll();
	}
	
	public Map<Integer, String> getPartIdAndCode() {
		List<Object[]> list = repo.getPartIdAndCode();
		return myAppUtil.convertListToMap(list);
	}

}
/*
Класс PartServiceImpl реализует интерфейс IPartService, обеспечивая функциональность для управления деталями запасных частей (Part) в приложении. Этот сервис взаимодействует с репозиторием PartRepository для выполнения операций CRUD и предоставляет дополнительную бизнес-логику. Давайте рассмотрим основные моменты этой реализации и возможности для улучшения:

Внедрение зависимостей
PartRepository: Внедрение зависимости через аннотацию @Autowired позволяет сервису взаимодействовать с базой данных для выполнения операций над объектами Part.
Методы сервиса
CRUD операции: Реализованы стандартные операции создания (savePart), обновления (updatePart), удаления (deletePart), и получения (getOnePart, getAllParts) деталей запасных частей.

Обработка исключений: Метод getOnePart использует подход "ориентированный на отсутствие", выбрасывая исключение PartNotFoundException, если часть не найдена. Это позволяет клиенту сервиса ясно понять, что запрашиваемый ресурс отсутствует.

Преобразование данных для UI: Метод getPartIdAndCode извлекает список идентификаторов и кодов всех запасных частей и преобразует его в Map<Integer, String>, что удобно для использования, например, в выпадающих списках на пользовательском интерфейсе.

Рекомендации по улучшению
Логирование: Добавление логирования выполнения операций может помочь в отладке и мониторинге сервиса, особенно в части записи неуспешных операций и исключений.

Валидация входных данных: Прежде чем выполнять операции над данными, рекомендуется добавить валидацию входных параметров для улучшения надежности и предотвращения сохранения некорректных данных.

Транзакционность: Убедитесь, что методы, изменяющие данные (особенно если это касается нескольких операций, должны быть выполнены атомарно), аннотированы @Transactional. Это обеспечит корректное управление транзакциями и целостность данных.

Использование DTO и маппинг: Вместо прямой работы с сущностями базы данных может быть полезно использовать DTO (Data Transfer Objects) для обработки данных на уровне сервиса. Это улучшит безопасность и гибкость архитектуры, а также упростит внесение изменений в модель данных.

Обработка исключений на уровне контроллера: Перенос обработки исключений ближе к клиенту (например, с использованием @ControllerAdvice в Spring) может улучшить отзывчивость API и предоставление пользовательских сообщений об ошибках.

Класс PartServiceImpl является ключевым элементом бизнес-логики приложения, обеспечивая управление запасными частями. Применение вышеупомянутых улучшений может сделать сервис более надежным, безопасным и удобным для дальнейшего расширения.
*/