package src.Controler;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
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

import src.Libraries.DatePickerFragment;
import src.Libraries.FireBase.Utils;
import src.Model.Campos;
import src.Model.Cliente;
import src.Model.MensajeGenerico;
import src.Model.Movimiento;


public class MovimientoManual extends AppCompatActivity {

    private static Gson gson = new Gson();
    private static boolean estado = false;
    private static MensajeGenerico mensajeGenerico;
    private static Cliente cliente;
    private static Campos campos;
    private static ArrayList<String> resultadosMetodos_Pago;
    private static ArrayList<String> resultadosCategoria;
    private static ArrayList<ArrayList<String>> listOListsMetodos_Pago = new ArrayList<>();
    private static ArrayList<ArrayList<String>> listOListsCategoria = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimiento_manual);
        try {

            this.recibirGET_MetodoPago();

            this.recibirGET_Categoria();



            Spinner s = (Spinner) findViewById(R.id.Desplegable_Metodo_Pago);
            ArrayList<String> opciones = new ArrayList<>();

            for(ArrayList<String> actual : listOListsMetodos_Pago){
                opciones.add(actual.get(1));
                System.out.println("*********" + actual.get(0) + ":::" + actual.get(1) + "***********");
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s.setAdapter(adapter);

            //------------------------Categorías-----------------------

            Spinner c = (Spinner) findViewById(R.id.desplegable_Categoria);
            ArrayList<String> opciones2 = new ArrayList<>();

            for(ArrayList<String> actual : listOListsCategoria){
                opciones2.add(actual.get(1));
                System.out.println("*********" + actual.get(0) + ":::" + actual.get(1) + "***********");
            }

            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones2);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            c.setAdapter(adapter2);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void crearMovimientoManual(View view) {


    }



    public void checkIngreso(View view){
        CheckBox egreso = (CheckBox) findViewById(R.id.checkBoxEgresos);
        if(egreso.isChecked()){
            egreso.setChecked(false);
        }
    }

    public void checkEgreso(View view){
        CheckBox ingreso = (CheckBox) findViewById(R.id.checkBoxIngreso);
        if(ingreso.isChecked()){
            ingreso.setChecked(false);
        }
    }

    public static void recibirGET_Categoria() throws InterruptedException {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    resultadosCategoria = new ArrayList<>();
                    listOListsCategoria = new ArrayList<>();
                    StringBuilder resultado = new StringBuilder();

                    String tempURL = "https://striped-weaver-309814.ue.r.appspot.com/CategoriaGet?catG=" + Utils.getUser().getEmail() ;
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
                            resultadosCategoria = new ArrayList<>();
                            //el primer parametro es quien deberia guardar el ID del metodo de pago
                            String ID_categoria = (String) Temp.get(0);
                            String nombre_categoria = (String) Temp.get(1);

                            //resultados guarda 2 valores (el ID y el codigo que deberia ser el nombre del metodo de pago) por cada lista que tengo dentro de CollectionString
                            resultadosCategoria.add(ID_categoria);
                            resultadosCategoria.add(nombre_categoria);
                            listOListsCategoria.add(resultadosCategoria);

                            System.out.println("---lectura primer parametro:___" +ID_categoria+ "___segundo parametro___" + nombre_categoria +"\n" );
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













    public static void recibirGET_MetodoPago() throws InterruptedException {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    resultadosMetodos_Pago = new ArrayList<>();
                    listOListsMetodos_Pago = new ArrayList<>();
                    StringBuilder resultado = new StringBuilder();

                    String tempURL = "https://striped-weaver-309814.ue.r.appspot.com/Metodo_De_Pago?HolaE=" + Utils.getUser().getEmail() ;
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
                            resultadosMetodos_Pago = new ArrayList<>();
                            //el primer parametro es quien deberia guardar el ID del metodo de pago
                            String MP_id = (String) Temp.get(0);
                            String MP_Codigo = (String) Temp.get(1);

                            //resultados guarda 2 valores (el ID y el codigo que deberia ser el nombre del metodo de pago) por cada lista que tengo dentro de CollectionString
                            resultadosMetodos_Pago.add(MP_id);
                            resultadosMetodos_Pago.add(MP_Codigo);
                            listOListsMetodos_Pago.add(resultadosMetodos_Pago);

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



    public void showDatePickerDialog(View view) {

        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + "/" + (month+1) + "/" + year;

<<<<<<< HEAD
                EditText fecha_seleccionada = (EditText) findViewById(R.id.fecha_seleccionada);
                fecha_seleccionada.setText(selectedDate);
=======
//                EditText fecha_seleccionada = (EditText) findViewById(R.id.);
//                fecha_seleccionada.setText(selectedDate);

                EditText fecha_seleccionada = (EditText) findViewById(R.id.fecha_seleccionada);
                fecha_seleccionada.setText(selectedDate);

>>>>>>> 418e11e00371bc2f524ef343011416e2224e5f4c
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


}