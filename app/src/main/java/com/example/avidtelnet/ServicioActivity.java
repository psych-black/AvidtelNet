package com.example.avidtelnet;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;

public class ServicioActivity extends AppCompatActivity {

    private Button button1, button2, button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_servicio);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar los botones de navegación
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        // Configurar los botones de navegación
        button1.setOnClickListener(view -> {
            Intent intent = new Intent(ServicioActivity.this, DatosActivity.class);
            startActivity(intent);
        });

        button2.setOnClickListener(view -> {
            Intent intent = new Intent(ServicioActivity.this, ServicioActivity.class);
            startActivity(intent);
        });

        button3.setOnClickListener(view -> {
            Intent intent = new Intent(ServicioActivity.this, ContactoActivity.class);
            startActivity(intent);
        });
    }
}