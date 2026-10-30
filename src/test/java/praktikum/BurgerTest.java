package praktikum;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Locale;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    private Burger burger;

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Mock
    private Bun bunMock;

    @Mock
    private Ingredient ingredientMock;

    @Mock
    private Ingredient secondIngredientMock;

    @Before
    public void setUp() {
        burger = new Burger();
    }

    @Test
    public void shouldSetBuns () {

        burger.setBuns(bunMock);

        assertEquals("Метод setBuns() должен добавить булку в бургер!", bunMock, burger.bun);
    }

    @Test
    public void shouldAddIngredient () {

        burger.addIngredient(ingredientMock);

        assertEquals("Метод addIngredient() должен добавить ингредиент в бургер!", ingredientMock, burger.ingredients.get(0));
    }

    @Test
    public void shouldRemoveIngredient () {

        burger.addIngredient(ingredientMock);

        burger.removeIngredient(0);

        assertTrue("Метод removeIngredient() должен убрать ингредиент из бургера!", burger.ingredients.isEmpty());
    }

    @Test
    public void shouldMoveIngredient () {

        burger.addIngredient(ingredientMock);
        burger.addIngredient(secondIngredientMock);

        burger.moveIngredient(0, 1);

        collector.checkThat("Первый ингредиент должен сместиться на место второго!",
                burger.ingredients.get(0), equalTo(secondIngredientMock));
        collector.checkThat("Второй ингредиент должен встать на место первого!",
                burger.ingredients.get(1), equalTo(ingredientMock));
    }

    @Test
    public void shouldGetReceipt () {

        when(bunMock.getName()).thenReturn("black bun");
        when(bunMock.getPrice()).thenReturn(100f);
        when(ingredientMock.getType()).thenReturn(IngredientType.FILLING);
        when(ingredientMock.getName()).thenReturn("dinosaur");
        when(ingredientMock.getPrice()).thenReturn(200f);

        burger.setBuns(bunMock);
        burger.addIngredient(ingredientMock);

        String expectedPriceFormatted = String.format(Locale.getDefault(Locale.Category.FORMAT), "%f", 400.0f);

        String expectedReceipt = String.format(
                "(==== black bun ====)%n" +
                "= filling dinosaur =%n" +
                "(==== black bun ====)%n" +
                "%nPrice: %s%n", expectedPriceFormatted
        );

        String actualReceipt = burger.getReceipt();

        assertEquals("Текст сформированного чека не совпадает с ожидаемым!", expectedReceipt, actualReceipt);
    }
}