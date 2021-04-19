package src.Libraries.FireBase;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import src.Controler.CrearCuenta;
import src.Controler.MainActivity;
import src.Controler.MenuPrincipal;
import src.Model.Cliente;

public class Utils {


    private static final String TAG = "EmailPassword";


    public static void SignIn(FirebaseAuth mAuth, String email, String password, Context context) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            //se muestra mensaje de SUCESS
                            Toast.makeText(context, "Authentication - sign in sucess.",
                                    Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(context, MenuPrincipal.class);
                            context.startActivity(i);

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());

                            //se muestra mensaje de FAILED
                            Toast.makeText(context, "Authentication failed.",
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

    public static void SignUp(FirebaseAuth mAuth, Cliente cliente, String password, Context context) {

        AuthResult result;
        mAuth.createUserWithEmailAndPassword(cliente.getEmail(), password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            //se muestra mensaje de SUCESS
                            Toast.makeText(context, "Registration - sign up sucess.",
                                    Toast.LENGTH_SHORT).show();
                            CrearCuenta.enviarPost(cliente);
                            Intent i = new Intent(context, MainActivity.class);
                            context.startActivity(i);
                            
                        } else {
                            // If sign up fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());


                            //CrearCuenta.verificarCodigoError(task, context);
                            Toast.makeText(context, task.getException().getMessage() , Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


}
