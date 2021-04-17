package src.Controler;

import com.example.oneconomy.R;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
    }

    public void Finanzas(View view) {
        Intent i = new Intent(this, MenuPrincipal.class);
        startActivity(i);
    }
}