package pt.tpereira.cashierhelper.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ProductsMapper implements Parcelable {

    private LinkedList<Product> productsByOrder;
    private List<ProductBucket> productBuckets;
    private double totalValue;

    public ProductsMapper() {
        productBuckets = new LinkedList<>();
        productsByOrder = new LinkedList<>();
    }

    public void addProduct(Product product) {
        addProduct(product, 1);
    }

    public void addProduct(Product product, int quantity) {
        for (int i = 0; i < quantity; i++)
            productsByOrder.add(product);
        for (ProductBucket pb : productBuckets) {
            if (pb.getProduct().equals(product)) {
                pb.addUnits(quantity);
                return;
            }
        }
        productBuckets.add(new ProductBucket(product, quantity));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ProductsMapper> CREATOR = new Parcelable.Creator<ProductsMapper>() {
        @Override
        public ProductsMapper createFromParcel(Parcel in) {
            return new ProductsMapper(in);
        }

        @Override
        public ProductsMapper[] newArray(int size) {
            return new ProductsMapper[size];
        }
    };

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

    protected ProductsMapper(Parcel in) {
        if (in.readByte() == 0x01) {
            productsByOrder = new LinkedList<Product>();
            in.readList(productsByOrder, Product.class.getClassLoader());
        } else {
            productsByOrder = null;
        }
        if (in.readByte() == 0x01) {
            productBuckets = new ArrayList<ProductBucket>();
            in.readList(productBuckets, ProductBucket.class.getClassLoader());
        } else {
            productBuckets = null;
        }
        totalValue = in.readDouble();
    }

    @SuppressLint("DefaultLocale")
    public String getViewString() {
        double totalSum = 0;
        StringBuilder res = new StringBuilder();
        for (ProductBucket pb : productBuckets) {
            res.append(pb);
            totalSum += pb.totalPrice();
        }
        this.totalValue = totalSum;
        return res.toString();
    }

    public Double getTotalPrice() {
        return totalValue;
    }

    public List<ProductBucket> getPurchasedProducts() {
        return productBuckets;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (productsByOrder == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(productsByOrder);
        }
        if (productBuckets == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(productBuckets);
        }
        dest.writeDouble(totalValue);
    }
}