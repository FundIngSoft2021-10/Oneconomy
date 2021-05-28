package src.Controler;

import androidx.appcompat.app.AppCompatActivity;
import com.example.oneconomy.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

import src.Libraries.FireBase.Utils;
import src.Model.Categoria;

public class CrearCategoria extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_categoria);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void crearCategoria(View view) throws InterruptedException {
        Categoria nuevaCategoria = new Categoria();
        EditText nombreCategoria = (EditText)findViewById(R.id.editTextNameCat);
        EditText descripcion = (EditText)findViewById(R.id.editTextDesCat);
        String email = Utils.getUser().getEmail();
        if(nombreCategoria.getText().toString().isEmpty()){
            CrearCuenta.Alerta(view.getContext(), "Error al agregar categoria", "\n+ Existen parametros sin llenar");
        }else{
            Random random = new Random();
            nuevaCategoria.setID(random.nextInt(99999999));
            nuevaCategoria.setNombre(nombreCategoria.getText().toString());
            nuevaCategoria.setDescripcion(descripcion.getText().toString());
            nuevaCategoria.setPerfilEmail(email);
            if (Utils.enviarPost(nuevaCategoria, "https://striped-weaver-309814.ue.r.appspot.com//CategoriaGet?catP=")) {
                Toast.makeText(view.getContext(), "Categoría agregada correctamente",
                        Toast.LENGTH_SHORT).show();
                Intent i = new Intent(view.getContext(), Finanzas.class);
                view.getContext().startActivity(i);
            } else {
                CrearCuenta.Alerta(view.getContext(), "Error al añadir categoría", "\nPor favor intente más tarde");
            }
        }
    }

}