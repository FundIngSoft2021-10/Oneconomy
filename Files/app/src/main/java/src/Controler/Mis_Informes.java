    package src.Controler;

    import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

    import src.Controler.Graficas.MisGraficas;
    import src.Libraries.FireBase.Utils;

    public class Mis_Informes extends AppCompatActivity {

        private static Gson gson = new Gson();
        private static ArrayList<String> resultadosMovimiento;
        private static ArrayList<ArrayList<String>> listOListsMovimiento;

        public static ArrayList<ArrayList<String>> getListOListsMovimiento() {
            return listOListsMovimiento;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            try {
                recibirGET_movimientos();
                recibirGET_Categoria();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setContentView(R.layout.activity_mis__informes);
        }

        public void InformeMes(View view) {
            /*
            Intent i = new Intent(this, InformeMes.class);
            startActivity(i);
            */
        }

        public void InformeEscrito(View view) {
            /*
            Intent i = new Intent(this, InformeEscrito.class);
            startActivity(i);
            */
        }

        public void MisGraficas(View view) {
            Intent i = new Intent(this, MisGraficas.class);
            startActivity(i);
        }

        public void InformeCategoria(View view) {

            Intent i = new Intent(this, InformePorCategoria.class);
            startActivity(i);

        }

        public void InformeMetodoDePago(View view) {
            /*
            Intent i = new Intent(this, InformeMetodoDePago.class);
            startActivity(i);
            */
        }

        public static void recibirGET_movimientos() throws InterruptedException {

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        resultadosMovimiento = new ArrayList<>();
                        listOListsMovimiento = new ArrayList<>();
                        StringBuilder resultado = new StringBuilder();

                        String tempURL = "https://striped-weaver-309814.ue.r.appspot.com/movimientoST?CL=" + Utils.getUser().getEmail();
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

                        Type collectionType = new TypeToken<Collection<ArrayList>>() {
                        }.getType();
                        Collection<ArrayList> CollectionString = gson.fromJson(java.lang.String.valueOf(resultado), collectionType);

                        //CollectionString guarda todos los valores de un lista, dentro de otra lista

                        if (CollectionString.isEmpty() == false) {
                            for (ArrayList Temp : CollectionString) {
                                resultadosMovimiento = new ArrayList<>();

                                String idMovimiento = (String) Temp.get(0);
                                String valor = (String) Temp.get(1);
                                String fecha = (String) Temp.get(2);
                                String descripcion = (String) Temp.get(3);
                                String idCategoria = (String) Temp.get(4);
                                String idMetodoDePago = (String) Temp.get(5);
                                String email = (String) Temp.get(6);
                                String idExtracto = (String) Temp.get(7);

                                //resultados guarda 2 valores (el ID y el codigo que deberia ser el nombre del metodo de pago) por cada lista que tengo dentro de CollectionString
                                resultadosMovimiento.add(idMovimiento); //0
                                resultadosMovimiento.add(valor); //1
                                resultadosMovimiento.add(fecha); //2
                                resultadosMovimiento.add(descripcion); //3
                                resultadosMovimiento.add(idCategoria); //4
                                resultadosMovimiento.add(idMetodoDePago); //5
                                resultadosMovimiento.add(email); //6
                                resultadosMovimiento.add(idExtracto); //7
                                listOListsMovimiento.add(resultadosMovimiento);
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

        public static void recibirGET_Categoria() throws InterruptedException{
            src.Libraries.Utils.recibirGET_Categoria();
        }
    }
