package com.example.avidtelnet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DatosActivity extends AppCompatActivity {

    private TextView greetingTextView;
    private TextView greetingTextView2;
    private EditText editName, editCedula, editTelefono, editLocation, editEmail, editClave;
    private Button saveButton, button1, button2, button3;
    private ImageView logoImageView;
    private LinearLayout topButtonsLayout;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_datos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar Firebase Auth y Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();

        // Inicializar las vistas
        greetingTextView = findViewById(R.id.greetingTextView);
        greetingTextView2 = findViewById(R.id.greetingTextView2);
        logoImageView = findViewById(R.id.logoImageView);
        editName = findViewById(R.id.editName);
        editCedula = findViewById(R.id.editCedula);
        editTelefono = findViewById(R.id.editTelefono);
        editLocation = findViewById(R.id.editLocation);
        editEmail = findViewById(R.id.editEmail);
        editClave = findViewById(R.id.editClave);
        saveButton = findViewById(R.id.saveButton);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        topButtonsLayout = findViewById(R.id.topButtonsLayout);

        // Cargar las animaciones
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation translateUp = AnimationUtils.loadAnimation(this, R.anim.translate_up);

        // Iniciar las animaciones en el logo y los TextView de saludo
        logoImageView.startAnimation(fadeIn);
        logoImageView.startAnimation(translateUp);
        greetingTextView.startAnimation(fadeIn);
        greetingTextView.startAnimation(translateUp);
        greetingTextView2.startAnimation(fadeIn);
        greetingTextView2.startAnimation(translateUp);

        // Animar los botones individualmente
        button1.startAnimation(translateUp);
        button2.startAnimation(translateUp);
        button3.startAnimation(translateUp);

        // Animar los EditText y el Button
        editName.startAnimation(translateUp);
        editCedula.startAnimation(translateUp);
        editTelefono.startAnimation(translateUp);
        editLocation.startAnimation(translateUp);
        editEmail.startAnimation(translateUp);
        editClave.startAnimation(translateUp);
        saveButton.startAnimation(translateUp);

        // Mostrar las vistas de saludo
        greetingTextView.setVisibility(View.VISIBLE);
        greetingTextView2.setVisibility(View.VISIBLE);

        // Obtener y mostrar datos del usuario
        if (currentUser != null) {
            DocumentReference docRef = db.collection("users").document(currentUser.getUid());
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        editName.setText(document.getString("name"));
                        editCedula.setText(document.getString("cedula"));
                        editTelefono.setText(document.getString("telefono"));
                        editLocation.setText(document.getString("location"));
                        editEmail.setText(document.getString("email"));
                    } else {
                        Toast.makeText(DatosActivity.this, "No se encontraron datos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DatosActivity.this, "Error al obtener datos", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Configurar el botón de guardar
        saveButton.setOnClickListener(view -> {
            String name = editName.getText().toString();
            String cedula = editCedula.getText().toString();
            String telefono = editTelefono.getText().toString();
            String location = editLocation.getText().toString();
            String email = editEmail.getText().toString();
            String clave = editClave.getText().toString();

            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(cedula) && !TextUtils.isEmpty(telefono) && !TextUtils.isEmpty(location) && !TextUtils.isEmpty(email)) {
                DocumentReference docRef = db.collection("users").document(currentUser.getUid());
                docRef.update(
                        "name", name,
                        "cedula", cedula,
                        "telefono", telefono,
                        "location", location,
                        "email", email
                ).addOnSuccessListener(aVoid -> {
                    // Si el usuario actualiza su correo electrónico, también actualizamos FirebaseAuth
                    currentUser.updateEmail(email).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            if (!TextUtils.isEmpty(clave)) {
                                currentUser.updatePassword(clave).addOnCompleteListener(updateTask -> {
                                    if (updateTask.isSuccessful()) {
                                        Toast.makeText(DatosActivity.this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(DatosActivity.this, "Error al actualizar la contraseña", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(DatosActivity.this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(DatosActivity.this, "Error al actualizar el correo electrónico", Toast.LENGTH_SHORT).show();
                        }
                    });
                }).addOnFailureListener(e -> {
                    Toast.makeText(DatosActivity.this, "Error al actualizar los datos", Toast.LENGTH_SHORT).show();
                });
            } else {
                Toast.makeText(DatosActivity.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar los botones de navegación
        button1.setOnClickListener(view -> {
            Intent intent = new Intent(DatosActivity.this, DatosActivity.class);
            startActivity(intent);
        });

        button2.setOnClickListener(view -> {
            Intent intent = new Intent(DatosActivity.this, ServicioActivity.class);
            startActivity(intent);
        });

        button3.setOnClickListener(view -> {
            Intent intent = new Intent(DatosActivity.this, ContactoActivity.class);
            startActivity(intent);
        });
    }
}