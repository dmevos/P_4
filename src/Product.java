import java.util.Objects;

public class Product implements CountChangable {

    private final String name;
    private final double price;
    private final int count;

    public Product(String name, double price, int count) {

        this.name = name;
        this.price = price;
        this.count = count;
    }


    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("%15s %20s %20s", name, price, count);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    //Open-closed principle
    @Override
    public Product changeCount(int count) {
        return new Product(getName(), getPrice(), count);
    }
}