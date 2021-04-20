package src.Controler;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Scroller;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.oneconomy.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import src.Libraries.DatePickerFragment;
import src.Model.Cliente;
import static src.Libraries.FireBase.Utils.SignUp;

public class CrearCuenta extends AppCompatActivity {

    private static final String TAG = "FF";
    private FirebaseAuth mAuth;
    private static Gson gson = new Gson();
    private static boolean estado = false;

    public static void verificarCodigoError(Task<AuthResult> task, Context context) {
        switch (task.getException().getMessage()) {
            case "The email address is already in use by another account.":
                Alerta(context, "No se pudo crear la cuenta", "+ El email ya está en uso");
            break;
             case "The email address is badly formatted.":
                 Alerta(context, "No se pudo crear la cuenta", "+ El email tiene un formato incorrecto");
            break;
        }
    }

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

    public void mostrarTerminos(View view){
        String text = getString(R.string.terminos);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Terminos y condiciones")
                .setMessage(text)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
        TextView textView = (TextView) dialog.findViewById(android.R.id.message);
        textView.setScroller(new Scroller(this));
        textView.setVerticalScrollBarEnabled(true);
        textView.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
        textView.setMovementMethod(new ScrollingMovementMethod());

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void crearCuenta(View view) throws IOException, ParseException {
        boolean error = false;
        String mensaje = "";

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

        EditText fecha_seleccionada = (EditText) findViewById(R.id.fecha_seleccionada);
        String fecha_nacimientoString = String.valueOf(fecha_seleccionada.getText());

        EditText cedula = (EditText) findViewById(R.id.Cedula);
        String cedulaString = String.valueOf(cedula.getText());

        Context context = this;
        if (contrasenaString.compareTo(contrasena2String) != 0)
        {
            mensaje = mensaje + "+ Las contraseñas no coinciden\n";
            error = true;
        }
        if(correoString.isEmpty() ||nombre_usuarioString.isEmpty() || nombreString.isEmpty() || apellidoString.isEmpty() || fecha_nacimientoString.isEmpty() || cedulaString.isEmpty() || contrasena2String.isEmpty() || contrasenaString.isEmpty()){
            mensaje = mensaje + "+ Existen campos sin completar\n";
            error = true;
        }
        if((!contrasena2String.isEmpty() && (contrasena2String.length()<6)) || (!contrasenaString.isEmpty() && (contrasenaString.length()<6))){
            mensaje = mensaje + "+ La contraseña debe tener más de 6 caracteres\n";
            error = true;
        }
        if(terminos.isChecked() == false){
            mensaje = mensaje + "+ Debes aceptar los terminos y condiciones\n";
            error = true;
        }

        if (error == false)
        {
            System.out.println("Intentando Crear...");
            guardarCuenta(contrasenaString, correoString, nombreString,nombre_usuarioString,apellidoString,fecha_nacimientoString, cedulaString);
        }
        else{
            mensaje = mensaje.substring(0, mensaje.length() - 1);
            Alerta(context,"No se pudo crear la cuenta", mensaje);
        }
    }

    public static void Alerta(Context context, String titulo, String cuerpo){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(titulo);
        builder.setMessage(cuerpo);
        builder.setCancelable(true);

        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void guardarCuenta(String contrasenaString, String correoString, String nombreString, String nombre_usuarioString, String apellidoString, String fecha_nacimientoString, String cedulaString) throws IOException, ParseException {
        String TAG = "displayname";
        Context context = this;
        Date date = null;
        date=new SimpleDateFormat("dd/MM/yyyy").parse(fecha_nacimientoString);

        //la fecha de nacimiento no puede ser null
        Cliente cliente = new Cliente(correoString,nombre_usuarioString,nombreString,apellidoString,date,cedulaString);

        //esto es super importante y descomentar al final
        SignUp(mAuth, cliente, contrasenaString, context);

        //new JsonTask().execute("https://striped-weaver-309814.ue.r.appspot.com/ClienteTest");
    }

    public void showDatePickerDialog(View view) {

        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + "/" + (month+1) + "/" + year;
                EditText fecha_seleccionada = (EditText) findViewById(R.id.fecha_seleccionada);
                fecha_seleccionada.setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
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


               v

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
    }
    */

}


