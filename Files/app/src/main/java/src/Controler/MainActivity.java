package src.Controler;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oneconomy.R;
import com.google.firebase.auth.FirebaseAuth;

import src.Libraries.FireBase.Utils;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //eliminar focus de primer texto mediante la siguiente linea
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.login_activity);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.login_activity);
    }

    public void Prueba(View view) {

        EditText email = (EditText) findViewById(R.id.Email);
        String emailString = String.valueOf(email.getText());

        EditText password = (EditText) findViewById(R.id.password);
        String passwordString = String.valueOf(password.getText());

        //que string nulos no son admitidos;
        if (emailString.isEmpty() || passwordString.isEmpty()) {
            Context context = this;
            Toast.makeText(context, "Authentication failed - Campos Vacios.", Toast.LENGTH_SHORT).show();
        } else {
            signIn(emailString, passwordString);
            MenuPrincipal(view);
        }

    }

    public void MenuPrincipal(View view){
        Intent i = new Intent(this, MenuPrincipal.class);
        startActivity(i);
    }

    public void CrearCuenta(View view) {

        Intent i = new Intent(this, CrearCuenta.class);
        startActivity(i);
    }

    public void RecuperarContrasena(View view){
        Intent i = new Intent(this, RecuperarContrasena.class);
        startActivity(i);
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);

//        if (!validateForm()) {
//            return;
//        }
        Context context = this;
//        showProgressBar();


        Utils.SignIn(mAuth, email, password, context);
    }


}
