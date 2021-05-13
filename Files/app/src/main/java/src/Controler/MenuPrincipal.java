package src.Controler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oneconomy.R;

public class MenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
    }

    public void Finanzas(View view) {
        Intent i = new Intent(this, Finanzas.class);
        startActivity(i);
    }

    public void MetodosDePago(View view) {
        Intent i = new Intent(this, MetodosDePago.class);
        startActivity(i);
    }

    public void Config(View view) {
        Intent i = new Intent(this, Configuracion.class);
        startActivity(i);
    }


    public void mis_informes(View view) {
        Intent i = new Intent(this, Mis_Informes.class);
        startActivity(i);
    }
}