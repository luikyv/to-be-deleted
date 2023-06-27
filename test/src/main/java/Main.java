import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        String valuesStr = " value1 value2 value3 ";

        Set<String> values = Arrays.stream(valuesStr.trim().split(" ")).collect(Collectors.toSet());
        System.out.println(values);
        System.out.println(values.contains("value1"));

        // Valores separados por espaço
    }
}
