package com.example.perrositst;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdoptionFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adoption_form);

        // Obtener referencias a los elementos de la interfaz
        EditText editTextRazon = findViewById(R.id.RazonEditText);
        EditText editTextDormir = findViewById(R.id.DormirEditText);
        EditText editTextTelefono = findViewById(R.id.TelefonoEditText);
        EditText editTextCuentanos = findViewById(R.id.CuentanosEditText);

        Button btnSubmit = findViewById(R.id.EnviarButton);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Procesar la solicitud de adopción aquí
                String razon = editTextRazon.getText().toString();
                String dormir = editTextDormir.getText().toString();
                String telefono = editTextTelefono.getText().toString();
                String cuentanos = editTextCuentanos.getText().toString();

                // Ejemplo: Mostrar un mensaje de éxito
                String message = "Solicitud de adopción enviada con razón: " + razon +
                        ", lugar de dormir: " + dormir +
                        ", teléfono: " + telefono +
                        ", y presentación: " + cuentanos;

                Toast.makeText(AdoptionFormActivity.this, message, Toast.LENGTH_SHORT).show();

                // Puedes agregar lógica adicional aquí, como enviar la solicitud a un servidor, etc.
            }
        });
    }
}
