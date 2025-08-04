package ru.demo.wms.consts;

/**
 * Перечисление возможных статусов заказа на закупку (Purchase Order).
 */
public enum PurchaseOrderStatus {

	/** Заказ создан, но ещё не обработан */
	OPEN,

	/** Идёт комплектование товаров */
	PICKING,

	/** Заказ размещён у поставщика */
	ORDERED,

	/** Выставлен счёт */
	INVOICED,

	/** Товары получены */
	RECEIVED,

	/** Заказ отменён */
	CANCELLED;
}
