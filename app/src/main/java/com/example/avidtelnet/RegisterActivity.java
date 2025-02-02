package com.example.avidtelnet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText cedulaEditText;
    private EditText telefonoEditText;
    private EditText locationEditText;
    private EditText emailEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private TextView loginRedirectText;
    private Button registerButton;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar Firebase Auth y Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Inicializar las vistas
        nameEditText = findViewById(R.id.signup_name);
        cedulaEditText = findViewById(R.id.signup_cedula);
        telefonoEditText = findViewById(R.id.signup_telefono);
        locationEditText = findViewById(R.id.signup_location);
        emailEditText = findViewById(R.id.signup_email);
        usernameEditText = findViewById(R.id.signup_username);
        passwordEditText = findViewById(R.id.signup_password);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        registerButton = findViewById(R.id.signup_button);

        // Configurar el TextView para redirigir a la pantalla de inicio de sesión
        loginRedirectText.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // Configurar el botón de registro
        registerButton.setOnClickListener(view -> {
            String name = nameEditText.getText().toString().trim();
            String cedula = cedulaEditText.getText().toString().trim();
            String telefono = telefonoEditText.getText().toString().trim();
            String location = locationEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (!name.isEmpty() && !cedula.isEmpty() && !telefono.isEmpty() && !location.isEmpty() && !email.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
                mAuth.fetchSignInMethodsForEmail(email)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                boolean emailInUse = !task.getResult().getSignInMethods().isEmpty();
                                if (emailInUse) {
                                    Toast.makeText(RegisterActivity.this, "El correo electrónico ya está en uso. Por favor, usa otro correo o inicia sesión.", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Crear cuenta nueva
                                    mAuth.createUserWithEmailAndPassword(email, password)
                                            .addOnCompleteListener(this, createTask -> {
                                                if (createTask.isSuccessful()) {
                                                    // Registro exitoso, almacenar información en Firestore
                                                    FirebaseUser user = mAuth.getCurrentUser();
                                                    Map<String, Object> userInfo = new HashMap<>();
                                                    userInfo.put("name", name);
                                                    userInfo.put("cedula", cedula);
                                                    userInfo.put("telefono", telefono);
                                                    userInfo.put("location", location);
                                                    userInfo.put("email", email);
                                                    userInfo.put("username", username);

                                                    db.collection("users").document(user.getUid())
                                                            .set(userInfo)
                                                            .addOnSuccessListener(aVoid -> {
                                                                // Almacenamiento exitoso, redirigir a la actividad de inicio de sesión
                                                                Toast.makeText(RegisterActivity.this, "Registro exitoso. Por favor, inicia sesión.", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                                startActivity(intent);
                                                                finish();
                                                            })
                                                            .addOnFailureListener(e -> {
                                                                // Error al almacenar
                                                                Toast.makeText(RegisterActivity.this, "Error al almacenar la información del usuario.", Toast.LENGTH_SHORT).show();
                                                            });
                                                } else {
                                                    // Error de registro
                                                    Toast.makeText(RegisterActivity.this, "Error de registro.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            } else {
                                // Error al verificar el correo electrónico
                                Toast.makeText(RegisterActivity.this, "Error al verificar el correo electrónico.", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(RegisterActivity.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}