package ru.demo.wms.exception;

/**
 * Исключение {@code ShipmentTypeNotFoundException} выбрасывается, если запрошенный тип отправления
 * не найден в системе управления складом.
 * <p>
 * Это может произойти, например, при попытке получить тип отправления по ID, который отсутствует
 * в базе данных, был удалён или ещё не создан.
 * </p>
 *
 * <p>Пример использования:</p>
 * <pre>{@code
 *     ShipmentType st = shipmentTypeService.getById(id)
 *         .orElseThrow(() -> new ShipmentTypeNotFoundException("Тип отправления с ID " + id + " не найден"));
 * }</pre>
 */
public class ShipmentTypeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Конструктор по умолчанию. Создает исключение без сообщения.
	 */
	public ShipmentTypeNotFoundException() {
		super();
	}

	/**
	 * Конструктор с сообщением.
	 *
	 * @param message сообщение об ошибке, описывающее причину исключения
	 */
	public ShipmentTypeNotFoundException(String message) {
		super(message);
	}
}
