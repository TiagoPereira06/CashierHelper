package pt.tpereira.cashierhelper;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.LinkedList;

import pt.tpereira.cashierhelper.model.DBManager;
import pt.tpereira.cashierhelper.model.Product;


public class CashierController extends AppCompatActivity {
    private final String AUX = "..................................................";
    private ConstraintLayout rootPanel;
    private final int MAX_LENGTH = AUX.length();
    private DBManager dataBase = new DBManager();
    private LinkedList<Product> purchaseProducts = new LinkedList<Product>();
    private TextView listProducts,finalValue;
    private Button newClientButton, delLastProduct, clearButton;
    private TableLayout keysView,productsView;
    private String[] beforeAdd;
    private double totalValue;
    private int productCounter = 0;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            dataBase.initItems(this.getResources().openRawResource(R.raw.productslist));
        } catch (IOException e) {
            e.printStackTrace();
        }
        rootPanel = findViewById(R.id.rootPanel);
        listProducts = findViewById(R.id.listOfProducts);
        listProducts.setMovementMethod(new ScrollingMovementMethod());
        newClientButton = findViewById(R.id.newUserButton);
        newClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalValue=0;
                listProducts.setText(null);
                finalValue.setText(null);
                purchaseProducts.clear();
                rootPanel.invalidate();

            }
        });
        delLastProduct = findViewById(R.id.delLastProductButton);
        delLastProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeAdd = listProducts.getText().toString().split("\n");
                Product x =purchaseProducts.getLast();
                totalValue-=x.getPrice();
                finalValue.setText(String.valueOf(df2.format(totalValue)));
                //listProducts.setText(beforeAdd);
                purchaseProducts.removeLast();
                --productCounter;
            }
        });
        clearButton = findViewById(R.id.endSessionButton);
        keysView = findViewById(R.id.charViewLayout);
        productsView = findViewById(R.id.productsViewLayout);
        finalValue = findViewById(R.id.costView);
        initKeysView();
    }

    private void updatePurchaseList(Product product) {
        purchaseProducts.add(product);
        ++productCounter;
        listProducts.append((product.toString()+"\n"));
    }

    private void showProducts(String letter) {
        productsView.removeAllViews();
        final LinkedList<Product> products = dataBase.get(letter);
        for (int i = 0; i <products.size(); i++) {
            final int index = i;
            Button b = new Button(this);
            b.setText(products.get(i).toString());
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updatePurchaseList(products.get(index));
                    totalValue+=products.get(index).getPrice();
                    finalValue.setText(String.valueOf(df2.format(totalValue)));
                }
            });
            productsView.addView(b);
        }
        productsView.invalidate();
    }

    private void initKeysView() {
        String[] keysArray = dataBase.getKeysArray();
        /*String[] keysArray = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
                "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};*/
        Arrays.sort(keysArray);
        keysView.setOrientation(LinearLayout.HORIZONTAL);

        for (int i = 0; i <keysArray.length ; i++) {
            final Button keyButton = new Button(this);
            TableRow row = new TableRow(this);
            row.setOrientation(LinearLayout.HORIZONTAL);

            keyButton.setText(keysArray[i]);
            keyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showProducts((String) keyButton.getText());
                }
            });
            keyButton.setTextSize(15);
            row.addView(keyButton);
            keysView.addView(row);
        }
        keysView.invalidate();
    }

    private String stringFormater(String name, String price) {
        String exit = name+AUX.subSequence(name.length(),MAX_LENGTH-6) + price;
        return exit;
    }


}
