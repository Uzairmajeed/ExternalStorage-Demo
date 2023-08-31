package com.facebook.externalstorage_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button buttonsave,buttonload;
    EditText editText;
    TextView textView;
    String filename = "";
    String filepath="";
    String fileContent="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=findViewById(R.id.editText);
        textView=findViewById(R.id.textView);
        buttonsave=findViewById(R.id.button1);
        buttonload=findViewById(R.id.button2);
        filename = "myFile.txt";
        filepath = "MyFileDir";
        if(!isExternalStorageAvailableForRW()){
            buttonsave.setEnabled(false);
        }
        buttonsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileContent=editText.getText().toString().trim();
                if(!fileContent.equals ("")){
                File myExternalfile = new File (getExternalFilesDir(filepath), filename);
                FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(myExternalfile);
                        fos.write(fileContent.getBytes());
                    } catch (IOException e) {
                        e.getStackTrace();
                    }
                    editText.getText().clear();
                    Toast.makeText ( MainActivity.this,  "Information saved to SD card.", Toast.LENGTH_SHORT) . show();
            }
                else {
                    Toast.makeText ( MainActivity.this,  "Text field can not be empy",Toast.LENGTH_SHORT). show();
                }
            }
        });

        buttonload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileReader fr = null;
                File myExternalFile = new File(getExternalFilesDir(filepath), filename);
                StringBuilder stringBuilder = new StringBuilder ();
                try {
                    fr = new FileReader (myExternalFile);
                    BufferedReader br = new BufferedReader (fr);
                    String line = br.readLine();
                    while(line != null) {
                        stringBuilder.append(line).append('\n');
                        line = br.readLine();
                    }
                } catch (FileNotFoundException e) {
                    e.getStackTrace();
                } catch (IOException e) {
                   e.getStackTrace();
                }finally {
                    String fileContents = "File contents \n" + stringBuilder.toString();
                   textView.setText (fileContents);
                }

            }
        });
    }

    private boolean isExternalStorageAvailableForRW() {
        String extStorageState = Environment.getExternalStorageState();
        if(extStorageState.equals (Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return  false;
    }

}