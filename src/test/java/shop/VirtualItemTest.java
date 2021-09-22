package shop;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class VirtualItemTest {

    public static VirtualItem createValidVirtualItem(String name, double price, double size) {
        VirtualItem virtual = new VirtualItem();
        virtual.setName(name);
        virtual.setPrice(price);
        virtual.setSizeOnDisk(size);

        return virtual;
    }

    @DataProvider
    public Object[][] createVirtualItem() {
        return new Object[][] {
                { "file", 23, 34},
                { "image", 400, 450},
        };
    }

    @Test(dataProvider = "createVirtualItem", groups = {"shop.virtualItem"})
    public void checkToStringOutput(String name, double price, double size){
        VirtualItem item = createValidVirtualItem(name, price, size);
        assertEquals(item.toString(), String.format("Class: %s; Name: %s; Price: %s; Size on disk: %s", item.getClass(), name, price, size), "Strings do not match");
    }
}