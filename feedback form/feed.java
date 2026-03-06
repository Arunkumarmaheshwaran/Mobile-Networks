package com.example.feed;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.content.Intent;
public class FeedbackActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup ratingGroup = findViewById(R.id.ratingGroup);
        Button submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(v -> {
            int selectedId = ratingGroup.getCheckedRadioButtonId();
            if (selectedId != -1) {
                RadioButton selectedRb = findViewById(selectedId);
                String feedback = selectedRb.getText().toString();

                Intent intent = new Intent(FeedbackActivity.this, SavedActivity.class);
                intent.putExtra("feedback", feedback);
                startActivity(intent);
            }
        });
    }
}


}
