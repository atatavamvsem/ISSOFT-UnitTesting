package shop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartTest {

    public static Cart createCart(String name){
        Cart cart = new Cart(name);

        RealItem real = RealItemTest.createValidRealItem("fridge", 100, 255);
        VirtualItem virtual = VirtualItemTest.createValidVirtualItem("file", 100, 25);

        cart.addRealItem(real);
        cart.addVirtualItem(virtual);

        return cart;
    }

    @ParameterizedTest
    @ValueSource(strings = { "test1", "test2", "test3"})
    public void checkValidVirtualItem(String name){
        Cart cart = createCart(name);
        assertEquals(name, cart.getCartName());
    }

    @Test
    public void checkTotalPrice(){
        Cart cart = createCart("testCart");
        assertEquals(240, cart.getTotalPrice());
    }
}