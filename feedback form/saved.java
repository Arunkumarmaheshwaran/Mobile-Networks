package com.example.feed;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
public class SavedActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView savedText = findViewById(R.id.savedText);
        String feedback = getIntent().getStringExtra("feedback");

        String rating = "0";
        if ("Excellent".equals(feedback)) rating = "5/5 Stars";
        else if ("Good".equals(feedback)) rating = "4/5 Stars";
        else if ("Average".equals(feedback)) rating = "3/5 Stars";

        savedText.setText("Your Rating: " + rating + "\nFeedback: " + feedback);
    }
}


