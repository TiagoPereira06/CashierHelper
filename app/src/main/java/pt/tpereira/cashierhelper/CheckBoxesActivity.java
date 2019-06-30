package pt.tpereira.cashierhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.List;

import pt.tpereira.cashierhelper.model.ProductBucket;
import pt.tpereira.cashierhelper.model.ProductsMapper;

public class CheckBoxesActivity extends AppCompatActivity {

    private ProductsMapper productsMapper;
    private List<ProductBucket> purchaseProductsList;
    private int globalCounter;
    private ConstraintLayout rootPanel;
    private ScrollView checkBoxesScroll;
    private TableLayout checkBoxesTable;
    private Button finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkboxes);
        checkBoxesTable = findViewById(R.id.checkBoxesTable);
        finishButton = findViewById(R.id.resetButtom);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rootPanel = findViewById(R.id.boxesRootPanel);
        checkBoxesScroll = findViewById(R.id.checkBoxesScroll);
        Intent intent = getIntent();
        productsMapper = intent.getExtras().getParcelable("ProductsMapper");
        purchaseProductsList = productsMapper.getPurchasedProducts();


        for (ProductBucket pb : purchaseProductsList) {
            CheckBox checkBox = new CheckBox(this);
            TableRow row = new TableRow(this);
            checkBox.setText(pb.getProduct().getName() + " x" + pb.getUnits());
            checkBox.setTextSize(20);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((CheckBox) v).isChecked()) {
                        globalCounter++;
                        if (globalCounter == purchaseProductsList.size())
                            finishButton.setVisibility(View.VISIBLE);
                    } else globalCounter--;

                }
            });
            row.addView(checkBox);
            checkBoxesTable.addView(row);
        }
    }
}
