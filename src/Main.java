import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //принцип инверсии зависимостей(Dependency Inversion Principle)
        Printable printProductConsole = new PrintConsole();
        Colorable consoleColorGreen = ColorConsole.ANSI_GREEN;
        Colorable consoleColorReset = ColorConsole.ANSI_RESET;
        Colorable consoleColorYellow = ColorConsole.ANSI_YELLOW;

        String inputString = "";
        int productNumber;
        int productCount;

        Scanner scanner = new Scanner(System.in);
        //создание списка продуктов
        HashMap<Integer, Product> productMap = new HashMap<>();
        productMap.put(1, new Product("молоко", 99.99, 100));
        productMap.put(2, new Product("курица", 250.91, 50));
        productMap.put(3, new Product("бананы", 40, 90));
        productMap.put(4, new Product("лапша", 25.45, 35));

        //принцип инверсии зависимостей(Dependency Inversion Principle)
        printProductConsole.print(productMap);

        //создание корзины
        HashMap<Integer, Product> basketMap = new HashMap<>();

        while (!inputString.equals("end")) {

            System.out.println(consoleColorGreen.color() + "Введите КОД товара и желаемое количество через пробел. " + consoleColorReset.color());
            inputString = scanner.nextLine();
            String[] pieces = inputString.split("\\s+");
            try {
                if (inputString.equals("del")) {
                    if (!basketMap.isEmpty()) {
                        printProductConsole.print(basketMap);
                        System.out.println("Введите КОД товара который хотите удалить");
                        productNumber = Integer.parseInt(scanner.nextLine());

                        Product productToProductMap = productMap.get(productNumber).changeCount(basketMap.get(productNumber).getCount() + productMap.get(productNumber).getCount());
                        productMap.put(productNumber, productToProductMap);

                        basketMap.remove(productNumber);
                        System.out.println("удалено успешно...");
                        //Правило DRY (Don’t Repeat Yourself)
                        showShopAndBasket(printProductConsole, productMap, basketMap);

                    }
                } else {
                    //Правило Magic: не используй числа напрямую в коде.Переменные productNumber и productCount
                    productNumber = Integer.parseInt(pieces[0]);
                    productCount = Integer.parseInt(pieces[1]);

                    if (productCount <= productMap.get(productNumber).getCount()) {
                        Product productToBasketMap = productMap.get(productNumber).changeCount(productCount);
                        Product productToProductMap;
                        if (basketMap.containsKey(productNumber)) {
                            productToProductMap = productMap.get(productNumber).changeCount(basketMap.get(productNumber).getCount() + productMap.get(productNumber).getCount() - productCount);
                        } else {
                            productToProductMap = productMap.get(productNumber).changeCount(productMap.get(productNumber).getCount() - productCount);
                        }
                        productMap.put(productNumber, productToProductMap);

                        basketMap.put(productNumber, productToBasketMap);
                    }

                    //Правило DRY (Don’t Repeat Yourself)
                    showShopAndBasket(printProductConsole, productMap, basketMap);
                    System.out.println(consoleColorYellow.color() + "Для удаления товара из корзины введите `del` или введите `end` для завершения покупок" + consoleColorYellow.color());

                }

            } catch (Exception ex) {
                if (!pieces[0].equals("end"))
                    System.out.println("ОШИБКА! Что-то не то ввели.");
            }

        }

        double sum = result(basketMap);
        printProductConsole.print(basketMap);
        System.out.println("Итого: " + sum);


    }

    private static double result(HashMap<Integer, Product> basketMap) {
        return basketMap.values().stream().map(product -> product.getCount() * product.getPrice()).reduce(0.0, Double::sum);
    }

    private static void showShopAndBasket(Printable printProductConsole, HashMap<Integer, Product> productMap, HashMap<Integer, Product> basketMap) {
        printProductConsole.print(productMap);
        System.out.println("Корзина:");
        printProductConsole.print(basketMap);
    }
}