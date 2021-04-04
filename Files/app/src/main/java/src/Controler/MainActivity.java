package src.Controler;




import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.oneconomy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_main);
    }

    public void Prueba(View view) {

        EditText email = (EditText)findViewById(R.id.Email);
        String emailString = String.valueOf(email.getText());

        EditText password = (EditText)findViewById(R.id.password);
        String passwordString = String.valueOf(password.getText());

        signIn(emailString,passwordString);

    }


    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);

//        if (!validateForm()) {
//            return;
//        }

//        showProgressBar();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getBaseContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                           // updateUI(null);
                           // checkForMultiFactorFailure(task.getException());
                        }

//                        if (!task.isSuccessful()) {
  //                          mBinding.status.setText(R.string.auth_failed);
    //                    }
                       // hideProgressBar();
                    }
                });
    }





}
