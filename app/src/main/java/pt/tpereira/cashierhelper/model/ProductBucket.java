package pt.tpereira.cashierhelper.model;

class ProductBucket {

    private final Product product;
    private int units;

    ProductBucket(Product product) {
        this.product = product;
        units = 1;
    }

    void increment() {
        ++units;
    }

    void decrement() {
        --units;
    }

    boolean empty() {
        return units == 0;
    }

    public Product getProduct() {
        return product;
    }

    public int getUnits() {
        return units;
    }
}
