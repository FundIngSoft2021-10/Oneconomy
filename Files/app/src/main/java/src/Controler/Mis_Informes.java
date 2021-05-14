    package src.Controler;

    import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oneconomy.R;

import src.Controler.Graficas.MisGraficas;

public class Mis_Informes extends AppCompatActivity {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mis__informes);

        }

        public void MisGraficas(View view) {
            Intent i = new Intent(this, MisGraficas.class);
            startActivity(i);
        }

        //aqui iria la funcion del llamado get a movimientos en el servidor
    }git add F
