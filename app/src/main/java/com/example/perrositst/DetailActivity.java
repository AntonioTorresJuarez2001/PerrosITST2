package com.example.perrositst;

import static android.Manifest.permission.CALL_PHONE;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailActivity extends AppCompatActivity {

    public static final String PET_KEY = "pet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        Pet pet = extras.getParcelable(PET_KEY);

        if (pet != null) {
            TextView petName = findViewById(R.id.activity_detail_pet_name);
            TextView petDescription = findViewById(R.id.activity_detail_pet_description);
            TextView ownerName = findViewById(R.id.activity_detail_owner_name);
            TextView ownerPhoneNumber = findViewById(R.id.activity_detail_owner_phone_number);
            ImageView petImage = findViewById(R.id.activity_detail_pet_image);

            petName.setText(pet.getName());
            petDescription.setText(pet.getDescription());
            ownerName.setText(pet.getOwnerName());
            ownerPhoneNumber.setText(pet.getPhoneNumber());
            petImage.setImageDrawable(ContextCompat.getDrawable(this, pet.getImageId()));

            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Handle FAB click event to initiate a phone call
                    String phoneNumber = ownerPhoneNumber.getText().toString().trim();

                    if (!phoneNumber.isEmpty()) {
                        // Check if the CALL_PHONE permission is granted
                        if (ContextCompat.checkSelfPermission(DetailActivity.this, CALL_PHONE)
                                == PackageManager.PERMISSION_GRANTED) {
                            // Permission is granted, initiate the phone call
                            Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                            startActivity(dialIntent);
                        } else {
                            // Permission is not granted, request it
                            requestCallPhonePermission();
                        }
                    } else {
                        // Handle the case where the phone number is empty
                        Toast.makeText(DetailActivity.this, "Phone number is not available", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            Button btnAdopt = findViewById(R.id.btnAdopt);
            btnAdopt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Abrir la actividad del formulario de adopción
                    openAdoptionFormActivity();
                }
            });
        }
    }

    private void requestCallPhonePermission() {
        // Request the CALL_PHONE permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{CALL_PHONE}, 123);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 123 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission is granted, initiate the phone call
            String phoneNumber = ((TextView) findViewById(R.id.activity_detail_owner_phone_number)).getText().toString().trim();
            Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
            startActivity(dialIntent);
        } else {
            // Permission is denied, show a message or handle it accordingly
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void openAdoptionFormActivity() {
        Intent intent = new Intent(DetailActivity.this, AdoptionFormActivity.class);
        startActivity(intent);
    }
}
