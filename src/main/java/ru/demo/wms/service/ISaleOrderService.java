package ru.demo.wms.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import ru.demo.wms.model.SaleOrder;
import ru.demo.wms.model.SaleOrderDetails;

public interface ISaleOrderService {


	public Integer saveSaleOrder(SaleOrder saleOrder);


	public List<SaleOrder> getAllSaleOrder();


	public SaleOrder getOneSaleOrder(Integer id);


	public boolean validateOrderCode(String code);

	public boolean validateOrderCodeAndId(String code, Integer id);


	public Integer savePurchaseDetails(SaleOrderDetails saleOrderDetails);


	public List<SaleOrderDetails> getSaleDtlsBySaleOrderId(Integer id);

	public void deleteSaleDetails(Integer detailId);


	public String getCurrentStatusOfSaleOrder(Integer soId);

	public void updateSaleOrderStatus(Integer soId, String newStatus);

	public Integer getSaleDtlsCountBySaleOrderId(Integer soId);


	public Optional<SaleOrderDetails> getSaleDetailByPartIdAndSaleOrderId(Integer partId, Integer soId);

	public Integer updateSaleOrderDetailQtyByDetailId(Integer newQty, Integer dtlId);


	Map<Integer, String> findSaleOrderIdAndCodeByStatus(String status);
}
/*
Интерфейс ISaleOrderService представляет собой ключевую часть системы управления заказами на продажу, предлагая ряд методов для создания, обновления, удаления и запроса информации о заказах на продажу (SaleOrder) и связанных с ними деталях заказа (SaleOrderDetails). Вот детальный обзор предоставляемых методов:

Основные методы для управления заказами на продажу:
saveSaleOrder(SaleOrder saleOrder): Сохраняет новый заказ на продажу или обновляет существующий в системе, возвращая идентификатор сохраненного заказа.

getAllSaleOrder(): Возвращает список всех заказов на продажу в системе.

getOneSaleOrder(Integer id): Возвращает информацию о конкретном заказе на продажу по его идентификатору.

validateOrderCode(String code) и validateOrderCodeAndId(String code, Integer id): Проверяют уникальность кода заказа при создании нового заказа или обновлении существующего, чтобы избежать дублирования кодов.

Управление деталями заказа на продажу:
savePurchaseDetails(SaleOrderDetails saleOrderDetails): Сохраняет детали для заказа на продажу, такие как информация о продукте, количество и стоимость.

getSaleDtlsBySaleOrderId(Integer id): Возвращает список всех деталей, связанных с конкретным заказом на продажу.

deleteSaleDetails(Integer detailId): Удаляет определенную деталь из заказа на продажу.

Управление статусом заказа:
getCurrentStatusOfSaleOrder(Integer soId): Получает текущий статус заказа на продажу.

updateSaleOrderStatus(Integer soId, String newStatus): Обновляет статус заказа на продажу, что может включать изменение на "обработан", "отгружен" и т.д.

Дополнительные функции:
getSaleDtlsCountBySaleOrderId(Integer soId): Возвращает количество деталей заказа для конкретного заказа на продажу.

getSaleDetailByPartIdAndSaleOrderId(Integer partId, Integer soId): Проверяет, была ли определенная деталь уже добавлена в заказ на продажу, и возвращает ее, если да.

updateSaleOrderDetailQtyByDetailId(Integer newQty, Integer dtlId): Обновляет количество конкретной детали в заказе на продажу.

findSaleOrderIdAndCodeByStatus(String status): Позволяет интегрировать информацию о заказах на продажу с другими системами, предоставляя карту идентификаторов и кодов заказов по определенному статусу.

Реализация ISaleOrderService играет важную роль в обеспечении функциональности системы управления заказами на продажу, позволяя эффективно управлять заказами и обеспечивать высокий уровень обслуживания клиентов.
*/