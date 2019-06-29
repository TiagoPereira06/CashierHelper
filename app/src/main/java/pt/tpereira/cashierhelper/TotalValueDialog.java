package pt.tpereira.cashierhelper;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class TotalValueDialog extends AppCompatDialogFragment {
    private EditText editTotalValue;
    private EditText editName;
    private EditText editQuantity;
    private TotalValueDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view)
                .setTitle("Add Product")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String price = editTotalValue.getText().toString();
                        String name = editName.getText().toString();
                        String quanty = editQuantity.getText().toString();
                        listener.applyValue(name, price, quanty);

                    }
                });

        editTotalValue = view.findViewById(R.id.editTotalValue);
        editName = view.findViewById(R.id.editName);
        editQuantity = view.findViewById(R.id.editQuantity);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (TotalValueDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Error");
        }
    }

    public interface TotalValueDialogListener {
        void applyValue(String name, String price, String units);
    }
}
