package com.example.perrositst;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    SystemClock.sleep(3000);

                    // Verificar si el usuario ya ha iniciado sesión
                    boolean userLoggedIn = false /* Lógica para verificar el estado de inicio de sesión */;

                    Intent intent;

                    if (userLoggedIn) {
                        // Si el usuario ha iniciado sesión, abrir MainActivity
                        intent = new Intent(SplashScreen.this, MainActivity.class);
                    } else {
                        // Si el usuario no ha iniciado sesión, abrir LoginActivity
                        intent = new Intent(SplashScreen.this, LoginActivity.class);
                    }

                    startActivity(intent);
                    finish();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}
