import java.util.Locale;
import java.util.Scanner;

public class OrderCalculator {
    private static final double VAT_RATE = 20.0;
    private static final int FREE_PROCESSING_FROM = 20;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);

        System.out.print("Количество: ");
        if (!scanner.hasNextInt()) {
            System.out.println("Ошибка: количество должно быть целым числом.");
            return;
        }
        int quantity = scanner.nextInt();

        System.out.print("Цена за единицу: ");
        if (!scanner.hasNextDouble()) {
            System.out.println("Ошибка: цена должна быть числом.");
            return;
        }
        double price = scanner.nextDouble();

        System.out.print("Скидка в процентах: ");
        if (!scanner.hasNextDouble()) {
            System.out.println("Ошибка: скидка должна быть числом.");
            return;
        }
        double discountPercent = scanner.nextDouble();

        if (!isValid(quantity, price, discountPercent)) {
            System.out.println("Ошибка: количество от 1 до 10000, цена больше 0 и не больше 5000000, скидка от 0 до 30 %.");
            return;
        }

        double base = calculateBase(quantity, price);
        double afterDiscount = applyDiscount(base, discountPercent);
        double vat = calculateVat(afterDiscount, VAT_RATE);
        double total = afterDiscount + vat;

        Locale ru = Locale.forLanguageTag("ru-RU");
        System.out.printf(ru, "Базовая сумма: %.2f руб.%n", base);
        System.out.printf(ru, "После скидки: %.2f руб.%n", afterDiscount);
        System.out.printf(ru, "НДС: %.2f руб.%n", vat);
        System.out.printf(ru, "Итого: %.2f руб.%n", total);
        System.out.println("Бесплатная обработка: " + (isFreeProcessing(quantity) ? "да" : "нет"));
    }

    private static double calculateBase(int quantity, double price) {
        return quantity * price;
    }

    private static double applyDiscount(double base, double discountPercent) {
        return base * (1 - discountPercent / 100);
    }

    private static double calculateVat(double afterDiscount, double vatRate) {
        return afterDiscount * vatRate / 100;
    }

    private static boolean isValid(int quantity, double price, double discountPercent) {
        return quantity >= 1 && quantity <= 10000
                && Double.isFinite(price) && price > 0 && price <= 5000000
                && Double.isFinite(discountPercent) && discountPercent >= 0 && discountPercent <= 30;
    }

    private static boolean isFreeProcessing(int quantity) {
        return quantity >= FREE_PROCESSING_FROM;
    }
}

