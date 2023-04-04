package com.example.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btn_move;
    private EditText et_test;
    private String str;
    ImageView test;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_test = findViewById(R.id.et_test);

        btn_move = findViewById(R.id.btn_move);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // str을 여기에 넣어야 버튼을 눌렀을 때 getText한다.
                str = et_test.getText().toString(); // 그냥 쓰면 에러가 난다. toString() 을 적어줘야 스트링으로 인식한다.
                Intent intent = new Intent(MainActivity.this, SubActivity.class); // 첫번째에서 두번째로
                intent.putExtra("str" , str);
                startActivity(intent); // 액티비티 이동 구문
            }
        });

        test = findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "석호 등장", Toast.LENGTH_SHORT).show();
            }
        });
    }
}