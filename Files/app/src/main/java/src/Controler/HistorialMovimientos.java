package src.Controler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oneconomy.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HistorialMovimientos extends AppCompatActivity {
    private TableLayout tableLayout;
    private String[] header = {"Fecha","Valor","Descripcion"};
    private ArrayList<String[]> rows= new ArrayList<>();


    protected void onCreate(Bundle savedInstanceState) {
        super. onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimientos);

        tableLayout = (TableLayout)findViewById(R.id.tablaMovimientos);
        TableDynamic tableDynamic = new TableDynamic(tableLayout,getApplicationContext());
        tableDynamic.addHeader(header);
        try{
            tableDynamic.addData(getMovements());
        }catch (Exception e){
            System.out.println(e.toString());
        }

    }

    private ArrayList<String[]> getMovements() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
            .url("localhost:4200/movements/get/credito/all")
            .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.body().string());
        }

        StringTokenizer st = new StringTokenizer("hola,mundo|estoy,hablando","|");
        while (st.hasMoreTokens()) {
            StringTokenizer nst = new StringTokenizer(st.nextToken(),",");
            while (nst.hasMoreTokens()) {
                System.out.println(nst.nextToken());
            }
            System.out.println("-----------------------------------------");
        }

        rows.add(new String[]{"Ene 15 2021","15.000","PAGO EPREPAGO"});
        rows.add(new String[]{"Ene 17 2021","-24.000","RECARGA EPREPAGO"});
        rows.add(new String[]{"Feb 04 2021","-600.000","PAGO ZINOBE"});
        return rows;
    }
}
