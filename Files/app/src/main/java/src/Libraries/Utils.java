package src.Libraries;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import src.Controler.Mis_Informes;
import src.Model.DatoGrafica;

public class Utils {

    private static Gson gson = new Gson();
    private static ArrayList<String> resultadosCategoria;
    private static ArrayList<ArrayList<String>> listOListsCategoria = new ArrayList<>();

    public static ArrayList<ArrayList<String>> getListOListsCategoria() {
        return listOListsCategoria;
    }

    public static void recibirGET_Categoria() throws InterruptedException {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    resultadosCategoria = new ArrayList<>();
                    listOListsCategoria = new ArrayList<>();
                    StringBuilder resultado = new StringBuilder();

                    String tempURL = "https://striped-weaver-309814.ue.r.appspot.com/CategoriaGet?catP=" + src.Libraries.FireBase.Utils.getUser().getEmail();
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
                            resultadosCategoria = new ArrayList<>();
                            //el primer parametro es quien deberia guardar el ID del metodo de pago
                            String ID_categoria = (String) Temp.get(0);
                            String nombre_categoria = (String) Temp.get(1);

                            //resultados guarda 2 valores (el ID y el codigo que deberia ser el nombre del metodo de pago) por cada lista que tengo dentro de CollectionString
                            resultadosCategoria.add(ID_categoria);
                            resultadosCategoria.add(nombre_categoria);
                            listOListsCategoria.add(resultadosCategoria);

                            System.out.println("---lectura primer parametro:___" + ID_categoria + "___segundo parametro___" + nombre_categoria + "\n");
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

    public static ArrayList<DatoGrafica> analisisDatos() {
        ArrayList<ArrayList<String>> listOfListsCategorias = new ArrayList<>();
        ArrayList<DatoGrafica> datosGraficas = new ArrayList<>();
        float contI, contE;
        //Por cada categoria se suman los movimientos que la involucran. Se agrega la categoria al piechart con su respectivo total.
        for (ArrayList<String> categoriaActual : src.Libraries.Utils.getListOListsCategoria()) {
            ArrayList<String> temporal = new ArrayList<>();
            contI = 0;
            contE = 0;
            temporal.add(categoriaActual.get(1));
            DatoGrafica dato = new DatoGrafica();
            dato.setNombre(categoriaActual.get(1));
            for (ArrayList<String> movimientoActual : Mis_Informes.getListOListsMovimiento()) {
                if (movimientoActual.get(4).equals(categoriaActual.get(0))) {
                    if (Float.parseFloat(movimientoActual.get(1)) >= 0) {
                        contI += Float.parseFloat(movimientoActual.get(1));
                    } else {
                        contE += Float.parseFloat(movimientoActual.get(1)) * (-1);
                    }
                }
            }
            dato.setIngreso(contI);
            dato.setEgreso(contE);
            datosGraficas.add(dato);
            temporal.add(String.valueOf(contI));
            temporal.add(String.valueOf(contE));
            listOfListsCategorias.add(temporal);
            System.out.println("Total de " + temporal.get(0) + " = " + String.valueOf(contI) + ", " + String.valueOf(contE));
        }
        return datosGraficas;
    }

}
