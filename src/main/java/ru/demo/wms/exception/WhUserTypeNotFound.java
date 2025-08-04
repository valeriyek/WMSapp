package ru.demo.wms.exception;

/**
 * Исключение {@code WhUserTypeNotFound} выбрасывается, когда
 * указанный тип пользователя склада (Warehouse User Type) не найден в системе.
 * <p>
 * Это может произойти, например, при попытке получить доступ к типу по ID или коду,
 * если соответствующая запись отсутствует в базе данных.
 * </p>
 *
 * <p>Типичное применение:</p>
 * <pre>{@code
 *     WhUserType type = service.getById(id)
 *         .orElseThrow(() -> new WhUserTypeNotFound("Тип пользователя склада с ID " + id + " не найден"));
 * }</pre>
 *
 * <p>Исключение unchecked — не требует обязательной обработки.</p>
 */
public class WhUserTypeNotFound extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Конструктор без параметров. Используется, если не требуется описание ошибки.
	 */
	public WhUserTypeNotFound() {
	}

	/**
	 * Конструктор с сообщением об ошибке.
	 *
	 * @param message подробное описание причины исключения
	 */
	public WhUserTypeNotFound(String message) {
		super(message);
	}
}
