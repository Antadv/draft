package javase.hashmap;

import java.util.HashMap;
import java.util.Map;

/**
 * @see Map.Entry#hashCode()
 * @author LBG - 2017/9/25 0025
 */
public class EntryHashCode {

    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(4, 10);
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            // 100
            int keyHashCode = entry.getKey().hashCode();
            // 1010
            int valueHashCode = entry.getValue().hashCode();
            int entryHashCode = entry.hashCode();

            // true
            System.out.println(entryHashCode == (keyHashCode ^ valueHashCode));
        }
    }
}
