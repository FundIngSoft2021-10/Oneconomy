package src.Controler;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oneconomy.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import java.util.Collection;

import src.Libraries.FireBase.Utils;

public class Perfil extends AppCompatActivity {


    private static Gson gson = new Gson();
    private static ArrayList<String> resultadosPerfil;
    private static ArrayList<ArrayList<String>> listOListsPerfil = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        try {
            recibirGET_Perfil();
            TextView nombreUsuario = (TextView) findViewById(R.id.textoNombreUsuario);
            TextView nombre = (TextView) findViewById(R.id.textoNombre);
            TextView apellido = (TextView) findViewById(R.id.textoApellido);
            TextView cedula = (TextView) findViewById(R.id.textoCedula);
            TextView fecha = (TextView) findViewById(R.id.textoFechaN);
            TextView correo = (TextView) findViewById(R.id.textoCorreo);

            correo.setText(listOListsPerfil.get(0).get(0));
            nombreUsuario.setText(listOListsPerfil.get(0).get(1));
            nombre.setText(listOListsPerfil.get(0).get(2));
            apellido.setText(listOListsPerfil.get(0).get(3));
            fecha.setText(listOListsPerfil.get(0).get(4));
            cedula.setText(listOListsPerfil.get(0).get(5));

        } catch (InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(this , "Error accediendo a la BD",
                    Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, Configuracion.class);
            this.startActivity(i);
        }
    }


    public static void recibirGET_Perfil() throws InterruptedException {


        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    resultadosPerfil = new ArrayList<>();
                    listOListsPerfil = new ArrayList<>();
                    StringBuilder resultado = new StringBuilder();

                    String tempURL = "https://striped-weaver-309814.ue.r.appspot.com/ClienteGP?CC=" + Utils.getUser().getEmail() ;
                    URL url = new URL(tempURL);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");


                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String linea;
                    // Mientras el BufferedReader se pueda leer, agregar contenido a resultado
                    while ((linea = rd.readLine()) != null) {
                        resultado.append(linea);
                        System.out.println("------------Lectura_recibidatest--------------F" + linea + "___\n");
                    }
                    // Cerrar el BufferedReader
                    rd.close();

                    Type collectionType = new TypeToken<Collection<ArrayList>>(){}.getType();
                    Collection<ArrayList> CollectionString = gson.fromJson(java.lang.String.valueOf(resultado), collectionType);

                    //CollectionString guarda todos los valores de un lista, dentro de otra lista

                    if( CollectionString.isEmpty() == false )
                    {
                        for (ArrayList Temp : CollectionString)
                        {

                            resultadosPerfil = new ArrayList<>();
                            //el primer parametro es quien deberia guardar el ID del metodo de pago
                            String email = (String) Temp.get(0);
                            String nombre_usuario = (String) Temp.get(1);
                            String nombre = (String) Temp.get(2);
                            String apellido = (String) Temp.get(3);
                            String fecha = (String) Temp.get(4);
                            String cedula = (String) Temp.get(5);



                            //resultados guarda 2 valores (el ID y el codigo que deberia ser el nombre del metodo de pago) por cada lista que tengo dentro de CollectionString
                            resultadosPerfil.add(email);
                            resultadosPerfil.add(nombre_usuario);
                            resultadosPerfil.add(nombre);
                            resultadosPerfil.add(apellido);
                            resultadosPerfil.add(fecha);
                            resultadosPerfil.add(cedula);

                            listOListsPerfil.add(resultadosPerfil);

                            System.out.println("---lectura primer parametro:___" +email+ "___segundo parametro___" + nombre_usuario +"\n" );
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        thread.join();
    }
}

