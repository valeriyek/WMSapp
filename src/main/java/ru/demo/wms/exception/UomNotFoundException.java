package ru.demo.wms.exception;

/**
 * Исключение {@code UomNotFoundException} выбрасывается, если указанная
 * единица измерения (UoM — Unit of Measurement) не найдена в системе.
 * <p>
 * Это может быть связано с отсутствием записи в базе данных, попыткой
 * получить несуществующий идентификатор, или удалением единицы ранее.
 * </p>
 *
 * <p>Пример использования:</p>
 * <pre>{@code
 *     Uom uom = uomService.getById(id)
 *         .orElseThrow(() -> new UomNotFoundException("Единица измерения с ID " + id + " не найдена"));
 * }</pre>
 *
 * <p>Исключение является unchecked и не требует обязательной обработки.</p>
 */
public class UomNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Конструктор по умолчанию. Создаёт исключение без описания.
	 */
	public UomNotFoundException() {
		super();
	}

	/**
	 * Конструктор с сообщением об ошибке.
	 *
	 * @param message описание причины исключения
	 */
	public UomNotFoundException(String message) {
		super(message);
	}
}
