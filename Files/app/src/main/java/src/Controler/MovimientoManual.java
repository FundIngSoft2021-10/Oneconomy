package src.Controler;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import src.Libraries.DatePickerFragment;
import src.Libraries.FireBase.Utils;
import src.Model.Campos;
import src.Model.Cliente;
import src.Model.MensajeGenerico;
import src.Model.Movimiento;


public class MovimientoManual extends AppCompatActivity {

    private static Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimiento_manual);
        try {

            src.Libraries.Utils.recibirGET_MetodoPago();
            src.Libraries.Utils.recibirGET_Categoria();

            Spinner s = (Spinner) findViewById(R.id.Desplegable_Metodo_Pago);
            ArrayList<String> opciones = new ArrayList<>();
            opciones.add(" ---- ");
            for (ArrayList<String> actual : src.Libraries.Utils.getListOListsMetodos_Pago()) {
                opciones.add(actual.get(1));
                System.out.println("*********" + actual.get(0) + ":::" + actual.get(1) + "***********");
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s.setAdapter(adapter);
            s.setSelection(0);

            //------------------------Categorías-----------------------

            Spinner c = (Spinner) findViewById(R.id.desplegable_Categoria);
            ArrayList<String> opciones2 = new ArrayList<>();
            opciones2.add(" ---- ");
            for (ArrayList<String> actual : src.Libraries.Utils.getListOListsCategoria()) {
                opciones2.add(actual.get(1));
                System.out.println("*********" + actual.get(0) + ":::" + actual.get(1) + "***********");
            }

            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones2);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            c.setAdapter(adapter2);
            c.setSelection(0);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void crearMovimientoManual(View view) throws ParseException, InterruptedException {
        Movimiento tempMovimiento = new Movimiento();

        CheckBox ingreso = (CheckBox) findViewById(R.id.checkBoxIngreso);
        CheckBox egreso = (CheckBox) findViewById(R.id.checkBoxEgresos);
        EditText fecha = (EditText) findViewById(R.id.fechaEntradaManual);
        EditText valor = (EditText) findViewById(R.id.campoValorEntradaManual);
        EditText descripcion = (EditText) findViewById(R.id.DescripcionEntradaManual);
        Spinner metodo = (Spinner) findViewById(R.id.Desplegable_Metodo_Pago);
        Spinner categoria = (Spinner) findViewById(R.id.desplegable_Categoria);

        if ((!egreso.isChecked() && !ingreso.isChecked()) || fecha.getText().toString().isEmpty() ||
                valor.getText().toString().isEmpty() || descripcion.getText().toString().isEmpty() ||
                metodo.getSelectedItemPosition() == 0 || categoria.getSelectedItemPosition() == 0) {
            CrearCuenta.Alerta(view.getContext(), "Error al agregar movimiento", "\n+ Existen parametros sin llenar");
        } else {
            tempMovimiento.setValue(Integer.parseInt(valor.getText().toString()));
            if (egreso.isChecked())
                tempMovimiento.setValue(tempMovimiento.getValue() * (-1));

            Date date = null;
            String fechaS = String.valueOf(fecha.getText());
            date = new SimpleDateFormat("dd/MM/yyyy").parse(fechaS);
            tempMovimiento.setFecha_Movimiento(date);

            String metodoS = String.valueOf(metodo.getSelectedItem().toString());
            for (ArrayList<String> actual : src.Libraries.Utils.getListOListsMetodos_Pago()) {
                if (actual.get(1).contains(metodoS)) {
                    tempMovimiento.setIdMetodo_pago(Integer.parseInt(actual.get(0)));
                    break;
                }
            }

            String categoriaS = String.valueOf(categoria.getSelectedItem().toString());
            for (ArrayList<String> actual : src.Libraries.Utils.getListOListsCategoria()) {
                if (actual.get(1).contains(categoriaS)) {
                    tempMovimiento.setIdCategoria(Integer.parseInt(actual.get(0)));
                    break;
                }
            }

            tempMovimiento.setDescripcion(String.valueOf(descripcion.getText()));

            tempMovimiento.setPerfilEmail(Utils.getUser().getEmail());

            Random random = new Random();
            tempMovimiento.setIdMovimiento(random.nextInt(99999999));

            if (Utils.enviarPost(tempMovimiento, "https://striped-weaver-309814.ue.r.appspot.com/movimientoST")) {
                Toast.makeText(view.getContext(), "Movimiento agregado correctamente",
                        Toast.LENGTH_SHORT).show();
                Intent i = new Intent(view.getContext(), Finanzas.class);
                view.getContext().startActivity(i);
            } else {
                CrearCuenta.Alerta(view.getContext(), "Error al añadir movimiento", "\nPor favor intente más tarde");
            }
        }
    }

    public void checkIngreso(View view) {
        CheckBox egreso = (CheckBox) findViewById(R.id.checkBoxEgresos);
        if (egreso.isChecked()) {
            egreso.setChecked(false);
        }
    }

    public void checkEgreso(View view) {
        CheckBox ingreso = (CheckBox) findViewById(R.id.checkBoxIngreso);
        if (ingreso.isChecked()) {
            ingreso.setChecked(false);
        }
    }

    public void showDatePickerDialog(View view) {

        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + "/" + (month + 1) + "/" + year;


                EditText fecha_seleccionada = (EditText) findViewById(R.id.fechaEntradaManual);

                fecha_seleccionada.setText(selectedDate);

            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}