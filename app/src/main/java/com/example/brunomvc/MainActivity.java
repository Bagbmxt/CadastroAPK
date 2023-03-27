package com.example.brunomvc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;

public class MainActivity extends AppCompatActivity {

    ImageView botaoADD,botaoVer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoADD  = findViewById(R.id.botaoADD);
        botaoVer = findViewById(R.id.botaoVer);

        botaoADD.setOnClickListener(new android.view.View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, Cadastro.class);
                startActivity(intent);
            }
        });

        botaoVer.setOnClickListener(new android.view.View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, ListarAlunosActivity.class);
                startActivity(intent);
            }
        });



    }
}