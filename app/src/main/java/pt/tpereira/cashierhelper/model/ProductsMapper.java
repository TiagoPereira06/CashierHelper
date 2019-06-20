package pt.tpereira.cashierhelper.model;

import android.annotation.SuppressLint;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

public class ProductsMapper {

    private static final String format = "%-2d %-15s %-5s€ %-5s€%n";
    private static final DecimalFormat df = new DecimalFormat("#0.00");
    private LinkedList<Product> productsByOrder;
    private List<ProductBucket> productBuckets;
    private double totalValue;

    public ProductsMapper() {
        productBuckets = new LinkedList<>();
        productsByOrder = new LinkedList<>();
    }

    public void addProduct(Product product) {
        productsByOrder.add(product);
        for (ProductBucket pb : productBuckets) {
            if (pb.getProduct().equals(product)) {
                pb.increment();
                return;
            }
        }
        productBuckets.add(new ProductBucket(product));
    }

    public void removeLast() {
        if (productsByOrder.isEmpty()) return;
        Product p = productsByOrder.removeLast();
        for (ProductBucket pb : productBuckets) {
            if (pb.getProduct().equals(p)) {
                pb.decrement();
                if (pb.empty())
                    productBuckets.remove(pb);
            }
        }
    }

    public void clear() {
        productsByOrder.clear();
        productBuckets.clear();
        totalValue = 0;
    }

    @SuppressLint("DefaultLocale")
    public String getViewString() {
        int units;
        double unitPrice, total, totalSum = 0;
        StringBuilder res = new StringBuilder();
        for (ProductBucket pb : productBuckets) {
            Product p = pb.getProduct();
            units = pb.getUnits();
            unitPrice = p.getUnitPrice();
            total = units * unitPrice;
            res.append(String.format(format, units, p.getName(), df.format(unitPrice), df.format(total)));
            totalSum += total;
        }
        this.totalValue = totalSum;
        return res.toString();
    }

    public String getTotalPrice() {
        return df.format(totalValue) + "€";
    }
}

