package praktikum;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class BurgerPriceParameterizedTest {

    Burger burger;

    private AutoCloseable mocks;

    @Mock
    private Bun bunMock;

    @Mock
    private Ingredient ingredientMock;

    @Parameterized.Parameter(0)
    public float bunPrice;

    @Parameterized.Parameter(1)
    public float ingredientPrice;

    @Parameterized.Parameter(2)
    public float expectedPrice;

    @Parameterized.Parameters(name = "Цена булки - {0}, цена ингредиента - {1}")
    public static Object[][] getTestData() {
        return new Object[][] {
                {100f, 200f, 400f},
                {0f, 0f, 0f},
                {0f, 300f, 300f},
                {150f, 0f, 300f},
                {99.99f, 49.55f, 249.53f},
                {-200f, -100f, -500f},
        };
    }

    @Before
    public void setUp() {
        burger = new Burger();
        mocks = MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldCalculatePrice() {
        when(bunMock.getPrice()).thenReturn(bunPrice);
        when(ingredientMock.getPrice()).thenReturn(ingredientPrice);

        burger.setBuns(bunMock);
        burger.addIngredient(ingredientMock);

        float actualPrice = burger.getPrice();

        assertEquals("Расчет стоимости бургера неверен", expectedPrice, actualPrice, 0.001f);
    }

    @After
    public void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }
}
