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
import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import src.Controler.CrearCuenta;
import src.Controler.MainActivity;
import src.Controler.MenuPrincipal;
import src.Model.Cliente;

public class Utils {

    private static final String TAG = "EmailPassword";
    private static FirebaseUser user;
    private static boolean estado;
    private static Gson gson = new Gson();

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
                            user = mAuth.getCurrentUser();
                            //se muestra mensaje de SUCESS

                            Toast.makeText(context, "Registration - sign up sucess.",
                                    Toast.LENGTH_SHORT).show();
                            try {
                                if(enviarPost(cliente, "https://striped-weaver-309814.ue.r.appspot.com/ClienteGP")){
                                    //CrearCuenta.Alerta(context, "Su cuenta ha sido creada", "Ya puedes iniciar sesión");
                                    Toast.makeText(context, "Cuenta creada",
                                            Toast.LENGTH_SHORT).show();

                                    Intent i = new Intent(context, MainActivity.class);
                                    context.startActivity(i);
                                }
                                else{
                                    CrearCuenta.Alerta(context, "Error en el servidor", "Por favor intentelo más tarde");
                                    eliminarCuenta();
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

    public static void eliminarCuenta(){
        user.delete().addOnCompleteListener
                (new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            boolean bandera = false;

                            try {
                                bandera = enviarPost(user.getEmail().toString(),"https://striped-weaver-309814.ue.r.appspot.com/ClienteD");
                                Log.d(TAG, "User account deleted.");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            if (bandera == false)
                            {
                                //aqui deberia verificar o tomar las medidas necesarias qen caso de que la cunta sea eliminada de firebase pero no de la bse de datos por algun problema con el servidor
                            }
                        }
                    }
                });
    }

    public static boolean enviarPost(Object obj, String targetURL) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                estado = false;
                try {
                    URL url = new URL(targetURL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    String JsonString = gson.toJson(obj);

                    Log.i("JSON", JsonString);

                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());

                    os.writeBytes(JsonString);

                    os.flush();
                    os.close();

                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());

                    if(String.valueOf(conn.getResponseCode()).equals("200")){
                        estado = true;
                    }
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        thread.join();
        System.out.println("-----------ESTADO---------"+Boolean.toString(estado));
        return estado;

    }


}
