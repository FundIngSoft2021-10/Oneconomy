package src.Controler;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oneconomy.R;
import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static src.Libraries.FireBase.Utils.SignUp;

public class CrearCuenta extends AppCompatActivity {

    private FirebaseAuth mAuth;


    /*
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
    */


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

    public void crearCuenta(View view) throws IOException {

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

    private void guardarCuenta(String contrasena, String correo, String nombre) throws IOException {

        String TAG = "displayname";

        Context context = this;

        System.out.println(contrasena);
        System.out.println(correo);
        System.out.println(nombre);
        SignUp(mAuth, correo, contrasena, context);


        Thread thread = new Thread(() -> {
            try  {

                String link = "https://striped-weaver-309814.ue.r.appspot.com/";

                URL url = new URL(link);

                //String data  = URLEncoder.encode("username", "UTF-8")+ "=" + URLEncoder.encode(username, "UTF-8");
                //data += "&" + URLEncoder.encode("password", "UTF-8")+ "=" + URLEncoder.encode(password, "UTF-8");

                URLConnection conn = url.openConnection();
                System.out.println("URLConnection");

                //OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                //wr.write( data );

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                System.out.println("BufferedReader");

                String line;

                StringBuilder sb = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                }

                System.out.println(sb);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread.start();
    }



}
