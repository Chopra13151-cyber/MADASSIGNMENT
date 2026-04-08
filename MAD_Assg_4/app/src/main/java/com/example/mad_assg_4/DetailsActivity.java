package com.example.mad_assg_4;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.util.Date;

public class DetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String path = getIntent().getStringExtra("path");
        File file = new File(path);

        ImageView iv = findViewById(R.id.ivFull);
        TextView tv = findViewById(R.id.tvInfo);
        Button btn = findViewById(R.id.btnDelete);

        iv.setImageURI(Uri.fromFile(file));
        tv.setText("Name: " + file.getName() + "\nSize: " + (file.length()/1024) + "KB\nDate: " + new Date(file.lastModified()));

        btn.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete?")
                    .setPositiveButton("Yes", (d, w) -> {
                        if(file.delete()) { finish(); Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show(); }
                    }).setNegativeButton("No", null).show();
        });
    }
}