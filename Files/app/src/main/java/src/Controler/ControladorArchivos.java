package src.Controler;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oneconomy.R;

public class ControladorArchivos extends AppCompatActivity {


    protected void onCreate(){
        Button btn_filePicker = (Button) findViewById(R.id.btn_filePicker);
        btn_filePicker.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent myFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
                myFileIntent.setType("*/*");
                startActivityForResult(myFileIntent,10);
            }
        });
    }

}
