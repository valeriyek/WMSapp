package ru.demo.wms.model;
/*Класс ShipingDtl представляет собой модель для хранения деталей отгрузки в системе управления складом. Эта модель используется для учета конкретных товаров, входящих в состав отгрузки, их стоимости, количества и статуса обработки.

Ключевые характеристики:

Уникальный идентификатор детали отгрузки (id): Используется для уникальной идентификации каждой позиции в отгрузке.
Код товара (partCode): Уникальный идентификатор или код товара, входящего в отгрузку.
Базовая стоимость (baseCost): Стоимость единицы товара.
Количество (qty): Количество единиц товара, входящего в отгрузку.
Статус (status): Текущий статус обработки товара в рамках отгрузки (например, "принято", "возврат" и т.д.).
Использование в приложении:
Класс ShipingDtl используется для детализации информации по отгрузке, позволяя тем самым точно учитывать состав каждой отгрузки, ее стоимость и статус обработки каждой позиции. Это позволяет обеспечить высокую точность логистических и складских операций, а также эффективно управлять возвратами и обменами товаров.

Примеры использования:

Формирование отгрузки: При создании новой отгрузки в систему вносится информация о всех товарах, включая их код, стоимость, количество и статус. Это обеспечивает полную прозрачность состава отгрузки.
Отслеживание статуса товаров: Использование статусов в ShipingDtl позволяет отслеживать текущее состояние каждого товара в процессе его отгрузки и доставки клиенту.
Учет возвратов: В случае возврата товара клиентом статус соответствующей позиции в ShipingDtl может быть обновлен для отражения этого события, что позволяет точно учитывать возвраты и обмены в системе.
Взаимодействие с другими компонентами системы:

Связь с Shiping: ShipingDtl напрямую связан с отгрузкой (Shiping), в рамках которой эти детали были созданы, позволяя тем самым агрегировать всю информацию по отгрузке в одном месте.*/
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "shiping_dtl_tab")
public class ShipingDtl {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shiping_dtl_id")
	private Integer id;

	@Column(name = "shiping_dtl_code")
	private String partCode;

	@Column(name = "shiping_dtl_cost")
	private Double baseCost;

	@Column(name = "shiping_dtl_qty")
	private Integer qty;

	@Column(name = "shiping_dtl_status")
	private String status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPartCode() {
		return partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public Double getBaseCost() {
		return baseCost;
	}

	public void setBaseCost(Double baseCost) {
		this.baseCost = baseCost;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ShipingDtl(Integer id, String partCode, Double baseCost, Integer qty, String status) {
		super();
		this.id = id;
		this.partCode = partCode;
		this.baseCost = baseCost;
		this.qty = qty;
		this.status = status;
	}

	public ShipingDtl() {
		super();
		// TODO
	}

	
	
}
