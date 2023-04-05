package com.example.exampleboxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("커밋 테스트");
        System.out.println("커밋 2차 테스트 왜 안돼 이거");
    }
}