// Importaciones de clases necesarias para la actividad
package com.example.perrositst;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// Definición de la clase LoginActivity, que extiende AppCompatActivity
public class LoginActivity extends AppCompatActivity {

    // Declaración de variables para los elementos de la interfaz de usuario
    TextInputEditText editTextEmail, editTextPassword;
    Button buttonLogin;
    Button btnRegistro; // Se agrega un botón para el registro
    FirebaseAuth mAuth; // Objeto para interactuar con Firebase Authentication
    ProgressBar progressBar;
    TextView textView;

    // Método onStart, se ejecuta al iniciar la actividad
    @Override
    public void onStart() {
        super.onStart();
        // Verifica si el usuario ya ha iniciado sesión
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Si el usuario ya ha iniciado sesión, lo lleva a MainActivity y cierra esta actividad.
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    // Método onCreate, se ejecuta al crear la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Establece el diseño de la actividad a partir de un archivo XML
        setContentView(R.layout.activity_login);

        // Inicialización del objeto FirebaseAuth para interactuar con Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Asociación de variables con elementos de la interfaz mediante IDs definidos en XML
        editTextEmail = findViewById(R.id.email);  // Corrección: Cambiado el ID a "email"
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.btnLogin);
        btnRegistro = findViewById(R.id.btnRegister); // Se asocia con el botón de registro
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.RegisterNow);

        // Configuración de un listener para el botón de registro
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Cuando se hace clic en el botón de registro, se lanza la actividad RegisterActivity
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });


        // Configuración de un listener para el botón de inicio de sesión
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Muestra un ProgressBar para indicar que se está realizando la autenticación
                progressBar.setVisibility(View.VISIBLE);

                // Obtiene el email y la contraseña ingresados por el usuario
                String email, password;
                email = String.valueOf(editTextEmail.getText().toString());
                password =  String.valueOf(editTextPassword.getText().toString());

                // Verifica que el campo de email no esté vacío
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(LoginActivity.this, "Ingresa un Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verifica que el campo de contraseña no esté vacío
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "Ingresa una Contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Inicia sesión mediante Firebase Authentication
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // Oculta el ProgressBar después de completar la tarea
                                progressBar.setVisibility(View.GONE);

                                // Verifica si la autenticación fue exitosa
                                if (task.isSuccessful()) {
                                    // Si es exitosa, lleva al usuario a MainActivity
                                    Toast.makeText(getApplicationContext(), "Inicio de sesión exitoso.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish(); // Cierra esta actividad para evitar que el usuario vuelva atrás y regrese al LoginActivity
                                } else {
                                    // Si la autenticación falla, muestra un mensaje de error
                                    Toast.makeText(LoginActivity.this, "Falló la autenticación.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
