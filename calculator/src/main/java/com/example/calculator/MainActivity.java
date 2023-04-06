package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean firstInput = true; // 입력 값이 처음 입력 되는가
    int resultNumber = 0; // 계산된 결과 값을 저장
    char operator = '+'; // 입력된 연산자를 저장

    TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultText = findViewById(R.id.result_text);

    }
    public void buttonClick(View view) {
        if(view.getId() == R.id.all_clear_button){
            firstInput = true;
            resultNumber = 0;
            operator = '+';
            resultText.setTextColor(0xff666666);
            resultText.setText(String.valueOf(resultNumber));
        }

        if(view.getId() == R.id.one){
            if(firstInput){
                resultText.setTextColor(0xff000000);
                resultText.setText("1");
                firstInput = false;
            }else{
                resultText.append("1");
            }
        }else{
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        
        return super.onTouchEvent(event);
    }
}
