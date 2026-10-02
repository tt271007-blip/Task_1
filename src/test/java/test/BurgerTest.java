package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;
import praktikum.IngredientType;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тесты класса Burger")
class BurgerTest {

    private static final float DELTA = 0.0001f;

    @Mock
    private Bun bun;

    @Mock
    private Ingredient ingredient1;

    @Mock
    private Ingredient ingredient2;

    private Burger burger;

    @BeforeEach
    void setUp() {
        burger = new Burger();
        burger.setBuns(bun);
    }

    @Test
    @DisplayName("setBuns() устанавливает булочку")
    void setBunsShouldAssignBun() {
        assertEquals(bun, burger.bun);
    }

    @Test
    @DisplayName("addIngredient добавляет ингредиент в список")
    void addIngredientShouldAddToList() {
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);

        assertEquals(2, burger.ingredients.size());
        assertSame(ingredient1, burger.ingredients.get(0));
        assertSame(ingredient2, burger.ingredients.get(1));
    }

    @Test
    @DisplayName("Новый бургер содержит пустой список ингредиентов")
    void newBurgerHasEmptyIngredients() {
        Burger emptyBurger = new Burger();
        assertNotNull(emptyBurger.ingredients);
        assertTrue(emptyBurger.ingredients.isEmpty());
    }

    @Test
    @DisplayName("moveIngredient перемещает ингредиент на новую позицию")
    void moveIngredientShouldReorder() {
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);

        burger.moveIngredient(0, 1);

        assertEquals(2, burger.ingredients.size());
        assertSame(ingredient2, burger.ingredients.get(0));
        assertSame(ingredient1, burger.ingredients.get(1));
    }

    @Test
    @DisplayName("moveIngredient не меняет количество элементов")
    void moveIngredientKeepsSize() {
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);

        burger.moveIngredient(0, 1);

        assertEquals(2, burger.ingredients.size());
    }

    @ParameterizedTest(name = "Цена булочки: {0}, Цена ингредиента 1: {1}, Цена ингредиента 2: {2}, Ожидаемая цена: {3}")
    @DisplayName("getPrice считает цену как 2*булочка + сумма ингредиентов")
    @CsvSource({
            "100.0, 50.0, 80.0, 330.0",
            "120.0, 0.0, 0.0, 240.0",
            "10.0, 5.0, 5.0, 30.0"
    })
    void getPriceShouldCalculateCorrectly(float bunPrice, float ing1Price, float ing2Price, float expectedPrice) {
        when(bun.getPrice()).thenReturn(bunPrice);
        when(ingredient1.getPrice()).thenReturn(ing1Price);
        when(ingredient2.getPrice()).thenReturn(ing2Price);

        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);

        assertEquals(expectedPrice, burger.getPrice(), DELTA);
    }

    @Test
    @DisplayName("getPrice для бургера без ингредиентов = 2*булочка")
    void getPriceWithoutIngredients() {
        when(bun.getPrice()).thenReturn(120f);

        assertEquals(240f, burger.getPrice(), DELTA);
    }

    @Test
    @DisplayName("getReceipt формирует чек с булочкой, ингредиентами и ценой")
    void getReceiptShouldContainAllParts() {
        when(bun.getName()).thenReturn("bulka");
        when(bun.getPrice()).thenReturn(100f);
        when(ingredient1.getType()).thenReturn(IngredientType.SAUCE);
        when(ingredient1.getName()).thenReturn("sauce");
        when(ingredient1.getPrice()).thenReturn(50f);
        when(ingredient2.getType()).thenReturn(IngredientType.FILLING);
        when(ingredient2.getName()).thenReturn("kotleta");
        when(ingredient2.getPrice()).thenReturn(80f);

        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);

        String expectedReceipt = String.format(
                "(==== bulka ====)%n"
                        + "= sauce sauce =%n"
                        + "= filling kotleta =%n"
                        + "(==== bulka ====)%n"
                        + "%n"
                        + "Price: 330,000000%n");
        assertEquals(expectedReceipt, burger.getReceipt());
    }

    @Test
    @DisplayName("getReceipt содержит тип и имя каждого ингредиента")
    void getReceiptContainsIngredientTypeAndName() {
        when(bun.getName()).thenReturn("bulka");
        when(ingredient1.getType()).thenReturn(IngredientType.SAUCE);
        when(ingredient1.getName()).thenReturn("cheese");

        burger.addIngredient(ingredient1);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("= sauce cheese ="));
        assertTrue(receipt.contains("(==== bulka ====)"));
    }
}