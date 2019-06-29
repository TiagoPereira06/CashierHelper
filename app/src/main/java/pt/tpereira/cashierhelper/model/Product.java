package pt.tpereira.cashierhelper.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

    private final String name;
    private final double unitPrice;

    public Product(String name, double unitPrice) {
        this.name = name;
        this.unitPrice = unitPrice;
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    double getUnitPrice() {
        return unitPrice;
    }

    protected Product(Parcel in) {
        name = in.readString();
        unitPrice = in.readDouble();
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

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " ( " + DBManager.format(unitPrice) + " ) ";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(unitPrice);
    }
}