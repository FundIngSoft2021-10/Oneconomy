package src.Controler;

import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oneconomy.R;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.DecimalFormat;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import src.Libraries.FireBase.Utils;

public class InformsActualMonth extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informe_mes);


        llenarDatos();
    }
    private void setData(String data){
        JsonParser parser = new JsonParser();
        JsonObject json = (JsonObject) parser.parse(data);

        TextView costo = (TextView) findViewById(R.id.costo);
        TextView categoria = (TextView) findViewById(R.id.categoria);
        TextView consejo = (TextView) findViewById(R.id.consejo);

        String category=json.get("categoria").toString();
        String value =setDotsDigit(Double.parseDouble(json.get("valor").toString().substring(1)));
        String advice = getAdvice(category.substring(1,category.length()-1));

        categoria.setText(category.substring(1,category.length()-1));
        costo.setText(value);
        consejo.setText(advice);

    }

    private String getAdvice(String category){
        switch(category){
            case "General":
                return "Estas seguro que gastar en \n cualquier cosa esta bien?";
            case "Ingresos":
                return "Reporta un bug esto no deberia \n aparecer en gastos!";
            case "Hogar":
                return "Invertir en el hogar es bueno \n pero no tan seguido";
            case "Ocio":
                return "La diversion sana y costosa \n no siempre te deja felicidad";
            case "Facturas":
                return "Las deudas son sagradas, pero \n pagarlas no es \n placentero para tu billetera";
            default:
                return "No tengo ningún consejo :c";
        }

    }
    public static String setDotsDigit(double value){
        return new DecimalFormat("###,###,###.##").format(value);
    }

    private void llenarDatos(){
        Thread thread=new Thread(new Runnable() {
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://18.219.21.101:4200/movements/get/max/"+ Utils.getUser().getEmail())
                        .build();
                try(Response response = client.newCall(request).execute()){
                    setData(response.body().string());
                }catch (Exception e){
                    System.out.println("ERROR TRAYENDO DATOS DE LA BASE DE DATOS");
                    System.out.println(e.toString());
                }
            }
        });
        thread.start();
/*        try {
            thread.join(10000);
            if (thread.isAlive()) {
                System.out.println("Not finished");
            } else {
                loadTable();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}
