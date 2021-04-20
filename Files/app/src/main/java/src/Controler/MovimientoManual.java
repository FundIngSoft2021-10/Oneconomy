<<<<<<< HEAD
package src.Controler;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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

import src.Model.Campos;
import src.Model.Cliente;
import src.Model.MensajeGenerico;


public class MovimientoManual extends AppCompatActivity {

    private static Gson gson = new Gson();
    private static boolean estado = false;
    private static MensajeGenerico mensajeGenerico;
    private static Cliente cliente;
    private static Campos campos;
    private static ArrayList<String> resultados;
    private static ArrayList<ArrayList<String>> listOLists = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimiento_manual);
        try {

            this.recibirGET();
            Spinner s = (Spinner) findViewById(R.id.Desplegable_Metodo_Pago);
            ArrayList<String> opciones = new ArrayList<>();

            for(ArrayList<String> actual : listOLists){
                opciones.add(actual.get(1));
                System.out.println("*********" + actual.get(0) + ":::" + actual.get(1) + "***********");
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s.setAdapter(adapter);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void recibirGET() throws InterruptedException {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    resultados = new ArrayList<>();
                    listOLists = new ArrayList<>();
                    StringBuilder resultado = new StringBuilder();

                    String tempURL = "https://striped-weaver-309814.ue.r.appspot.com/Metodo_De_Pago?HolaE=" + "dominer340@gmail.com";
                    URL url = new URL(tempURL);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");


                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String linea;
                    // Mientras el BufferedReader se pueda leer, agregar contenido a resultado
                    while ((linea = rd.readLine()) != null) {
                        resultado.append(linea);
                        System.out.println("------------Lectura_recibidatest--------------F" + linea + "Fdominer---dominer340@gmail.\n");
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
                            resultados = new ArrayList<>();
                            //el primer parametro es quien deberia guardar el ID del metodo de pago
                            String MP_id = (String) Temp.get(0);
                            String MP_Codigo = (String) Temp.get(1);

                            //resultados guarda 2 valores (el ID y el codigo que deberia ser el nombre del metodo de pago) por cada lista que tengo dentro de CollectionString
                            resultados.add(MP_id);
                            resultados.add(MP_Codigo);
                            listOLists.add(resultados);

                            System.out.println("---lectura primer parametro:___" +MP_id+ "___segundo parametro___" + MP_Codigo +"\n" );
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
=======
package src.Controler;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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

import src.Model.Campos;
import src.Model.Cliente;
import src.Model.MensajeGenerico;


public class MovimientoManual extends AppCompatActivity {

    private static Gson gson = new Gson();
    private static boolean estado = false;
    private static MensajeGenerico mensajeGenerico;
    private static Cliente cliente;
    private static Campos campos;
    private static ArrayList<String> resultados;
    private static ArrayList<ArrayList<String>> listOLists = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimiento_manual);
        try {

            this.recibirGET();
            Spinner s = (Spinner) findViewById(R.id.Desplegable_Metodo_Pago);
            ArrayList<String> opciones = new ArrayList<>();

            for(ArrayList<String> actual : listOLists){
                opciones.add(actual.get(1));
                System.out.println("*********" + actual.get(0) + ":::" + actual.get(1) + "***********");
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s.setAdapter(adapter);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void recibirGET() throws InterruptedException {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    resultados = new ArrayList<>();
                    listOLists = new ArrayList<>();
                    StringBuilder resultado = new StringBuilder();

                    String tempURL = "https://striped-weaver-309814.ue.r.appspot.com/Metodo_De_Pago?HolaE=" + "dominer340@gmail.com";
                    URL url = new URL(tempURL);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");


                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String linea;
                    // Mientras el BufferedReader se pueda leer, agregar contenido a resultado
                    while ((linea = rd.readLine()) != null) {
                        resultado.append(linea);
                        System.out.println("------------Lectura_recibidatest--------------F" + linea + "Fdominer---dominer340@gmail.\n");
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
                            resultados = new ArrayList<>();
                            //el primer parametro es quien deberia guardar el ID del metodo de pago
                            String MP_id = (String) Temp.get(0);
                            String MP_Codigo = (String) Temp.get(1);

                            //resultados guarda 2 valores (el ID y el codigo que deberia ser el nombre del metodo de pago) por cada lista que tengo dentro de CollectionString
                            resultados.add(MP_id);
                            resultados.add(MP_Codigo);
                            listOLists.add(resultados);

                            System.out.println("---lectura primer parametro:___" +MP_id+ "___segundo parametro___" + MP_Codigo +"\n" );
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
>>>>>>> 7ca721869f01b5577709ad9f2ea1e1bdd55d6240
}