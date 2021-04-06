package src.Controler;




import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
        super.onCreate(savedInstanceState);


        conection.CONN();


        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.login_activity);


    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.login_activity);
    }

    public void Prueba(View view) {

        EditText email = (EditText)findViewById(R.id.Email);
        String emailString = String.valueOf(email.getText());

        EditText password = (EditText)findViewById(R.id.password);
        String passwordString = String.valueOf(password.getText());

        //que string nulos no son admitidos;
        if(emailString.isEmpty() || passwordString.isEmpty() )
        {
            Context context = this;
            Toast.makeText(context, "Authentication failed - Campos Vacios.",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            signIn(emailString,passwordString);
        }

    }


    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);

//        if (!validateForm()) {
//            return;
//        }
        Context context = this;
//        showProgressBar();


        Utils.SignIn(mAuth,email,password,context);
    }





}
