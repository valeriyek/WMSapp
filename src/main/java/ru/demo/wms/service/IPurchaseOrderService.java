package ru.demo.wms.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import ru.demo.wms.model.PurchaseDtl;
import ru.demo.wms.model.PurchaseOrder;

public interface IPurchaseOrderService {

	Integer savePurchaseOrder(PurchaseOrder po);
	
	PurchaseOrder getOnePurchaseOrder(Integer id);
	List<PurchaseOrder> getAllPurchaseOrders();
	boolean isPurchaseOrderCodeExist(String code);
	boolean isPurchaseOrderCodeExistForEdit(String code,Integer id);

	Integer savePurchaseDtl(PurchaseDtl pdtl);
	List<PurchaseDtl> getPurchaseDtlsByPoId(Integer id);	
	void deletePurchaseDtl(Integer dtlId);
	

	String getCurrentStatusOfPo(Integer poId);
	void updatePoStatus(Integer poId,String newStatus);
	
	Integer getPurchaseDtlsCountByPoId(Integer poId);

	Optional<PurchaseDtl> getPurchaseDtlByPartIdAndPoId(Integer partId, Integer poId);
	Integer updatePurchaseDtlQtyByDtlId(Integer newQty,Integer dtlId);

	Map<Integer,String> getPoIdAndCodesByStatus(String status);
}
/*
Интерфейс IPurchaseOrderService определяет сервисный слой для управления заказами на покупку (PurchaseOrder) и связанными с ними деталями (PurchaseDtl) в системе управления складом или логистикой. Вот обзор ключевых функций, которые предоставляет этот интерфейс:

Основные функции:
Сохранение заказа на покупку:

Integer savePurchaseOrder(PurchaseOrder po);
Сохраняет новый заказ на покупку в системе и возвращает идентификатор сохраненного заказа.
Получение информации о заказе на покупку:

PurchaseOrder getOnePurchaseOrder(Integer id);
Возвращает информацию о конкретном заказе на покупку по его идентификатору.

List<PurchaseOrder> getAllPurchaseOrders();
Возвращает список всех заказов на покупку в системе.

Проверка существования кода заказа:

boolean isPurchaseOrderCodeExist(String code);
Проверяет, существует ли уже заказ на покупку с указанным кодом.

boolean isPurchaseOrderCodeExistForEdit(String code, Integer id);
Проверяет существование кода заказа на покупку за исключением указанного идентификатора заказа, что полезно при редактировании заказа.

Работа с деталями заказа на покупку (screen#2):
Сохранение деталей заказа:

Integer savePurchaseDtl(PurchaseDtl pdtl);
Сохраняет детали заказа на покупку.
Получение и удаление деталей заказа:

List<PurchaseDtl> getPurchaseDtlsByPoId(Integer id);
Возвращает список всех деталей для указанного заказа на покупку.

void deletePurchaseDtl(Integer dtlId);
Удаляет конкретную деталь заказа на покупку.

Управление статусом заказа:
Чтение и обновление статуса заказа:

String getCurrentStatusOfPo(Integer poId);
Возвращает текущий статус указанного заказа на покупку.

void updatePoStatus(Integer poId, String newStatus);
Обновляет статус указанного заказа на покупку.

Дополнительные функции:

Integer getPurchaseDtlsCountByPoId(Integer poId);
Возвращает количество деталей для указанного заказа на покупку.

Optional<PurchaseDtl> getPurchaseDtlByPartIdAndPoId(Integer partId, Integer poId);
Проверяет, добавлена ли уже деталь для указанного заказа на покупку и возвращает её, если да.

Integer updatePurchaseDtlQtyByDtlId(Integer newQty, Integer dtlId);
Обновляет количество определенной детали в заказе на покупку.

Интеграция с GRN:

Map<Integer, String> getPoIdAndCodesByStatus(String status);
Возвращает карту идентификаторов и кодов заказов на покупку по заданному статусу, что может быть использовано для интеграции с другими модулями, такими как модуль приемки товаров (Goods Received Note - GRN).
Этот интерфейс важен для обеспечения гибкости и масштабируемости системы управления складом или логистикой, позволяя адаптировать процессы заказа товаров под различные бизнес-требования.
*/