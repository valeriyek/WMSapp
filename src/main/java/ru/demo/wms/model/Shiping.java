package ru.demo.wms.model;
/*
Класс Shiping представляет собой модель для управления информацией о доставке в системе управления складом. Этот класс содержит детали отгрузки, такие как код отгрузки, номер справки отгрузки, номер справки курьера, контактные данные для связи, описание отгрузки, а также связь с заказом на продажу (SaleOrder) и детализацию отгрузки (Set<ShipingDtl>).

Ключевые характеристики:

Уникальный идентификатор отгрузки (id): Используется для уникальной идентификации каждой отгрузки в базе данных.
Код отгрузки (shipingCode): Уникальный код, идентифицирующий отгрузку.
Номер справки отгрузки (shipngRefNum): Номер, используемый для отслеживания и управления отгрузкой.
Номер справки курьера (courierRefNum): Номер, предоставленный курьерской службой для отслеживания доставки.
Контактные данные (contactDetails): Информация для связи с ответственным за отгрузку лицом или организацией.
Описание (description): Дополнительная информация об отгрузке.
Связь с заказом на продажу (SaleOrder): Указывает на заказ на продажу, к которому относится данная отгрузка.
Детализация отгрузки (Set<ShipingDtl>): Список деталей отгрузки, включая информацию о товарах, входящих в отгрузку.
Использование в приложении:
Класс Shiping используется для управления процессами доставки товаров от склада к клиенту. Он позволяет отслеживать каждую отгрузку, её состав, статус доставки и другие ключевые параметры, обеспечивая тем самым эффективное управление логистическими процессами внутри компании.

Взаимодействие с другими компонентами системы:

Связь с SaleOrder: Позволяет установить прямую связь между отгрузкой и соответствующим заказом на продажу, упрощая учет и анализ продаж и доставок.
Связь с ShipingDtl: Детализация отгрузки, включая перечень товаров, их количество и другие характеристики, что позволяет детально управлять комплектацией каждой отгрузки.
Примеры использования:

Создание новой отгрузки: При формировании заказа на продажу система может автоматически создавать запись об отгрузке, привязывая её к соответствующему заказу и заполняя необходимые детали.
Отслеживание статуса доставки: Изменяя статусы отгрузок в системе, можно эффективно управлять процессами доставки, отслеживая каждый этап от комплектации до вручения клиенту.
Анализ эффективности доставки: Собирая данные об отгрузках, их времени выполнения и статусах доставки, можно анализировать эффективность логистических процессов и оптимизировать их для повышения уровня удовлетворенности клиентов.
*/
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Set;
import java.util.stream.IntStream;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "shiping_tab")
public class Shiping {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shiping_id")
	private Integer id;

	@Column(name = "shiping_code")
	private String shipingCode;

	@Column(name = "shiping_ref_num")
	private String shipngRefNum;

	@Column(name = "courier_ref_num")
	private String courierRefNum;

	@Column(name = "contact_details")
	private String contactDetails;

	@Column(name = "shiping_desc")
	private String description;


