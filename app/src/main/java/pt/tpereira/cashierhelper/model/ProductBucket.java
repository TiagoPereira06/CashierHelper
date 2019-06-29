package pt.tpereira.cashierhelper.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;

public class ProductBucket implements Parcelable {
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ProductBucket> CREATOR = new Parcelable.Creator<ProductBucket>() {
        @Override
        public ProductBucket createFromParcel(Parcel in) {
            return new ProductBucket(in);
        }

        @Override
        public ProductBucket[] newArray(int size) {
            return new ProductBucket[size];
        }
    };
    private final Product product;
    private static final String format = "%-2d %-15s %-6s %-6s%n";

    private int units;

    ProductBucket(Product product) {
        this.product = product;
        units = 1;
    }

    ProductBucket(Product product, int units) {
        this.product = product;
        this.units = units;
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

    protected ProductBucket(Parcel in) {
        product = (Product) in.readValue(Product.class.getClassLoader());
        units = in.readInt();
    }

    public int getUnits() {
        return units;
    }

    double totalPrice() {
        return product.getUnitPrice() * units;
    }

    @Override
    public String toString() {
        return String.format(
                Locale.getDefault(),
                format,
                units,
                product.getName(),
                DBManager.format(product.getUnitPrice()),
                DBManager.format(totalPrice()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(product);
        dest.writeInt(units);
    }
}
