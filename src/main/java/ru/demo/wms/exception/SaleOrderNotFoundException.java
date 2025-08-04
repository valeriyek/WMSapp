package ru.demo.wms.exception;

/**
 * Исключение {@code SaleOrderNotFoundException} выбрасывается, если заказ на продажу
 * не найден в системе управления складом (WMS).
 * <p>
 * Это исключение сигнализирует о ситуации, когда пользователь запрашивает
 * заказ, который отсутствует в базе данных или еще не был создан.
 * Используется для точной и централизованной обработки подобных ошибок.
 * </p>
 *
 * <p>Пример использования:</p>
 * <pre>{@code
 *     SaleOrder order = service.findById(id)
 *         .orElseThrow(() -> new SaleOrderNotFoundException("Заказ на продажу с ID " + id + " не найден"));
 * }</pre>
 *
 */
public class SaleOrderNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Создает исключение без сообщения.
	 */
	public SaleOrderNotFoundException() {
		super();
	}

	/**
	 * Создает исключение с заданным сообщением об ошибке.
	 *
	 * @param message текст ошибки, поясняющий причину исключения
	 */
	public SaleOrderNotFoundException(String message) {
		super(message);
	}
}
