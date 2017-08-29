package com.example.sy0317.floatballdemo;


import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button open ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        open = (Button) findViewById(R.id.open);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(view);

            }
        });
    }
    private void startService(View view){
        Intent intent = new Intent(this,MyFloatService.class);
        startService(intent);
        finish();


    }
}
