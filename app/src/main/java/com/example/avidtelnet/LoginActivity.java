package com.example.avidtelnet;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.cardview.widget.CardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private CheckBox showPasswordCheckBox;
    private TextView signupRedirectText;
    private Button loginButton;
    private TextView forgotPasswordText;
    private ImageView logoImageView;
    private TextView loginTextView;
    private LinearLayout loginLinearLayout;
    private CardView cardView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Inicializar las vistas
        emailEditText = findViewById(R.id.login_email);
        passwordEditText = findViewById(R.id.login_password);
        showPasswordCheckBox = findViewById(R.id.show_password);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        loginButton = findViewById(R.id.login_button);
        forgotPasswordText = findViewById(R.id.forgot_password);
        logoImageView = findViewById(R.id.logoImageView);
        loginTextView = findViewById(R.id.loginTextView);
        loginLinearLayout = findViewById(R.id.loginLinearLayout);
        cardView = findViewById(R.id.cardView);

        // Cargar las animaciones
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation translateUp = AnimationUtils.loadAnimation(this, R.anim.translate_up);

        // Iniciar las animaciones en el logo
        logoImageView.startAnimation(fadeIn);
        logoImageView.startAnimation(translateUp);

        // Animar el TextView de inicio de sesión
        loginTextView.startAnimation(fadeIn);
        loginTextView.startAnimation(translateUp);

        // Animar el LinearLayout de inicio de sesión
        loginLinearLayout.startAnimation(fadeIn);
        loginLinearLayout.startAnimation(translateUp);

        // Animar el CardView
        cardView.startAnimation(fadeIn);
        cardView.startAnimation(translateUp);

        // Animar los elementos del LinearLayout
        emailEditText.startAnimation(translateUp);
        passwordEditText.startAnimation(translateUp);
        showPasswordCheckBox.startAnimation(translateUp);
        signupRedirectText.startAnimation(translateUp);
        loginButton.startAnimation(translateUp);
        forgotPasswordText.startAnimation(translateUp);

        // Configurar el CheckBox para mostrar/ocultar la contraseña
        showPasswordCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Mostrar la contraseña
                passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                // Ocultar la contraseña
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            // Mover el cursor al final del texto
            passwordEditText.setSelection(passwordEditText.length());
        });

        // Configurar el TextView para redirigir a la pantalla de registro
        signupRedirectText.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Configurar el TextView para redirigir a la pantalla de recuperación de cuenta
        forgotPasswordText.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RecoveryActivity.class);
            startActivity(intent);
        });

        // Configurar el botón de inicio de sesión
        loginButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (!email.isEmpty() && !password.isEmpty()) {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Iniciar sesión exitoso
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso.", Toast.LENGTH_SHORT).show();
                                // Redirigir a DatosActivity
                                Intent intent = new Intent(LoginActivity.this, DatosActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // Error de inicio de sesión
                                Toast.makeText(LoginActivity.this, "Error de autenticación.", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(LoginActivity.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}