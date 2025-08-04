package ru.demo.wms.exception;

/**
 * Исключение {@code ShipingNotFoundException} выбрасывается в ситуациях,
 * когда информация об отправлении (доставке) не найдена в системе управления складом.
 * <p>
 * Это может происходить, например, при запросе отправки по ID, которая отсутствует
 * в базе данных, была удалена или еще не создана.
 * </p>
 *
 * <p>Пример использования:</p>
 * <pre>{@code
 *     Shipping shipping = service.getById(id)
 *         .orElseThrow(() -> new ShipingNotFoundException("Отправление с ID " + id + " не найдено"));
 * }</pre>
 */
public class ShipingNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Создает исключение без сообщения.
	 */
	public ShipingNotFoundException() {
		super();
	}

	/**
	 * Создает исключение с заданным сообщением об ошибке.
	 *
	 * @param message сообщение, описывающее причину исключения
	 */
	public ShipingNotFoundException(String message) {
		super(message);
	}
}
