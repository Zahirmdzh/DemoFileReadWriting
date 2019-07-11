package sg.edu.rp.c347.demofilereadwriting;

import android.Manifest;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity {

    String folderLocation;
    Button btnWrite,btnRead;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnWrite  = findViewById(R.id.buttonWrite);
        btnRead = findViewById(R.id.buttonRead);
        tv = findViewById(R.id.textView);


        folderLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Folder";

        if (checkPermission() == true) {

            File folder = new File(folderLocation);
            if (folder.exists() == false) {
                boolean result = folder.mkdir();
                if (result == true) {
                    Log.d("File Read/Write", "Folder created");
                }
            }







        } else {
            String msg = "Permission not granted to retrieve location info";
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        }



        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String folderLocation = Environment.getExternalStorageDirectory().getAbsolutePath()
                            + "/Folder";

                    File targetFile = new File(folderLocation, "data.txt");

                    FileWriter writer = new FileWriter(targetFile, true);
                    //false will overwrite with the first line
                    //true adds to the line
                    writer.write("Hello world" + "\n");
                    writer.flush();
                    writer.close();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Failed to write!",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }


            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String folderLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Folder";
                File targetFile = new File(folderLocation, "data.txt");

                if (targetFile.exists() == true) {
                    String data = "";

                    try {
                        FileReader reader = new FileReader(targetFile);
                        BufferedReader br = new BufferedReader(reader);
                        String line = br.readLine();
                        while (line!= null) {
                            data += line + "\n";
                            line = br.readLine();
                        }
                        br.close();
                        reader.close();

                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Failed to read!",Toast.LENGTH_LONG).show();
                        e.printStackTrace();

                    }

                    Log.d("Content",data);
                    tv.setText(data);
                }
            }
        });




    }





    private boolean checkPermission(){
        int permissionCheck_WRITEEXT = ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheck_READEXT = ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck_WRITEEXT == PermissionChecker.PERMISSION_GRANTED
                || permissionCheck_READEXT == PermissionChecker.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
}
