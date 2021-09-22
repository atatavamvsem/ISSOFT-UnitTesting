package shop;

import com.google.gson.Gson;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static shop.RealItemTest.createValidRealItem;
import static shop.VirtualItemTest.createValidVirtualItem;

public class CartTest {

    public static Cart createCart(String name) {
        Cart cart = new Cart(name);

        RealItem real = createValidRealItem(UUID.randomUUID().toString(), 100, 255);
        VirtualItem virtual = createValidVirtualItem(UUID.randomUUID().toString(), 100, 25);

        cart.addRealItem(real);
        cart.addVirtualItem(virtual);

        return cart;
    }

    public static Cart createCartWithoutItems(String name) {
        Cart cart = new Cart(name);

        return cart;
    }

    @DataProvider
    public Object[][] createCartNames() {
        return new Object[][]{
                {"Cedric"},
                {"Anne"},
        };
    }

    @Test(dataProvider = "createCartNames", groups = "shop.cart")
    public void checkValidVirtualItem(String name) {
        Cart cart = createCart(name);
        assertEquals(name, cart.getCartName(), "The name is wrong");
    }

    @Test(groups = {"shop.cart"})
    public void checkTotalPrice() {
        Cart cart = createCart(UUID.randomUUID().toString());
        assertEquals(240, cart.getTotalPrice(), "The price is wrong");
    }

    @Test(groups = {"shop.cart", "defect"})
    public void testDeleteVirtualItem() {
        Cart cartWithoutVI = createCartWithoutItems(UUID.randomUUID().toString());
        Cart cartWithDeletedVI = createCartWithoutItems(cartWithoutVI.getCartName());

        VirtualItem virtualItem = createValidVirtualItem(UUID.randomUUID().toString(), 100, 100);

        cartWithDeletedVI.addVirtualItem(virtualItem);
        cartWithDeletedVI.deleteVirtualItem(virtualItem);

        assertTrue(cartWithoutVI.getTotalPrice() == cartWithDeletedVI.getTotalPrice(), "Total price do not match");
    }

    @Test(groups = {"shop.cart", "defect"})
    public void testDeleteRealItem() {
        Cart cartWithoutRI = createCartWithoutItems(UUID.randomUUID().toString());
        Cart cartWithDeletedRI = createCartWithoutItems(cartWithoutRI.getCartName());

        RealItem realItem = createValidRealItem(UUID.randomUUID().toString(), 100, 100);

        cartWithDeletedRI.addRealItem(realItem);
        cartWithDeletedRI.deleteRealItem(realItem);

        assertTrue(cartWithoutRI.getTotalPrice() == cartWithDeletedRI.getTotalPrice(), "Total price do not match");
    }
}