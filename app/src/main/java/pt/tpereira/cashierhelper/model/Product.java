package pt.tpereira.cashierhelper.model;

public class Product {

    private final String name;
    private final double unitPrice;

    public Product(String name, double unitPrice) {
        this.name = name;
        this.unitPrice = unitPrice;
    }

    String getName() {
        return name;
    }

    double getUnitPrice() {
        return unitPrice;
    }

    @Override
    public String toString() {
        return name + " ( " + unitPrice + "€ ) ";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        return name.equals(other.name);
    }
}
