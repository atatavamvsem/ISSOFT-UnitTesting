package shop;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static shop.RealItemTest.createValidRealItem;
import static shop.VirtualItemTest.createValidVirtualItem;

public class CartTest {
    private static Gson gson;

    @BeforeAll
    public static void before() {
        gson = new Gson();
    }

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

    @ParameterizedTest
    @ValueSource(strings = {"test1", "test2", "test3"})
    public void checkValidVirtualItem(String name) {
        Cart cart = createCart(name);
        assertEquals(name, cart.getCartName(), "The name is wrong");
    }

    @Test
    public void checkTotalPrice() {
        Cart cart = createCart(UUID.randomUUID().toString());
        assertEquals(240, cart.getTotalPrice(), "The price is wrong");
    }

    @Test
    public void testDeleteVirtualItem() {
        Cart cartWithoutVI = createCartWithoutItems(UUID.randomUUID().toString());
        Cart cartWithDeletedVI = createCartWithoutItems(cartWithoutVI.getCartName());

        VirtualItem virtualItem = createValidVirtualItem(UUID.randomUUID().toString(), 100, 100);

        cartWithDeletedVI.addVirtualItem(virtualItem);
        cartWithDeletedVI.deleteVirtualItem(virtualItem);

        assertTrue(gson.toJson(cartWithoutVI).equals(gson.toJson(cartWithDeletedVI)), "Сarts do not match");
    }

    @Test
    public void testDeleteRealItem() {
        Cart cartWithoutRI = createCartWithoutItems(UUID.randomUUID().toString());
        Cart cartWithDeletedRI = createCartWithoutItems(cartWithoutRI.getCartName());

        RealItem realItem = createValidRealItem(UUID.randomUUID().toString(), 100, 100);

        cartWithDeletedRI.addRealItem(realItem);
        cartWithDeletedRI.deleteRealItem(realItem);

        assertTrue(gson.toJson(cartWithoutRI).equals(gson.toJson(cartWithDeletedRI)), "Сarts do not match");
    }
}