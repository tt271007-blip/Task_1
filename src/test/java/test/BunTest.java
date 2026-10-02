package test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import praktikum.Bun;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты для класса Bun")
class BunTest {

    private static final float DELTA = 0.0001f;

    @ParameterizedTest(name = "Булка \"{0}\" с ценой {1}")
    @DisplayName("Конструктор и геттеры должны корректно сохранять и возвращать имя и цену")
    @CsvSource({
            "Флюоресцентная булка, 988.0",
            "Краторная булка, 1255.0",
            "Марсианская булка, 500.0"
    })
    void constructorAndGettersShouldWorkCorrectly(String name, float price) {
        // Given
        Bun bun = new Bun(name, price);

        // Then
        assertEquals(name, bun.getName(), "Имя булочки должно совпадать с переданным в конструктор");
        assertEquals(price, bun.getPrice(), DELTA, "Цена булочки должна совпадать с переданной в конструктор");
    }

    @ParameterizedTest(name = "Поля булки \"{0}\" доступны напрямую")
    @DisplayName("Поля класса доступны напрямую (public)")
    @CsvSource({
            "Марсианская булка, 500.0",
            "Обычная булка, 100.0"
    })
    void fieldsShouldBeAccessibleDirectly(String name, float price) {
        // Given
        Bun bun = new Bun(name, price);

        // Then
        assertEquals(name, bun.name);
        assertEquals(price, bun.price, DELTA);
    }
}
