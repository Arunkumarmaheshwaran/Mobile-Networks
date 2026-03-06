package com.example.location;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
public class MainActivity extends AppCompatActivity {
    private EditText editLatitude, editLongitude, editLocationName;
    private Button btnLatLongToArea, btnAddressToLatLong;
    private TextView txtResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editLatitude = findViewById(R.id.editLatitude);
        editLongitude = findViewById(R.id.editLongitude);
        editLocationName = findViewById(R.id.editLocationName);
        btnLatLongToArea = findViewById(R.id.btnLatLongToArea);
        btnAddressToLatLong = findViewById(R.id.btnAddressToLatLong);
        txtResult = findViewById(R.id.txtResult);
        btnLatLongToArea.setOnClickListener(v -> getLocationFromLatLong());
        btnAddressToLatLong.setOnClickListener(v -> getLatLongFromLocation());
    }
    private void getLocationFromLatLong() {
        String latStr = editLatitude.getText().toString();
        String lonStr = editLongitude.getText().toString();
        if (latStr.isEmpty() || lonStr.isEmpty()) {
            Toast.makeText(this, "Enter both latitude and longitude", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            double latitude = Double.parseDouble(latStr);
            double longitude = Double.parseDouble(lonStr);
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String location = address.getLocality();
                if (location == null) location = address.getAdminArea();
                if (location == null) location = "Location not available";
                txtResult.setText("Location: " + location);
            } else {
                txtResult.setText("No location found");
            }
        } catch (Exception e) {
            txtResult.setText("Error: " + e.getMessage());
        }
    }
    private void getLatLongFromLocation() {
        String locationName = editLocationName.getText().toString();
        if (locationName.isEmpty()) {
            Toast.makeText(this, "Enter a location name", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocationName(locationName, 1);
            if (addresses != null && !addresses.isEmpty()) {
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();
                txtResult.setText("Latitude: " + latitude + "\nLongitude: " + longitude);
            } else {
                txtResult.setText("No coordinates found");
            }
        } catch (IOException e) {
            txtResult.setText("Error: " + e.getMessage());
        }
    }
}
