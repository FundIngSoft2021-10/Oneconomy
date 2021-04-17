package src.Controler;

import com.example.oneconomy.R;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Finanzas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finanzas);
    }

    public void MovimientoManual(View view) {
        Intent i = new Intent(this, MovimientoManual.class);
        startActivity(i);
    }
}