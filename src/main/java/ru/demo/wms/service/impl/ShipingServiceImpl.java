package ru.demo.wms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.demo.wms.exception.ShipingNotFoundException;
import ru.demo.wms.model.Shiping;
import ru.demo.wms.repo.ShipingDetailRepository;
import ru.demo.wms.repo.ShipingRepository;
import ru.demo.wms.service.IShipingService;

@Service
public class ShipingServiceImpl implements IShipingService {

	@Autowired
	private ShipingRepository repository;

	@Autowired
	private ShipingDetailRepository dtlRepository;

	@Override
	public Integer saveShiping(Shiping shiping) {
		return repository.save(shiping).getId();
	}

	@Override
	public List<Shiping> getAllShiping() {
		return repository.findAll();
	}

	@Override
	public Shiping getOneShiping(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ShipingNotFoundException("Shiping Not Exit:"));
	}

	@Override
	@Transactional
	public void updateShipingDtlStatus(Integer id, String status) {
		dtlRepository.updateShipingDtlStatus(id, status);
	}

}
/*
Класс ShipingServiceImpl представляет собой реализацию интерфейса IShipingService, предназначенного для управления процессами отправки (Shiping) в приложении. Давайте рассмотрим основные моменты реализации и возможные направления для дальнейшего улучшения.

Основные функции:
Сохранение отправки (saveShiping): Метод сохраняет информацию об отправке в базе данных и возвращает её идентификатор. Использует репозиторий ShipingRepository для выполнения операции.
Получение всех отправок (getAllShiping): Метод возвращает список всех отправок из базы данных, обеспечивая доступ к полному перечню записей отправок.
Получение отправки по ID (getOneShiping): Возвращает информацию об одной отправке, идентифицируемой по её ID. Если отправка с таким ID не найдена, генерируется исключение ShipingNotFoundException.
Обновление статуса деталей отправки (updateShipingDtlStatus): Метод обновляет статус деталей отправки, что важно для отслеживания состояния отправленных грузов. Операция выполняется в контексте транзакции, гарантируя атомарность изменений.
Возможные улучшения:
Тщательная обработка исключений: Вместо генерации RuntimeException лучше использовать более специализированные исключения для каждого типа ошибок. Это улучшит управляемость ошибками и их обработку на стороне клиента.
Логирование: Добавление логирования на ключевых этапах выполнения операций поможет в диагностике проблем и мониторинге поведения приложения. Рекомендуется логировать как успешные операции, так и исключения.
Валидация данных: Перед сохранением или обновлением сущностей в базе данных стоит реализовать логику валидации входных данных для предотвращения сохранения некорректных данных.
Использование DTO (Data Transfer Objects): Для разделения сущностей базы данных от транспортных моделей, используемых в API, можно воспользоваться DTO. Это поможет избежать прямой зависимости API от структуры базы данных и упростит возможные будущие изменения.
Декомпозиция и модульность: Если логика работы с деталями отправки (ShipingDtl) и отправками (Shiping) сильно отличается, можно рассмотреть возможность разделения функционала на отдельные сервисы для каждой сущности. Это упростит управление кодом и его тестирование.
Класс ShipingServiceImpl является важным компонентом системы управления логистикой, предоставляя необходимые операции для управления отправками. Применение предложенных улучшений может сделать сервис более надежным, удобным для поддержки и расширения.
*/