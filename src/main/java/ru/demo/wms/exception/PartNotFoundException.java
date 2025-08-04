package ru.demo.wms.exception;

/**
 * Исключение {@code PartNotFoundException} выбрасывается, когда запрошенная деталь (часть)
 * не найдена в системе управления складом (WMS).
 * <p>
 * Используется в ситуациях, когда поиск детали по ID или другим параметрам не дал результата.
 * Это исключение позволяет централизованно и понятно обрабатывать ошибки, связанные
 * с отсутствием компонентов в базе данных.
 * </p>
 *
 * <p>Пример использования:</p>
 * <pre>{@code
 *     Part part = repository.findById(id)
 *         .orElseThrow(() -> new PartNotFoundException("Деталь с ID " + id + " не найдена"));
 * }</pre>
 *
 */
public class PartNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Создает исключение без описания.
	 */
	public PartNotFoundException() {
		super();
	}

	/**
	 * Создает исключение с заданным сообщением.
	 *
	 * @param message сообщение об ошибке, поясняющее причину исключения
	 */
	public PartNotFoundException(String message) {
		super(message);
	}
}
