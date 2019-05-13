package niofeigncore.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DisplayName("Array test")
class ArrayUtilsTest {

    @Test
    void name() {
        Integer[] arr1 = {1, 2};
        Integer[] arr2 = {3, 4};

        assertEquals(4, ArrayUtils.combine(arr1, arr2).length);
    }
}