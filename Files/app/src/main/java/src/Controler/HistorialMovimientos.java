package src.Controler;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.oneconomy.R;
import java.util.ArrayList;

import src.Libraries.Utils;

public class HistorialMovimientos extends AppCompatActivity {

    private TableLayout tableLayout;
    private String[] header = {"Fecha","Valor","Descripcion"};
    private static ArrayList<ArrayList<String>> listOListsMovimiento;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimientos);
        try {
            Utils.recibirGET_movimientos();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        listOListsMovimiento = Utils.getListOListsMovimiento();
        tableLayout = (TableLayout)findViewById(R.id.tablaMovimientos);
        loadTable();
    }

    private void loadTable(){

        tableLayout.setColumnShrinkable(0, true);
        tableLayout.setColumnShrinkable(1, true);
        tableLayout.setColumnShrinkable(2, true);
        tableLayout.removeAllViews();
        int text_size = 20;

        TextView col1 = new TextView(this);
        col1.setGravity(Gravity.CENTER);
        col1.setTypeface(null, Typeface.BOLD);
        TextView col2 = new TextView(this);
        col2.setGravity(Gravity.CENTER);
        col2.setTypeface(null, Typeface.BOLD);
        TextView col3 = new TextView(this);
        col3.setGravity(Gravity.CENTER);
        col3.setTypeface(null, Typeface.BOLD);

        TableRow titulos = new TableRow(this);
        titulos.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        col1.setText(header[0]);
        col1.setTextSize(text_size);
        titulos.addView(col1);
        col2.setText(header[1]);
        col2.setTextSize(text_size);
        titulos.addView(col2);
        col3.setText(header[2]);
        col3.setTextSize(text_size);
        titulos.addView(col3);
        tableLayout.addView(titulos);

        for (int i = 0; i < listOListsMovimiento.size(); i++) {
                TextView texto1 = new TextView(this);
                texto1.setGravity(Gravity.CENTER);
                texto1.setTextSize(text_size);
                texto1.setSingleLine(false);
                TextView texto2 = new TextView(this);
                texto2.setGravity(Gravity.CENTER);
                texto2.setTextSize(text_size);
                texto2.setSingleLine(false);
                TextView texto3 = new TextView(this);
                texto3.setGravity(Gravity.CENTER);
                texto3.setTextSize(text_size);
                texto3.setSingleLine(false);
                texto3.setMaxWidth(30);
                TableRow row = new TableRow(this);
                row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                texto1.setText(listOListsMovimiento.get(i).get(2));
                row.addView(texto1);
                texto2.setText(listOListsMovimiento.get(i).get(1));
                row.addView(texto2);
                texto3.setText(listOListsMovimiento.get(i).get(3));
                row.addView(texto3);
                tableLayout.addView(row);
        }

    }
}
