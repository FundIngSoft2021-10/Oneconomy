package src.Controler;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oneconomy.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import src.Model.MensajeGenerico;

public class MovimientoManual extends AppCompatActivity {

    private static Gson gson = new Gson();
    private static boolean estado = false;
    private static MensajeGenerico mensajeGenerico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimiento_manual);
        try {
            this.recibirGET();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void recibirGET() throws InterruptedException {

        Thread thread = new Thread(new Runnable() {
            BufferedReader reader = null;
            @Override
            public void run() {
                try {

                    URL url = new URL("https://striped-weaver-309814.ue.r.appspot.com/ClienteGP");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    //conn.setDoInput(true);
                    InputStream stream = conn.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));
                    Gson gson = new Gson();
                    System.out.println("------------Lectura--------------" + reader.toString());
                    mensajeGenerico = gson.fromJson(reader, MensajeGenerico.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        thread.join();
    }
}