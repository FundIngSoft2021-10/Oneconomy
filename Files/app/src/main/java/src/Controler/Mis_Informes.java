package src.Controler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oneconomy.R;
import com.google.gson.Gson;

import src.Controler.Graficas.MisGraficas;
import src.Libraries.Utils;

public class Mis_Informes extends AppCompatActivity {

    private static Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Utils.recibirGET_movimientos();
            Utils.recibirGET_Categoria();
            Utils.recibirGET_MetodoPago();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_mis__informes);
    }

    public void InformeMes(View view) {
        Intent i = new Intent(this, InformsActualMonth.class);
        startActivity(i);
    }

    public void InformeEscrito(View view) {
            /*
            Intent i = new Intent(this, InformeEscrito.class);
            startActivity(i);
            */
    }

    public void MisGraficas(View view) {
        Intent i = new Intent(this, MisGraficas.class);
        startActivity(i);
    }

    public void InformeCategoria(View view) {

        Intent i = new Intent(this, InformePorCategoria.class);
        startActivity(i);

    }

    public void InformeMetodoDePago(View view) {

        Intent i = new Intent(this, InformePorMetodo.class);
        startActivity(i);

    }

    public static void recibirGET_Categoria() throws InterruptedException {
        src.Libraries.Utils.recibirGET_Categoria();
    }
}
