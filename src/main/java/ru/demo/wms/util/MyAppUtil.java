package ru.demo.wms.util;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

/**
 * Утилитный компонент приложения, содержащий часто используемые методы:
 * - преобразование списков в Map
 * - генерация случайного пароля
 * - генерация одноразового кода (OTP)
 */
@Component
public class MyAppUtil {

    /**
     * Преобразует список массивов объектов в Map&lt;Integer, String&gt;.
     * Предполагается, что каждый элемент списка — массив из двух элементов:
     * [0] — ключ типа Integer, [1] — значение типа String.
     *
     * @param list список массивов объектов
     * @return карта, составленная из элементов списка
     * @throws ClassCastException если элементы массива имеют несовместимые типы
     * @throws IllegalStateException если в списке дублируются ключи
     */
    public Map<Integer, String> convertListToMap(List<Object[]> list) {
        return list.stream()
                .collect(Collectors.toMap(
                        ob -> (Integer) ob[0],
                        ob -> (String) ob[1]
                ));
    }

    /**
     * Генерирует случайный пароль длиной 8 символов.
     * Использует UUID без дефисов, обрезает до 8 символов.
     * <p><b>Важно:</b> не предназначен для генерации криптостойких паролей.</p>
     *
     * @return строка из 8 символов, представляющая случайный пароль
     */
    public String genPwd() {
        return UUID.randomUUID()
                .toString()
                .replaceAll("-", "")
                .substring(0, 8);
    }

    /**
     * Генерирует 6-значный одноразовый код (OTP).
     * Используется для подтверждения операций, двухфакторной аутентификации и пр.
     *
     * @return строка из 6 цифр с ведущими нулями (например, "004231")
     */
    public String genOtp() {
        return String.format("%06d", new Random().nextInt(999999));
    }
}
