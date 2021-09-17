package shop;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VirtualItemTest {

    public static VirtualItem createValidVirtualItem(String name, double price, double size) {
        VirtualItem virtual = new VirtualItem();
        virtual.setName(name);
        virtual.setPrice(price);
        virtual.setSizeOnDisk(size);

        return virtual;
    }

    @ParameterizedTest
    @CsvSource({
            "image, 1, 2",
            "file, 2, 3"
    })
    public void checkValidVirtualItem(String name, double price, double size){
        VirtualItem item = createValidVirtualItem(name, price, size);
        assertAll("valid item",
                () -> assertEquals(name, item.getName()),
                () -> assertEquals(price, item.getPrice()),
                () -> assertEquals(size, item.getSizeOnDisk())
        );
    }
}