package parser;

import com.google.gson.Gson;
import org.testng.annotations.*;
import shop.Cart;
import shop.CartTest;

import java.io.*;
import java.util.UUID;

import static org.testng.Assert.*;

class JsonParserTest {

    private static Parser parser;
    private static Gson gson;

    private Cart cart;

    @BeforeGroups(groups = "writing")
    public static void before() {
        gson = new Gson();
        parser = new JsonParser();
    }

    @BeforeMethod(groups = "writing")
    public void beforeEach() {
        cart = CartTest.createCart(UUID.randomUUID().toString());
    }

    @AfterMethod
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

    @Test
    public void checkReadFile() {
        try (FileWriter writer = new FileWriter("src/main/resources/" + cart.getCartName() + ".json")) {
            writer.write(gson.toJson(cart));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Cart checkedCart = parser.readFromFile(new File("src/main/resources/" + cart.getCartName() + ".json"));

        assertTrue(gson.toJson(checkedCart).equals(gson.toJson(cart)), "File reading is wrong");
    }

    @DataProvider
    public Object[][] createCartNames() {
        return new Object[][]{
                {"src/main/resources/e.json"},
                {"D:\\projects\\UnitTesting-master\\src\\main\\resources\\e.json"},
                {"A:eugen-cart.json"},
                {"../Documents/Clients/contacts/eugen-cart.json"},
                {"src\\main\\resources\\e.json"},
                {"11\\...\\1212//\\e.json"},
        };
    }

    @Test(dataProvider = "createCartNames", expectedExceptions = {NoSuchFileException.class})
    public void throwExceptionReadFromFile(String path) {
        parser.readFromFile(new File(path));
    }

    @Test(groups = {"writing"})
    public void checkIfFileIsCreated() {
        parser.writeToFile(cart);
        File cartJson = new File("src/main/resources/" + cart.getCartName() + ".json");

        assertTrue(cartJson.exists(), "File doesn't exist");
    }
}
