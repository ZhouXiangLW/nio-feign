package niofeigncore.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Array test")
class ArrayUtilsTest {

    @Test
    void should_combine_arrays() {
        Integer[] arr1 = {1, 2};
        Integer[] arr2 = {3, 4};

        assertEquals(4, ArrayUtils.combine(arr1, arr2).length);
    }

    @Test
    void should_return_false_when_array_is_not_empty() {
        assertFalse(ArrayUtils.isEmpty(new Integer[] {1, 2}));
    }

    @Test
    void should_return_true_when_array_is_null() {
        assertTrue(ArrayUtils.isEmpty(null));
    }

    @Test
    void should_return_true_when_array_length_is_zero() {
        assertTrue(ArrayUtils.isEmpty(new Integer[]{}));
    }
}