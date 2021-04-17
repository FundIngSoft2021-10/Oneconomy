package src.Controler;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oneconomy.R;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import src.Libraries.DatePickerFragment;
import src.Model.Cliente;

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
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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

        CheckBox terminos = (CheckBox) findViewById(R.id.acceptoterminos);

        EditText contrasena = (EditText) findViewById(R.id.campocontrasena);
        String contrasenaString = String.valueOf(contrasena.getText());

        EditText contrasena2 = (EditText) findViewById(R.id.campoconfirmarcontrasena);
        String contrasena2String = String.valueOf(contrasena2.getText());


        EditText correo = (EditText) findViewById(R.id.campocorreo);
        String correoString = String.valueOf(correo.getText());

        EditText nombre_usuario = (EditText) findViewById(R.id.Nombre_usuario);
        String nombre_usuarioString = String.valueOf(nombre_usuario.getText());

        EditText nombre = (EditText) findViewById(R.id.camponombre);
        String nombreString = String.valueOf(nombre.getText());

        EditText apellido = (EditText) findViewById(R.id.Apellido);
        String apellidoString = String.valueOf(apellido.getText());

        //aqui no va esta variable que le paso al final en la sigiente linea
        EditText fecha_seleccionada = (EditText) findViewById(R.id.fecha_seleccionada);
        String fecha_nacimientoString = String.valueOf(fecha_seleccionada.getText());


        EditText cedula = (EditText) findViewById(R.id.Cedula);
        String cedulaString = String.valueOf(cedula.getText());


        Context context = this;
        if (contrasenaString.compareTo(contrasena2String) != 0 || (contrasenaString.isEmpty() || contrasena2String.isEmpty()))
        {
            Toast.makeText(context, "No se pudo crear la cuenta - las contraseñas no coinciden o vacias",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {

            if (terminos.isChecked() == false) {
                error = true;
            }
            else
            {
                if (correoString.isEmpty() ||nombre_usuarioString.isEmpty() || nombreString.isEmpty() || apellidoString.isEmpty() || fecha_nacimientoString.isEmpty() || cedulaString.isEmpty() ) {
                    error = true;
                }
                else
                {
                    System.out.println("Intentando Crear...");
                    guardarCuenta(contrasenaString, correoString, nombreString,nombre_usuarioString,apellidoString,fecha_nacimientoString, cedulaString);
               }
            }
        }

        if (error == true)
        {
            Toast.makeText(context, "No se pudo crear la cuenta - intente de nuevo",
                    Toast.LENGTH_SHORT).show();
        }
    }


    private void guardarCuenta(String contrasenaString, String correoString, String nombreString, String nombre_usuarioString, String apellidoString, String fecha_nacimientoString, String cedulaString) throws IOException {

        String TAG = "displayname";

        Context context = this;

        Cliente cliente = new Cliente(correoString,nombre_usuarioString,nombreString,apellidoString,null,cedulaString);

        SignUp(mAuth, cliente, correoString, context);

        //new JsonTask().execute("https://striped-weaver-309814.ue.r.appspot.com/ClienteTest");
    }

    public void showDatePickerDialog(View view) {

        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                EditText fecha_seleccionada = (EditText) findViewById(R.id.fecha_seleccionada);
                fecha_seleccionada.setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static void enviarPost(Cliente nuevoCliente) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://striped-weaver-309814.ue.r.appspot.com/ClienteGP");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jCliente = new JSONObject();

                    jCliente.put("cliente", nuevoCliente);

                    Log.i("JSON", jCliente.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jCliente.toString());

                    os.flush();
                    os.close();

                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    /*

    private class JsonTask extends AsyncTask<String, String, JSONObject> {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected JSONObject doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return new JSONObject(buffer.toString());


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject response) {
            if(response != null)
            {
                //
            }
        }
    }*/

}


