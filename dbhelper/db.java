package com.example.db;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.database.Cursor;
import java.util.*;
public class MainActivity extends AppCompatActivity {
    EditText staffInput, studentInput, searchInput, deleteInput;
    Button saveButton, searchButton, deleteButton;
    ListView resultList;
    ArrayAdapter<String> adapter;
    List<String> displayList = new ArrayList<>();
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        staffInput = findViewById(R.id.staffInput);
        studentInput = findViewById(R.id.studentInput);
        searchInput = findViewById(R.id.searchInput);
        deleteInput = findViewById(R.id.deleteInput);
        saveButton = findViewById(R.id.saveButton);
        searchButton = findViewById(R.id.searchButton);
        deleteButton = findViewById(R.id.deleteButton);
        resultList = findViewById(R.id.resultList);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        resultList.setAdapter(adapter);
        dbHelper = new DBHelper(this);
        saveButton.setOnClickListener(v -> {
            String staff = staffInput.getText().toString().trim();
            String student = studentInput.getText().toString().trim();
            if (!staff.isEmpty() && !student.isEmpty()) {
                dbHelper.insertMapping(staff, student);
                Toast.makeText(this, "Saved: " + student + " → " + staff, Toast.LENGTH_SHORT).show();
                staffInput.setText("");
                studentInput.setText("");
            } else {
                Toast.makeText(this, "Enter both staff and student", Toast.LENGTH_SHORT).show();
            }
        });
        searchButton.setOnClickListener(v -> {
            String query = searchInput.getText().toString().trim();
            displayList.clear();
            Cursor staffCursor = dbHelper.getStaffByStudent(query);
            if (staffCursor != null && staffCursor.moveToFirst()) {
                displayList.add("Student: " + query + " → Staff(s):");
                do {
                    displayList.add(staffCursor.getString(0));
                } while (staffCursor.moveToNext());
                staffCursor.close();
            } else {
                Cursor studentCursor = dbHelper.getStudentsByStaff(query);
                if (studentCursor != null && studentCursor.moveToFirst()) {
                    displayList.add("Staff: " + query + " → Students:");
                    do {
                        displayList.add(studentCursor.getString(0));
                    } while (studentCursor.moveToNext());
                    studentCursor.close();
                } else {
                    displayList.add("No match found");
                }
            }
            adapter.notifyDataSetChanged();
        });
        deleteButton.setOnClickListener(v -> {
            String name = deleteInput.getText().toString().trim();
            if (!name.isEmpty()) {
                int rows = dbHelper.deletePerson(name);
                if (rows > 0) {
                    Toast.makeText(this, "Deleted: " + name, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "No match found to delete", Toast.LENGTH_SHORT).show();
                }
                deleteInput.setText("");
            } else {
                Toast.makeText(this, "Enter a name to delete", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
