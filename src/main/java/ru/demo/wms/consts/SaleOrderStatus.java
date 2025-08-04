package ru.demo.wms.consts;

/**
 * Перечисление возможных статусов заказа на продажу (Sale Order).
 */
public enum SaleOrderStatus {

	/** Заказ создан и ожидает обработки */
	OPEN,

	/** Заказ укомплектован и готов к подтверждению */
	READY,

	/** Заказ подтверждён */
	CONFIRM,

	/** Счёт на заказ выставлен */
	INVOICED,

	/** Заказ отгружен */
	SHIPPED,

	/** Заказ отменён */
	CANCELLED;
}
