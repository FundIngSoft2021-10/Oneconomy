package src.Controler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.oneconomy.R;

public class Perfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        TextView nombre = (TextView) findViewById(R.id.textoNombre);
        TextView nombreUsuario = (TextView) findViewById(R.id.textoNombreUsuario);


        nombre.setText("prueba");
        nombreUsuario.setText("prueba");

    }


    
}