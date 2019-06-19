package pt.tpereira.cashierhelper.model;

public class Product {

    private double price;
    private String name;

    @Override
    public String toString() {
        return name + " -> " + price+"€";
    }

    public Product(double price, String name) {
        this.price = price;
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
