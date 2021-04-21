package src.Controler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oneconomy.R;

public class MetodosDePago extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metodos_de_pago);

        //Cargar la lista de metodos de pago en la listview metodos

    }

    public void agregarMetodo(View view) {
        Intent i = new Intent(this, AgregarMetodoDePago.class);
        startActivity(i);
    }

    public void eliminarMetodo(View view) {

    }



}