package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import praktikum.Ingredient;
import praktikum.IngredientType;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты класса Ingredient")
class IngredientTest {

    private static final float DELTA = 0.0001f;
    private static final String NAME = "sauce";
    private static final float PRICE = 50f;

    private Ingredient ingredient;

    @BeforeEach
    void setUp() {
        ingredient = new Ingredient(IngredientType.SAUCE, NAME, PRICE);
    }

    @ParameterizedTest(name = "Тип: {0}, Имя: {1}, Цена: {2}")
    @DisplayName("Конструктор должен корректно сохранять все поля")
    @CsvSource({
            "SAUCE, sauce, 50.0",
            "FILLING, kotleta, 80.0",
            "SAUCE, , 10.0", // пустое имя
            "SAUCE, null, 10.0", // null имя (будет преобразовано)
            "SAUCE, something, 0.0", // нулевая цена
            "SAUCE, weird, -5.0" // отрицательная цена
    })
    void constructorShouldSetAllFields(IngredientType type, String name, float price) {
        // Given
        Ingredient ing = new Ingredient(type, name, price);

        // Then
        assertEquals(type, ing.type);
        assertEquals(name, ing.name);
        assertEquals(price, ing.price, DELTA);
    }

    @Test
    @DisplayName("Конструктор принимает null в качестве type")
    void constructorShouldAcceptNullType() {
        Ingredient nullType = new Ingredient(null, "something", 10f);

        assertNull(nullType.type);
    }

    @Test
    @DisplayName("getName() возвращает имя, переданное в конструктор")
    void getNameShouldReturnName() {
        assertEquals(NAME, ingredient.getName());
    }

    @Test
    @DisplayName("getName() возвращает null, если имя null")
    void getNameShouldReturnNull() {
        Ingredient nullName = new Ingredient(IngredientType.SAUCE, null, 10f);

        assertNull(nullName.getName());
    }

    @Test
    @DisplayName("getName() возвращает пустую строку, если имя пустое")
    void getNameShouldReturnEmptyString() {
        Ingredient empty = new Ingredient(IngredientType.SAUCE, "", 10f);

        assertEquals("", empty.getName());
    }

    @ParameterizedTest(name = "Цена: {0}")
    @DisplayName("getPrice() возвращает цену, переданную в конструктор")
    @ValueSource(floats = {50f, 0f, -5f})
    void getPriceShouldReturnPrice(float price) {
        Ingredient ing = new Ingredient(IngredientType.SAUCE, "test", price);

        assertEquals(price, ing.getPrice(), DELTA);
    }

    @Test
    @DisplayName("Поле name можно изменить напрямую")
    void nameFieldIsMutable() {
        ingredient.name = "new name";

        assertEquals("new name", ingredient.getName());
    }

    @Test
    @DisplayName("Поле price можно изменить напрямую")
    void priceFieldIsMutable() {
        ingredient.price = 999f;

        assertEquals(999f, ingredient.getPrice(), DELTA);
    }

    @Test
    @DisplayName("Поле type можно изменить напрямую")
    void typeFieldIsMutable() {
        ingredient.type = IngredientType.FILLING;

        assertEquals(IngredientType.FILLING, ingredient.getType());
    }
}
