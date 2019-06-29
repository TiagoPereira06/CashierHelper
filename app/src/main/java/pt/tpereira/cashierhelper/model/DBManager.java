package pt.tpereira.cashierhelper.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class DBManager {

    private HashMap<Character, LinkedList<Product>> map;
    private static final DecimalFormat df = new DecimalFormat("#0.00");

    public DBManager() {
        map = new HashMap<>();
    }

    public void initItems(InputStream file) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(file));
        String line;
        while ((line = br.readLine()) !=null){
            add(line.split(" "));
        }
    }

    private void add(String[] parts){
        String name = parts[0];
        Character key = name.charAt(0);
        Double price = Double.valueOf(parts[1]);

        LinkedList<Product> products = map.get(key);
        if (products != null) {
            products.add(new Product(name, price));
        }else{
            products = new LinkedList<>();
            map.put(key, products);
            products.add(new Product(name, price));
        }

    }

    public Character[] getKeysArray() {
        Set<Character> keys = map.keySet();
        return keys.toArray(new Character[0]);
    }

    public List<Product> get(Character c) {
        List<Product> list = map.get(c);
        return list == null ? Collections.<Product>emptyList() : list;
    }

    public static String format(Double amount) {
        return df.format(amount) + "€";
    }
}
