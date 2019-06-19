package pt.tpereira.cashierhelper.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

public class DBManager {
    HashMap<String,LinkedList<Product>> db;

    public DBManager() {
        db = new HashMap<>();
    }

    public void initItems(InputStream file) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(file));
        String line = null;
        while ((line = br.readLine()) !=null){
            add(line.split(" "));
        }
    }

    private void add(String[] parts){
        String name = parts[0];
        String key = String.valueOf(name.charAt(0));
        Double price = Double.valueOf(parts[1]);

        if(db.containsKey(key)){
            db.get(key).add(new Product(price, name));
        }else{
            db.put(key,new LinkedList<Product>());
            db.get(key).add(new Product(price,name));
        }

    }

    public String[] getKeysArray() {
        Set<String> keys = db.keySet();
        return keys.toArray(new String[keys.size()]);
    }

    public LinkedList<Product> get (String s){
        return db.get(s);
    }
}
