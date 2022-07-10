package com.example.scanner2.AboutUs;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.scanner2.MainActivity;
import com.example.scanner2.R;

/* renamed from: com.example.scanner.AboutUs.AboutUs */
public class AboutUs extends AppCompatActivity {
    ImageView back;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ImageView imageView = (ImageView) findViewById(R.id.back);
        this.back = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AboutUs.this.startActivity(new Intent(AboutUs.this.getApplicationContext(), MainActivity.class));
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
