package src.Controler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oneconomy.R;

import java.io.IOException;
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
        if (requestCode == 10){
            System.out.println("Intento de subir un archivo");
            String path = data.getData().getPath();
            System.out.println("Directorio: "+path);
            try {
                byte[] input_file = Files.readAllBytes(Paths.get(path));
                byte[] encodedBytes = Base64.getEncoder().encode(input_file);

                String pdfInBase64 = new String(encodedBytes);

                this.sendPost("file",pdfInBase64);
            } catch (Exception e) {
                e.printStackTrace();
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