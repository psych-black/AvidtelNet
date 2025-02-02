package com.example.avidtelnet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Agregar animaciones de transición

        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba);
        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo);

        ImageView imageView1 = findViewById(R.id.logo2);
        ImageView imageView2 = findViewById(R.id.homeSplash);

        imageView1.startAnimation(animation1);
        imageView2.startAnimation(animation2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Iniciar la actividad de inicio de sesión
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000); // 4000 milisegundos = 4 segundos
    }
}