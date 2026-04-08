package com.example.mad_assg_4;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new GridLayoutManager(this, 3));

        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File[] files = dir.listFiles();
        List<File> fileList = (files != null) ? Arrays.asList(files) : new ArrayList<>();

        rv.setAdapter(new RecyclerView.Adapter<VH>() {
            @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup p, int t) {
                ImageView iv = new ImageView(p.getContext());
                iv.setLayoutParams(new ViewGroup.LayoutParams(350, 350));
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return new VH(iv);
            }
            @Override public void onBindViewHolder(@NonNull VH h, int i) {
                File f = fileList.get(i);
                ((ImageView)h.itemView).setImageURI(Uri.fromFile(f));
                h.itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(GalleryActivity.this, DetailsActivity.class);
                    intent.putExtra("path", f.getAbsolutePath());
                    startActivity(intent);
                });
            }
            @Override public int getItemCount() { return fileList.size(); }
        });
    }
    static class VH extends RecyclerView.ViewHolder { VH(View v) { super(v); } }
}