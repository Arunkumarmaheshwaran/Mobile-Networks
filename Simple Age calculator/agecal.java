package com.example.agecal;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Button;
import java.util.Calendar;
public class MainActivity extends AppCompatActivity {
    CalendarView calendarView;
    TextView textView;
    Button button;
    int birthYear, birthMonth, birthDay;
    boolean dateSelected = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarView = findViewById(R.id.calendarView);
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            birthYear = year;
            birthMonth = month;
            birthDay = dayOfMonth;
            dateSelected = true;
        });
        button.setOnClickListener(v -> {
            if (!dateSelected) {
                textView.setText("Please select your DOB first!");
                return;
            }
            Calendar today = Calendar.getInstance();
            int currentYear = today.get(Calendar.YEAR);
            int currentMonth = today.get(Calendar.MONTH);
            int currentDay = today.get(Calendar.DAY_OF_MONTH);
            int age = currentYear - birthYear;
            if (currentMonth < birthMonth ||
                    (currentMonth == birthMonth && currentDay < birthDay)) {
                age--;
            }
            textView.setText("Your Age: " + age + " years");
        });
    }
}
