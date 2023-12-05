package com.example.perrositst;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    Button button;
    TextView textView;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.logout);
        textView = findViewById(R.id.user_details);
        user = auth.getCurrentUser();
        if(user == null){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            textView.setText(user.getEmail());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        RecyclerView petRecycler = (RecyclerView) findViewById(R.id.activity_main_pet_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        petRecycler.setLayoutManager(linearLayoutManager);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        petRecycler.setLayoutManager(gridLayoutManager);

        ArrayList<Pet> petList = new ArrayList<>();
        petList.add(new Pet("Chester", getString(R.string.pet_description), "Actual Dueño: Ing Maximino ITST", "231-132-6008", R.drawable.chester));
        petList.add(new Pet("Chino", getString(R.string.pet_description), "Actual Dueño: Ing Dario ITST", "555-55-55", R.drawable.chino));
        petList.add(new Pet("Choco", getString(R.string.pet_description), "Actual Dueño: Ing Aldahit ITST", "555-55-55", R.drawable.choco));
        petList.add(new Pet("Chorejas", getString(R.string.pet_description), "Actual Dueño: Ing Karla ITST", "555-55-55", R.drawable.chorejas));
        petList.add(new Pet("Clarita", getString(R.string.pet_description), "Actual Dueño: Ing Quijano ITST", "555-55-55", R.drawable.clarita));
        petList.add(new Pet("Negrito", getString(R.string.pet_description), "Claudia", "555-55-55", R.drawable.negrita));
        petList.add(new Pet("Chester", getString(R.string.pet_description), "Actual Dueño: Ing Daniel ITST", "555-55-55", R.drawable.chester));
        petList.add(new Pet("Chino", getString(R.string.pet_description), "Actual Dueño: Ing Eli ITST", "555-55-55", R.drawable.chino));
        petList.add(new Pet("Choco", getString(R.string.pet_description), "Actual Dueño: Ing Alma ITST", "555-55-55", R.drawable.choco));
        petList.add(new Pet("Chorejas", getString(R.string.pet_description), "Actual Dueño: Ing Alejandra ITST", "555-55-55", R.drawable.chorejas));
        petList.add(new Pet("Clarita", getString(R.string.pet_description), "Actual Dueño: Ing Oscarin ITST", "555-55-55", R.drawable.clarita));
        petList.add(new Pet("Negrito", getString(R.string.pet_description), "Actual Dueño: Ing Catta ITST", "555-55-55", R.drawable.negrita));


        PetAdapter petAdapter = new PetAdapter(this, petList);
        petRecycler.setAdapter(petAdapter);

        petAdapter.setOnPetClickListener(new PetAdapter.OnPetClickListener() {
            @Override
            public void onPetClick(Pet pet) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(DetailActivity.PET_KEY, pet);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle();
                    startActivity(intent, bundle);
                } else {
                    startActivity(intent);
                }
            }
        });
    }
}
