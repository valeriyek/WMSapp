package ru.demo.wms.exception;

/**
 * Исключение, выбрасываемое в случае, если необходимые данные не найдены.
 * <p>
 * Это пользовательский тип исключения, расширяющий {@link RuntimeException},
 * который позволяет более точно описывать ошибки, связанные с отсутствием
 * данных в системе (например, при запросе к БД или валидации ввода).
 * </p>
 *
 * <p>Примеры использования:</p>
 * <pre>
 *     throw new DataNotFoundException("Данные о пользователе не найдены");
 * </pre>
 */
public class DataNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Конструктор без параметров.
	 * Создаёт исключение без сообщения.
	 */
	public DataNotFoundException() {
		super();
	}

	/**
	 * Конструктор с сообщением.
	 *
	 * @param message текст сообщения об ошибке
	 */
	public DataNotFoundException(String message) {
		super(message);
	}
}
