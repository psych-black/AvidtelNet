package com.example.avidtelnet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class RecoveryActivity extends AppCompatActivity {

    private EditText recoveryEmailEditText;
    private Button recoveryButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recovery);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Inicializar las vistas
        recoveryEmailEditText = findViewById(R.id.recovery_email);
        recoveryButton = findViewById(R.id.recovery_button);

        // Configurar el botón de recuperación
        recoveryButton.setOnClickListener(view -> {
            String email = recoveryEmailEditText.getText().toString().trim();

            if (!email.isEmpty()) {
                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Correo de restablecimiento de contraseña enviado
                                Toast.makeText(RecoveryActivity.this, "Correo de restablecimiento enviado.", Toast.LENGTH_SHORT).show();
                                // Redirigir a la actividad de inicio de sesión
                                Intent intent = new Intent(RecoveryActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // Error al enviar el correo de restablecimiento
                                Toast.makeText(RecoveryActivity.this, "Error al enviar el correo de restablecimiento.", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(RecoveryActivity.this, "Por favor, ingresa tu correo electrónico.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}