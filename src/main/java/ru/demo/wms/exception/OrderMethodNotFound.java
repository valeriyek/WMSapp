package ru.demo.wms.exception;

/**
 * Исключение {@code OrderMethodNotFound} выбрасывается, когда указанный метод заказа
 * не найден в базе данных или не может быть обработан системой.
 * <p>
 * Это исключение используется в бизнес-логике приложения WMS для точной обработки
 * ситуаций, связанных с отсутствием метода заказа. Наследуется от {@link RuntimeException}.
 * </p>
 *
 * <p>Примеры использования:</p>
 * <pre>{@code
 *     OrderMethod om = repository.findById(id)
 *         .orElseThrow(() -> new OrderMethodNotFound("Метод заказа с ID " + id + " не найден"));
 * }</pre>
 *
 */
public class OrderMethodNotFound extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Создает исключение без описания.
	 */
	public OrderMethodNotFound() {
		super();
	}

	/**
	 * Создает исключение с заданным сообщением.
	 *
	 * @param message описание ошибки, которое будет отображено или залогировано
	 */
	public OrderMethodNotFound(String message) {
		super(message);
	}
}
