package src.Controler;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.oneconomy.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import okhttp3.*;

public class Finanzas extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finanzas);

        Button btn_filePicker = (Button) findViewById(R.id.btn_filePicker);
        btn_filePicker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
                myFileIntent.setType("*/*");
                startActivityForResult(myFileIntent, 10);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10 && resultCode == Activity.RESULT_OK){
            System.out.println("Intento de subir un archivo");
            String path = data.getData().getPath();
            System.out.println("Directorio: "+path);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                //Verifica permisos para Android 6.0+
                int permissionCheck = ContextCompat.checkSelfPermission(
                        this,  Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    System.out.println("No se tiene permiso para leer.");
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
                } else {
                    try {
                        System.out.println("Si se tiene permiso para leer.");
                        File file = new File(Environment.getExternalStorageDirectory(),path);
                        System.out.println("absoluto: "+file.getAbsolutePath());

                        InputStream buffer = new FileInputStream(file);
                        /*
                        byte[] fileContent = Files.readAllBytes(file.toPath());
                        String pdfInBase64 = Base64.getEncoder().encodeToString(buffer);
                        System.out.println("Base64");
                        System.out.println(pdfInBase64);
                        this.sendPost("file",pdfInBase64);*/
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    public void MovimientoManual(View view) {
        Intent i = new Intent(this, MovimientoManual.class);
        startActivity(i);
    }

    private void sendPost(String name, String value) throws Exception {
        final OkHttpClient httpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add(name,value)
                .build();

        Request request = new Request.Builder()
                .url("https://androidtesting.free.beeceptor.com")
                .addHeader("User-Agent", "Oneconomy/1.0")
                .addHeader("Content-Type", "application/json")
                .post(formBody)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            System.out.println(response.body().string());
        }

    }
}