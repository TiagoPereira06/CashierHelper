package pt.tpereira.cashierhelper;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import pt.tpereira.cashierhelper.model.DBManager;
import pt.tpereira.cashierhelper.model.Product;
import pt.tpereira.cashierhelper.model.ProductsMapper;


public class CashierController extends AppCompatActivity implements TotalValueDialog.TotalValueDialogListener {
    private ConstraintLayout rootPanel;
    private DBManager dataBase = new DBManager();
    private ProductsMapper productsMapper;
    private TextView listProducts, finalValue;
    private Button newClientButton, delLastProduct, endSessionButton, addTotalValueButton;
    private TableLayout keysView, allProductsView;


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
                newSessionRoutine();

            }
        });

        delLastProduct = findViewById(R.id.delLastProductButton);
        delLastProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productsMapper.removeLast();
                updatePurchaseList();
            }
        });

        endSessionButton = findViewById(R.id.endSessionButton);
        endSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCheckBoxesActivity();
            }
        });
        keysView = findViewById(R.id.charViewLayout);
        allProductsView = findViewById(R.id.productsViewLayout);
        finalValue = findViewById(R.id.costView);
        addTotalValueButton = findViewById(R.id.addFreeButton);
        addTotalValueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        productsMapper = new ProductsMapper();
        initKeysView();
    }

    private void openDialog() {
        TotalValueDialog totalValueDialog = new TotalValueDialog();
        totalValueDialog.show(getSupportFragmentManager(), "TotalValue Dialog");
    }

    private void startCheckBoxesActivity() {
        Intent intent = new Intent(this, CheckBoxesActivity.class);
        intent.putExtra("ProductsMapper", productsMapper);
        startActivity(intent);
    }

    private void updatePurchaseList() {
        listProducts.setText(productsMapper.getViewString());
        finalValue.setText(DBManager.format(productsMapper.getTotalPrice()));

    }

    private void showProducts(Character letter) {
        allProductsView.removeAllViews();
        final List<Product> products = dataBase.get(letter);
        for (int i = 0; i <products.size(); i++) {
            final int index = i;
            Button b = new Button(this);
            b.setText(products.get(i).toString());
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    productsMapper.addProduct(products.get(index));
                    updatePurchaseList();
                }
            });
            allProductsView.addView(b);
        }
        allProductsView.invalidate();
    }


    private void initKeysView() {
        Character[] keysArray = dataBase.getKeysArray();
        /*
        Character[] keysArray = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
                'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
                */
        Arrays.sort(keysArray);
        keysView.setOrientation(LinearLayout.HORIZONTAL);

        for (int i = 0; i <keysArray.length ; i++) {
            final Button keyButton = new Button(this);
            TableRow row = new TableRow(this);
            row.setOrientation(LinearLayout.HORIZONTAL);

            keyButton.setText(keysArray[i].toString());
            keyButton.setTextColor(Color.parseColor("#E91E63"));
            keyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showProducts(keyButton.getText().charAt(0));
                }
            });
            keyButton.setTextSize(15);
            row.addView(keyButton);
            keysView.addView(row);
        }
        keysView.invalidate();
    }

    private void newSessionRoutine() {
        listProducts.setText(null);
        finalValue.setText(null);
        productsMapper.clear();
        rootPanel.invalidate();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void applyValue(String name, String price, String units) {
        productsMapper.addProduct(new Product(name, Double.valueOf(price)), Integer.valueOf(units));
        updatePurchaseList();

    }
}
