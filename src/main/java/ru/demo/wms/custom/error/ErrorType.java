package ru.demo.wms.custom.error;
/*
В этом классе ErrorType используется Lombok для упрощения создания геттеров, сеттеров, 
конструктора без параметров (@NoArgsConstructor), и конструктора со всеми параметрами (@AllArgsConstructor). 
Однако, кажется, есть некоторая путаница из-за дополнительного конструктора, 
который вы добавили вручную и который не делает ничего в текущей реализации.

Давайте рассмотрим, как работает каждая аннотация Lombok и что необходимо изменить в вашем классе:

@Data: Генерирует геттеры и сеттеры для всех полей, а также методы equals(), 
hashCode() и toString().

@NoArgsConstructor: Создает конструктор без аргументов. Полезно, когда требуется 
создавать экземпляры класса без предоставления начальных значений.

@AllArgsConstructor: Генерирует конструктор, который принимает один аргумент 
для каждого поля класса. Это позволяет создавать экземпляры класса, сразу устанавливая все значения.

Однако, в вашем коде есть попытка создать еще один конструктор вручную:

java
Copy code
public ErrorType(String string, String string2, String message2) {
    // TODO Auto-generated constructor stub
}
Этот конструктор не делает ничего, поскольку в теле конструктора нет кода. 
Кроме того, названия параметров (string, string2, message2) не помогают понять, 
какие данные они должны представлять. Если ваша цель — создать конструктор, 
который принимает конкретные значения для полей dateTime, module, message, 
то вам следует использовать @AllArgsConstructor от Lombok, который уже делает это.
Если же вам нужен конструктор с частичным набором полей, вам придется создать 
его вручную и корректно инициализировать поля:

java
Copy code
public ErrorType(String dateTime, String module, String message) {
    this.dateTime = dateTime;
    this.module = module;
    this.message = message;
}
Убедитесь, что имена параметров соответствуют вашим намерениям и корректно инициализируют 
поля класса. Если же этот конструктор не нужен и вы хотите использовать только возможности, 
предоставляемые Lombok, его следует удалить, поскольку @AllArgsConstructor 
уже покрывает необходимость в конструкторе со всеми аргументами.

*/
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//@AllArgsConstructor
public class ErrorType {

	public ErrorType(String string, String string2, String message2) {
		// TODO
	}
	private String dateTime;
	private String module;
	private String message;
	
}
