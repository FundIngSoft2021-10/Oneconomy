package src.Controler;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.oneconomy.R;

import src.Libraries.FireBase.Utils;

public class Configuracion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
    }

    public void borrarCuenta(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("¿Desea borrar su cuenta?");
        builder.setMessage("Si elimina su cuenta, se perderán todos sus datos");
        builder.setCancelable(false);

        builder.setPositiveButton("ELIMINAR",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Utils.eliminarCuenta();

                        Toast.makeText(view.getContext(), "Cuenta Eliminada", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(view.getContext(), MainActivity.class);
                        startActivity(i);
                    }
                });

        builder.setNegativeButton("CANCELAR",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //No pasa nada
                    }
                });



        AlertDialog dialog = builder.create();
        dialog.show();

    }

}