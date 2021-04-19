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
    private static FirebaseUser user;

    public static FirebaseUser getUser() {
        return user;
    }

    public static void SignIn(FirebaseAuth mAuth, String email, String password, Context context) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            user = mAuth.getCurrentUser();

                            //se muestra mensaje de SUCESS
                            Toast.makeText(context, "Authentication - sign in sucess.",
                                    Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(context, MenuPrincipal.class);
                            context.startActivity(i);

                            //updateUI(user);
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
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
                            try {
                                if(CrearCuenta.enviarPost(cliente) == true){
                                    //CrearCuenta.Alerta(context, "Su cuenta ha sido creada", "Ya puedes iniciar sesión");
                                    Toast.makeText(context, "Cuenta creada",
                                            Toast.LENGTH_SHORT).show();

                                    Intent i = new Intent(context, MainActivity.class);
                                    context.startActivity(i);
                                }
                                else{
                                    CrearCuenta.Alerta(context, "Error en el servidor", "Por favor intentelo más tarde");

                                    user = mAuth.getCurrentUser();
                                    user.delete().addOnCompleteListener
                                            (new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d(TAG, "User account deleted.");
                                                    }
                                                }
                                            });
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();

                            }

                        } else {
                            // If sign up fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            CrearCuenta.verificarCodigoError(task, context);
                        }
                    }
                });
    }


}
