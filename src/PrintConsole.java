import java.util.HashMap;

public class PrintConsole implements Printable {
    @Override
    public void print(HashMap<Integer, Product> hashMap) {
        System.out.printf("%s %18s %18s %18s\n", "Код", "Наименование", "Цена/за.ед", "Кол-во товара");
        hashMap.entrySet().forEach(System.out::println);
    }
}