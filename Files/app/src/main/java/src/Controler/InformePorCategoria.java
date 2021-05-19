package src.Controler;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oneconomy.R;

import java.util.ArrayList;

public class InformePorCategoria extends AppCompatActivity {

    private static ArrayList<ArrayList<String>> listOListsCategoria;
    private static ArrayList<ArrayList<String>> listOListsMovimiento;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listOListsMovimiento = Mis_Informes.getListOListsMovimiento();
        try {
            src.Libraries.Utils.recibirGET_Categoria();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        listOListsCategoria = src.Libraries.Utils.getListOListsCategoria();
        setContentView(R.layout.activity_informes_por_categoria);

        Spinner c = (Spinner) findViewById(R.id.spinnerCategoria);
        ArrayList<String> opciones2 = new ArrayList<>();
        opciones2.add(" ---- ");
        for (ArrayList<String> actual : listOListsCategoria) {
            opciones2.add(actual.get(1));
            System.out.println("*********" + actual.get(0) + ":::" + actual.get(1) + "***********");
        }

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        c.setAdapter(adapter2);
        c.setSelection(0);

        setTable("--");
    }

    private void setTable(String id)
    {
        TableLayout tabla = (TableLayout) findViewById(R.id.tablaCategorias);
        TableRow row = new TableRow(this);
        TextView texto = new TextView(this);

        //fecha valor descripcion
        for(int i = 0; i < listOListsCategoria.size(); i++)
        {
            texto.setText(listOListsMovimiento.get(i).get(2));
            row.addView(texto);
            texto.setText(listOListsMovimiento.get(i).get(1));
            row.addView(texto);
            texto.setText(listOListsMovimiento.get(i).get(3));
            row.addView(texto);
            tabla.addView(row);
        }
    }
}
