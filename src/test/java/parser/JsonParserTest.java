package parser;

import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import shop.Cart;
import shop.CartTest;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {

    private static Parser parser;
    private static Gson gson;

    private Cart cart;

    @BeforeAll
    public static void before() {
        gson = new Gson();
        parser = new JsonParser();
    }

    @BeforeEach
    public void beforeEach() {
        cart = CartTest.createCart("test");
    }

    @AfterEach
    public void afterEach() {
        File cartJson = new File("src/main/resources/" + cart.getCartName() + ".json");
        cartJson.deleteOnExit();
    }

    @Test
    public void checkWritingData() {
        parser.writeToFile(cart);
        Cart savedCart = parser.readFromFile(new File("src/main/resources/" + cart.getCartName() + ".json"));
        assertEquals(cart.getCartName(), savedCart.getCartName());
        assertEquals(gson.toJson(cart), gson.toJson(savedCart));
    }

    @ParameterizedTest
    @CsvSource({
            "src/main/resources/e.json",
            "src/main/resources/e1.json",
            "src/eugen-cart.json",
            "src/main/asd.json",
            "src/main/111.json"
    })
    public void throwExceptionReadFromFile(String path){
        assertThrows(NoSuchFileException.class, () -> parser.readFromFile(new File(path)));
    }

    @Disabled("Disabled test")
    @Test
    public void checkIfFileIsCreated(){
        parser.writeToFile(cart);
        File cartJson = new File("src/main/resources/" + cart.getCartName() + ".json");
        assertTrue(cartJson.exists());
    }
}