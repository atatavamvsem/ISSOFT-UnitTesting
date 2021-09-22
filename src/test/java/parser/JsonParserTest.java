package parser;

import com.google.gson.Gson;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import shop.Cart;
import shop.CartTest;

import java.io.*;
import java.util.UUID;

import static org.testng.Assert.*;

class JsonParserTest {

    private static Parser parser;
    private static Gson gson;

    private Cart cart;

    SoftAssert softAssert = new SoftAssert();

    @BeforeSuite(groups = {"writing", "reading", "parser.JsonParser"})
    public static void before() {
        gson = new Gson();
        parser = new JsonParser();
    }

    @BeforeMethod(groups = {"writing", "reading", "parser.JsonParser"})
    public void beforeEach() {
        cart = CartTest.createCart(UUID.randomUUID().toString());
    }

    @AfterMethod(groups = {"writing", "reading", "parser.JsonParser"})
    public void afterEach() {
        File cartJson = new File("src/main/resources/" + cart.getCartName() + ".json");
        cartJson.deleteOnExit();
    }

    @Test(groups = {"writing", "parser.JsonParser"})
    public void checkWritingData() {
        parser.writeToFile(cart);

        Cart checkedCart = new Cart(UUID.randomUUID().toString());

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/" + cart.getCartName() + ".json"))) {
            checkedCart = gson.fromJson(reader.readLine(), Cart.class);
        } catch (FileNotFoundException ex) {
            throw new NoSuchFileException(String.format("File %s.json not found!", cart.getCartName()), ex);
        } catch (IOException e) {
            e.printStackTrace();
        }

        softAssert.assertEquals(cart.getCartName(), checkedCart.getCartName());
        softAssert.assertEquals(gson.toJson(cart), gson.toJson(checkedCart));
        softAssert.assertAll();
    }

    @Test(groups = {"reading", "parser.JsonParser"})
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

    @Test(dataProvider = "createCartNames", groups = {"reading", "parser.JsonParser"}, expectedExceptions = {NoSuchFileException.class})
    public void throwExceptionReadFromFile(String path) {
        parser.readFromFile(new File(path));
    }

    @Test(groups = {"writing", "parser.JsonParser"}, enabled = false)
    public void checkIfFileIsCreated() {
        parser.writeToFile(cart);
        File cartJson = new File("src/main/resources/" + cart.getCartName() + ".json");

        assertTrue(cartJson.exists(), "File doesn't exist");
    }
}
