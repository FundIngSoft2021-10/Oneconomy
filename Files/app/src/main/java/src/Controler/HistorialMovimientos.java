package src.Controler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
    TableDynamic tableDynamic;


    private View.OnClickListener generarTabla = new View.OnClickListener() {
        public void onClick(View v) {
                Thread thread=new Thread(new Runnable() {
                    public void run() {
                        Spinner dropdownMethod = (Spinner)findViewById(R.id.methodSelector);
                        Spinner dropdownBank = (Spinner)findViewById(R.id.bankSelector);

                        String method = dropdownMethod.getSelectedItem().toString();
                        String bank = dropdownBank.getSelectedItem().toString();

                        if(method.compareTo("Método pago")==0){
                            method="all";
                        }
                        if(bank.compareTo("Banco")==0){
                            bank="all";
                        }
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url("http://3.129.204.152:4200/movements/get/"+method+"/"+bank)
                                .build();
                        try(Response response = client.newCall(request).execute()){
                            setMovements(response.body().string());
                        }catch (Exception e){
                            System.out.println("ERROR");
                            System.out.println(e.toString());
                        }
                    }
                });
                thread.start();
                try {
                    thread.join(10000);
                    if (thread.isAlive()) {
                        System.out.println("Not finished");
                    } else {
                        loadTable();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super. onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimientos);

        Button button = (Button)findViewById(R.id.btnSearch);
        button.setOnClickListener(generarTabla);

        tableLayout = (TableLayout)findViewById(R.id.tablaMovimientos);
        tableDynamic = new TableDynamic(tableLayout,getApplicationContext());


        Spinner dropdownMethod = findViewById(R.id.methodSelector);
        Spinner dropdownBank = findViewById(R.id.bankSelector);
        String[] itemsMethod = new String[]{"Método pago","debito", "credito"};
        String[] itemsBank = new String[]{"Banco","bancolombia", "colpatria"};
        ArrayAdapter<String> adapterMethod = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsMethod);
        ArrayAdapter<String> adapterBank = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsBank);
        dropdownMethod.setAdapter(adapterMethod);
        dropdownBank.setAdapter(adapterBank);

    }
    private void loadTable(){
        tableDynamic.addData(rows,header);
    }

    private void setMovements(String data) {
        rows.removeAll(rows);
        System.out.println(data);
        StringTokenizer st = new StringTokenizer(data,"|");
        while (st.hasMoreTokens()) {
            String movement=st.nextToken();
            String[]dataSplited=movement.split(",");
            rows.add(new String[]{dataSplited[2],dataSplited[1],dataSplited[3].substring(0,12)+"..."});
            System.out.println(rows.size());
        }

    }
}
