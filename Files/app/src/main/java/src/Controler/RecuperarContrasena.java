package src.Controler;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.oneconomy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RecuperarContrasena extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contrasena);
    }

    public void enviarCorreoRecuperacion(View view){
        EditText correo = (EditText) findViewById(R.id.campocorreo);
        mAuth = FirebaseAuth.getInstance();
        String emailAddress = String.valueOf(correo.getText());
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        if(emailAddress.isEmpty()){
            Toast.makeText(context, "Fallo en envio - Campos Vacios.", Toast.LENGTH_SHORT).show();
        }else{
            mAuth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Email sent.");
                                CharSequence text = "Correo de recuperación enviado";
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                finish();
                            }else{
                                CharSequence text = "Fallo al enviar correo de recuperación";
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            }
                        }
                    });
        }
    }

}