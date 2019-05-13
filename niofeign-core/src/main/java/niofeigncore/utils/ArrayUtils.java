package niofeigncore.utils;

import java.util.Arrays;

public class ArrayUtils {

    private ArrayUtils() {
    }

    @SafeVarargs
    public static <T> T[] combine(T[] first, T[]... rest) {
        int arrayLength = first.length;
        for (T[] ts : rest) {
            arrayLength += ts.length;
        }
        T[] result = Arrays.copyOf(first, arrayLength);
        int offset = first.length;
        for (T[] array : rest) {
             System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

}
