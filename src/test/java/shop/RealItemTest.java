package shop;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
    public void checkToStringOutput(String name, double price, double weight) {
        RealItem item = createValidRealItem(name, price, weight);
        assertEquals(item.toString(), String.format("Class: %s; Name: %s; Price: %s; Weight: %s", item.getClass(), name, price, weight), "Strings do not match");
    }
}