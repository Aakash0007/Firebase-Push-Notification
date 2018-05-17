package com.example.aakash.firebasepushnotification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

import com.example.aakash.firebasepushnotification.Config.Config;
import com.squareup.picasso.Picasso;

public class DisplayActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        imageView = (ImageView) findViewById(R.id.imageView);

        if (TextUtils.isEmpty(Config.imageLink))
            Picasso.with(this).load(Config.imageLink).into(imageView);
    }
}
