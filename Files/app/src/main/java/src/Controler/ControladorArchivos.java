package src.Controler;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.oneconomy.R;

import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ControladorArchivos extends AppCompatActivity {
    //private final OkHttp Cliente = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button btn_filePicker = (Button) findViewById(R.id.btn_filePicker);
        btn_filePicker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
                myFileIntent.setType("*/*");
                startActivityForResult(myFileIntent, 10);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode ==10 && requestCode ==RESULT_OK){
            String path = data.getData().getPath();
            System.out.println(path);
            /*OkHttp request = new OkHttp();

            byte[] input_file = Files.readAllBytes(Paths.get(path));
            byte[] encodedBytes = Base64.getEncoder().encode(input_file);

            String pdfInBase64 = new String(encodedBytes);

            request.sendPost("file",pdfInBase64);
            System.out.println(pdfInBase64);*/
        }
    }

    /*private void sendPost(String name,String data) throws Exception {
        RequestBody formBody = new FormBody.Builder()
                .add(name,data)
                .build();

        Request request = new Request.Builder()
                .url("https://httpbin.org/post")
                .addHeader("User-Agent", "Uploader-AndroidStudio")
                .post(formBody)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            System.out.println(response.body().string());
        }

    }*/
}
