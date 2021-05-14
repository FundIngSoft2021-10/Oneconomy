package src.Controler.Graficas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oneconomy.R;

import src.Controler.HistorialMovimientos;

public class MisGraficas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_graficas);
    }

    public void graficaPastel(View view) {
        Intent i = new Intent(this, GraficaPastel.class);
        startActivity(i);
    }

}