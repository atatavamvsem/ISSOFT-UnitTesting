package parser;

import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import shop.Cart;
import shop.CartTest;

import java.io.*;
import java.util.UUID;

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
        cart = CartTest.createCart(UUID.randomUUID().toString());
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

        assertAll("valid data from file",
                () -> assertEquals(cart.getCartName(), savedCart.getCartName()),
                () -> assertEquals(gson.toJson(cart), gson.toJson(savedCart))
        );
    }

    /*I don't know if that approach is correct
    Can I mark that this method can throw an exception?
    Can I compare Json instead of String? */
    @ParameterizedTest
    @ValueSource(strings = {"src/main/resources/eugen-cart.json", "src/main/resources/andrew-cart.json"})
    public void checkReadFile(String path) throws IOException {
        Cart checkedCart = parser.readFromFile(new File(path));
        String expectedCart = new BufferedReader(new FileReader(new File(path))).readLine();

        assertTrue(gson.toJson(checkedCart).equals(expectedCart), "File reading is wrong");
    }

    @ParameterizedTest
    @CsvSource({
            "src/main/resources/e.json",
            "D:\\projects\\UnitTesting-master\\src\\main\\resources\\e.json",
            "A:eugen-cart.json",
            "../Documents/Clients/contacts/eugen-cart.json",
            "src\\main\\resources\\e.json",
            "11\\...\\1212//\\e.json"
    })
    public void throwExceptionReadFromFile(String path) {
        assertThrows(NoSuchFileException.class, () -> parser.readFromFile(new File(path)), "The exception was not thrown");
    }

    @Disabled("Disabled test")
    @Test
    public void checkIfFileIsCreated() {
        parser.writeToFile(cart);
        File cartJson = new File("src/main/resources/" + cart.getCartName() + ".json");

        assertTrue(cartJson.exists(), "File doesn't exist");
    }
}