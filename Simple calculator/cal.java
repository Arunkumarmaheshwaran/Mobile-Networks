package com.example.cal;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    private TextView resultTextView;
    private double num1 = 0, num2 = 0;
    private String operator = "";
    private boolean isNewOperand = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTextView = findViewById(R.id.resultTextView);
    }
    public void buttonClick(View view) {
        Button button = (Button) view;
        String text = button.getText().toString();
        if (text.matches("[0-9]")) {
            if (isNewOperand) {
                resultTextView.setText(text);
                isNewOperand = false;
            } else {
                resultTextView.append(text);
            }
        }
        else if (text.matches("[+\\-*/]")) {
            num1 = Double.parseDouble(resultTextView.getText().toString());
            operator = text;
            isNewOperand = true;
        }
        else if (text.equals("=")) {
            num2 = Double.parseDouble(resultTextView.getText().toString());
            double result = 0;
            switch (operator) {
                case "+": result = num1 + num2; break;
                case "-": result = num1 - num2; break;
                case "*": result = num1 * num2; break;
                case "/": result = (num2 != 0) ? num1 / num2 : Double.POSITIVE_INFINITY; break;
            }
            resultTextView.setText(String.valueOf(result));
            isNewOperand = true;
        }
        else if (text.equals("C")) {
            num1 = num2 = 0;
            operator = "";
            resultTextView.setText("0");
            isNewOperand = true;
        }
    }
}