	@ManyToOne
	@JoinColumn(name = "so_id_fk", unique = true)
	private SaleOrder so;


	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "shiping_id_fk")
	private Set<ShipingDtl> dtls;

	public int length() {
		return contactDetails.length();
	}

	public boolean isEmpty() {
		return contactDetails.isEmpty();
	}

	public char charAt(int index) {
		return contactDetails.charAt(index);
	}

	public int codePointAt(int index) {
		return contactDetails.codePointAt(index);
	}

	public int codePointBefore(int index) {
		return contactDetails.codePointBefore(index);
	}

	public int codePointCount(int beginIndex, int endIndex) {
		return contactDetails.codePointCount(beginIndex, endIndex);
	}

	public int offsetByCodePoints(int index, int codePointOffset) {
		return contactDetails.offsetByCodePoints(index, codePointOffset);
	}

	public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
		contactDetails.getChars(srcBegin, srcEnd, dst, dstBegin);
	}

	public void getBytes(int srcBegin, int srcEnd, byte[] dst, int dstBegin) {
		contactDetails.getBytes(srcBegin, srcEnd, dst, dstBegin);
	}

	public byte[] getBytes(String charsetName) throws UnsupportedEncodingException {
		return contactDetails.getBytes(charsetName);
	}

	public byte[] getBytes(Charset charset) {
		return contactDetails.getBytes(charset);
	}

	public byte[] getBytes() {
		return contactDetails.getBytes();
	}

	public boolean equals(Object anObject) {
		return contactDetails.equals(anObject);
	}

	public boolean contentEquals(StringBuffer sb) {
		return contactDetails.contentEquals(sb);
	}

	public boolean contentEquals(CharSequence cs) {
		return contactDetails.contentEquals(cs);
	}

	public boolean equalsIgnoreCase(String anotherString) {
		return contactDetails.equalsIgnoreCase(anotherString);
	}

	public int compareTo(String anotherString) {
		return contactDetails.compareTo(anotherString);
	}

	public int compareToIgnoreCase(String str) {
		return contactDetails.compareToIgnoreCase(str);
	}

	public boolean regionMatches(int toffset, String other, int ooffset, int len) {
		return contactDetails.regionMatches(toffset, other, ooffset, len);
	}

	public boolean regionMatches(boolean ignoreCase, int toffset, String other, int ooffset, int len) {
		return contactDetails.regionMatches(ignoreCase, toffset, other, ooffset, len);
	}

	public boolean startsWith(String prefix, int toffset) {
		return contactDetails.startsWith(prefix, toffset);
	}

	public boolean startsWith(String prefix) {
		return contactDetails.startsWith(prefix);
	}

	public boolean endsWith(String suffix) {
		return contactDetails.endsWith(suffix);
	}

	public int hashCode() {
		return contactDetails.hashCode();
	}

	public int indexOf(int ch) {
		return contactDetails.indexOf(ch);
	}

	public int indexOf(int ch, int fromIndex) {
		return contactDetails.indexOf(ch, fromIndex);
	}

	public int lastIndexOf(int ch) {
		return contactDetails.lastIndexOf(ch);
	}

	public int lastIndexOf(int ch, int fromIndex) {
		return contactDetails.lastIndexOf(ch, fromIndex);
	}

	public int indexOf(String str) {
		return contactDetails.indexOf(str);
	}

	public int indexOf(String str, int fromIndex) {
		return contactDetails.indexOf(str, fromIndex);
	}

	public int lastIndexOf(String str) {
		return contactDetails.lastIndexOf(str);
	}

	public int lastIndexOf(String str, int fromIndex) {
		return contactDetails.lastIndexOf(str, fromIndex);
	}

	public String substring(int beginIndex) {
		return contactDetails.substring(beginIndex);
	}

	public String substring(int beginIndex, int endIndex) {
		return contactDetails.substring(beginIndex, endIndex);
	}

	public CharSequence subSequence(int beginIndex, int endIndex) {
		return contactDetails.subSequence(beginIndex, endIndex);
	}

	public String concat(String str) {
		return contactDetails.concat(str);
	}

	public String replace(char oldChar, char newChar) {
		return contactDetails.replace(oldChar, newChar);
	}

	public boolean matches(String regex) {
		return contactDetails.matches(regex);
	}

	public boolean contains(CharSequence s) {
		return contactDetails.contains(s);
	}

	public String replaceFirst(String regex, String replacement) {
		return contactDetails.replaceFirst(regex, replacement);
	}

	public String replaceAll(String regex, String replacement) {
		return contactDetails.replaceAll(regex, replacement);
	}

	public String replace(CharSequence target, CharSequence replacement) {
		return contactDetails.replace(target, replacement);
	}

	public String[] split(String regex, int limit) {
		return contactDetails.split(regex, limit);
	}

	public String[] split(String regex) {
		return contactDetails.split(regex);
	}

	public String toLowerCase(Locale locale) {
		return contactDetails.toLowerCase(locale);
	}

	public String toLowerCase() {
		return contactDetails.toLowerCase();
	}

	public String toUpperCase(Locale locale) {
		return contactDetails.toUpperCase(locale);
	}

	public String toUpperCase() {
		return contactDetails.toUpperCase();
	}

	public String trim() {
		return contactDetails.trim();
	}

	public String toString() {
		return contactDetails.toString();
	}

	public IntStream chars() {
		return contactDetails.chars();
	}

	public IntStream codePoints() {
		return contactDetails.codePoints();
	}

	public char[] toCharArray() {
		return contactDetails.toCharArray();
	}


	public String intern() {
		return contactDetails.intern();
	}



	public Shiping(Integer id, String shipingCode, String shipngRefNum, String courierRefNum, String contactDetails,
			String description, SaleOrder so, Set<ShipingDtl> dtls) {
		super();
		this.id = id;
		this.shipingCode = shipingCode;
		this.shipngRefNum = shipngRefNum;
		this.courierRefNum = courierRefNum;
		this.contactDetails = contactDetails;
		this.description = description;
		this.so = so;
		this.dtls = dtls;
	}

	public Shiping() {
		super();
		// TODO
	}

	public SaleOrderDetails getSo() {
		// TODO
		return null;
	}

	public void setDtls(Set<ShipingDtl> shipingSet) {
		// TODO
		
	}

	public Object getDtls() {
		// TODO
		return null;
	}

	public Integer getId() {
		// TODO
		return null;
	}
	
	

}
