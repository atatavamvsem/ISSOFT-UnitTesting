package shop;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RealItemTest {

    public static RealItem createValidRealItem(String name, double price, double weight) {
        RealItem real = new RealItem();
        real.setName(name);
        real.setPrice(price);
        real.setWeight(weight);

        return real;
    }

    @ParameterizedTest
    @CsvSource({
            "boat, 1000, 2000",
            "table, 20, 30"
    })
    public void checkValidRealItem(String name, double price, double weight){
        RealItem item = createValidRealItem(name, price, weight);
        assertAll("valid item",
                () -> assertEquals(name, item.getName()),
                () -> assertEquals(price, item.getPrice()),
                () -> assertEquals(weight, item.getWeight())
        );
    }
}