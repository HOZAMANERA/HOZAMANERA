import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class SelfCheck {
    public static void main(String[] args) {
        check("1 100 0", "120,00");
        check("20 100 0", "2400,00", "Бесплатная обработка: да");
        check("19 100 0", "2280,00", "Бесплатная обработка: нет");
        check("10000 5000000 30", "42000000000,00");
        check("0 100 0", "Ошибка:");
        check("10001 100 0", "Ошибка:");
        check("1 0 0", "Ошибка:");
        check("1 5000000.01 0", "Ошибка:");
        check("1 100 30", "84,00");
        check("1 100 30.01", "Ошибка:");
        check("", "Ошибка:");
        check("q 100 0", "Ошибка:");
        System.out.println("SelfCheck: OK");
    }

    private static void check(String input, String... expectedParts) {
        String result = run(input);
        for (String expected : expectedParts) {
            if (!result.contains(expected)) {
                throw new AssertionError("Ввод: " + input + "; ожидалось: " + expected + "; вывод: " + result);
            }
        }
    }

    private static String run(String input) {
        InputStream oldIn = System.in;
        PrintStream oldOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            OrderCalculator.main(new String[0]);
            return output.toString(StandardCharsets.UTF_8);
        } finally {
            System.setIn(oldIn);
            System.setOut(oldOut);
        }
    }
}

