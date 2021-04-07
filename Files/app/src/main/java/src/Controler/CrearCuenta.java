package src.Controler;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import com.example.oneconomy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import src.librariesExternal.FireBase.Utils;

public class CrearCuenta extends AppCompatActivity {

    private FirebaseAuth mAuth;

    public static void verificarCodigoError(Task<AuthResult> task, Context context) {

        switch (task.getException().getMessage()) {
            case "auth/email-already-in-use":

                Toast.makeText(context, "El email ya está en uso",
                        Toast.LENGTH_LONG).show();

            break;
            case "auth/invalid-email":
                //
            break;
            case "auth/operation-not-allowed":
                //
                break;
            case "auth/weak-password":
                //
                break;
            case "auth/timeout":
                //
                break;
            default:
                //
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.signin_activity);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.signin_activity);
    }

    public void crearCuenta(View view) {

        boolean error = false;

        CheckBox terminos = (CheckBox) findViewById(R.id.checkBox);

        EditText nombre = (EditText) findViewById(R.id.camponombre);
        String nombreString = String.valueOf(nombre.getText());

        EditText contrasena = (EditText) findViewById(R.id.campocontrasena);
        String contrasenaString = String.valueOf(contrasena.getText());

        EditText contrasena2 = (EditText) findViewById(R.id.campoconfirmarcontrasena);
        String contrasena2String = String.valueOf(contrasena2.getText());

        EditText correo = (EditText) findViewById(R.id.campocorreo);
        String correoString = String.valueOf(correo.getText());


        if (contrasenaString.compareTo(contrasena2String) != 0) {
            error = true;

        }
        if (terminos != null) {
            error = true;
        }

        if (error) {

            Context context = this;
            Toast.makeText(context, "No se pudo crear la cuenta - las contraseñas no coinciden",
                    Toast.LENGTH_SHORT).show();

        } else {
            System.out.println("Intentando Crear...");
            guardarCuenta(contrasenaString, correoString, nombreString);
        }

    }

    private void guardarCuenta(String contrasena, String correo, String nombre) {

        String TAG = "displayname";
        Context context = this;

        System.out.println(contrasena);
        System.out.println(correo);
        System.out.println(nombre);
        Utils.SignUp(mAuth, correo, contrasena, context);
        System.out.println("2");

        /*
        SQL
        */


    }

}
