package shop;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class RealItemTest {

    public static RealItem createValidRealItem(String name, double price, double weight) {
        RealItem real = new RealItem();
        real.setName(name);
        real.setPrice(price);
        real.setWeight(weight);

        return real;
    }

    @DataProvider
    public Object[][] createRealItem() {
        return new Object[][] {
                { "boat", 23, 34},
                { "table", 44, 44},
        };
    }

    @Test(dataProvider = "createRealItem")
    public void checkToStringOutput(String name, double price, double weight) {
        RealItem item = createValidRealItem(name, price, weight);
        assertEquals(item.toString(), String.format("Class: %s; Name: %s; Price: %s; Weight: %s", item.getClass(), name, price, weight), "Strings do not match");
    }
}