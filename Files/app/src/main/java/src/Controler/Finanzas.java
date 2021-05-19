package src.Controler;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.oneconomy.R;
import org.apache.commons.io.IOUtils;
import java.io.InputStream;
import java.util.Base64;


import okhttp3.*;
import src.Libraries.FireBase.Utils;

public class Finanzas extends AppCompatActivity {

    private final int CHOOSE_PDF_FROM_DEVICE =1001;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finanzas);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Button btn_filePicker = (Button) findViewById(R.id.btn_filePicker);
        btn_filePicker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                callChooseFileFromDevice();
            }
        });
    }

    private void callChooseFileFromDevice(){
        Intent intent=new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        startActivityForResult(intent,CHOOSE_PDF_FROM_DEVICE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent resultData){
        super.onActivityResult(requestCode, resultCode, resultData);
        if(requestCode==CHOOSE_PDF_FROM_DEVICE && resultCode== Activity.RESULT_OK){
            if(resultData!=null){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                        System.out.println("No se tiene permiso para leer.");
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
                    } else {
                        try {
                            System.out.println("Si se tiene permiso para leer.");
                            InputStream pdfInput = this.getContentResolver().openInputStream(resultData.getData());
                            byte[] bytes = IOUtils.toByteArray(pdfInput);
                            String pdfInBase64 = Base64.getEncoder().encodeToString(bytes);
                            this.sendPost("file", pdfInBase64);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    public void MovimientoManual(View view) {
        Intent i = new Intent(this, MovimientoManual.class);
        startActivity(i);
    }

    public void HistorialMovimientos(View view) {
        Intent i = new Intent(this, HistorialMovimientos.class);
        startActivity(i);
    }

    private void sendPost(String name, String value) throws Exception {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final MediaType JSON = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, "{" +
                "\""+name+"\":\""+value+"\"," +
                "\"email\":" +
                "\""+ Utils.getUser().getEmail()+"\"}");
        Request request = new Request.Builder()
                .url("http://18.219.21.101:4200/pdf/upload")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if(response.code()==200){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Los datos han sido guardados correctamente");
                builder.setTitle("Genial!");
                builder.setPositiveButton("Gracias", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Algo ha salido mal, intenta de nuevo mas tarde.");
                builder.setTitle("Ouch!");
                builder.setPositiveButton("Aish OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            System.out.println(response.body().string());
        }

    }
}