package src.Controler;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oneconomy.R;

import java.util.ArrayList;

import src.Libraries.Utils;

public class InformePorMetodo extends AppCompatActivity {

    private static ArrayList<ArrayList<String>> listOListsMetodos;
    private static ArrayList<ArrayList<String>> listOListsMovimiento;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listOListsMovimiento = Utils.getListOListsMovimiento();
        listOListsMetodos = Utils.getListOListsMetodos_Pago();

        setContentView(R.layout.activity_informes_por_metodo);
        Spinner c = (Spinner) findViewById(R.id.spinnerMetodos);

        ArrayList<String> opciones2 = new ArrayList<>();
        opciones2.add(" ---- ");
        for (ArrayList<String> actual : listOListsMetodos) {
            opciones2.add(actual.get(1));
            System.out.println("*********" + actual.get(0) + ":::" + actual.get(1) + "***********");
        }

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.spinner_item, opciones2);
        adapter2.setDropDownViewResource(R.layout.spinner_item);
        c.setAdapter(adapter2);
        c.setSelection(0);
        c.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> spn,
                                               android.view.View v,
                                               int posicion,
                                               long id) {
                        for(ArrayList<String> actual : listOListsMetodos)
                        {
                            if(c.getSelectedItem().toString().equals(actual.get(1)))
                            {
                                setTable(actual.get(0));
                                break;
                            }
                        }
                    }

                    public void onNothingSelected(AdapterView<?> spn) {
                    }
                });
        setTable("---");
    }

    private void setTable(String id) {
        TableLayout tabla = (TableLayout) findViewById(R.id.tablaMetodos);
        tabla.setColumnShrinkable(0, true);
        tabla.setColumnShrinkable(1, true);
        tabla.setColumnShrinkable(2, true);
        tabla.removeAllViews();
        int text_size = 20;
        //fecha valor descripcion
        System.out.println("----setTable-----");
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

        col1.setText("FECHA");
        col1.setTextSize(text_size);
        titulos.addView(col1);
        col2.setText("VALOR");
        col2.setTextSize(text_size);
        titulos.addView(col2);
        col3.setText("DESCRIPCION");
        col3.setTextSize(text_size);
        titulos.addView(col3);
        tabla.addView(titulos);

        for (int i = 0; i < listOListsMovimiento.size(); i++) {
            if (listOListsMovimiento.get(i).get(5).equals(id)) {
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
                tabla.addView(row);
            }
        }
    }

}