package com.example.avidtelnet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.widget.ImageView;

public class ContactoActivity extends AppCompatActivity {

    private Button button1, button2, button3;
    private ImageView imageWeb;
    private ImageView paymentImage;
    private ImageView imageSpeedTest, whatsappImage, whatsappImage2, iconWhatsapp, iconFacebook, iconInstagram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contacto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar las vistas
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        imageWeb = findViewById(R.id.image_web);
        paymentImage = findViewById(R.id.payment_image);
        imageSpeedTest = findViewById(R.id.image_speed_test);
        whatsappImage = findViewById(R.id.whatsapp_image);
        whatsappImage2 = findViewById(R.id.whatsapp_image2);
        iconWhatsapp = findViewById(R.id.icon_whatsapp);
        iconFacebook = findViewById(R.id.icon_facebook);
        iconInstagram = findViewById(R.id.icon_instagram);

        // Configurar los botones de navegación
        button1.setOnClickListener(view -> {
            Intent intent = new Intent(ContactoActivity.this, DatosActivity.class);
            startActivity(intent);
        });

        button2.setOnClickListener(view -> {
            Intent intent = new Intent(ContactoActivity.this, ServicioActivity.class);
            startActivity(intent);
        });

        button3.setOnClickListener(view -> {
            Intent intent = new Intent(ContactoActivity.this, ContactoActivity.class);
            startActivity(intent);
        });

        // Redirigir a la página web Avidtel
        imageWeb.setOnClickListener(view -> {
            String url = "https://avidtel.net/";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });

        // Redirigir a la página web Avidtel de pago
        paymentImage.setOnClickListener(view -> {
            String url = "https://app.grupo-bit.com/intranet/#/payment/YXZpZHRlbF9zYXM";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });

        // Configurar el OnClickListener para realizar la prueba de velocidad
        imageSpeedTest.setOnClickListener(view -> {
            Intent intent = new Intent(ContactoActivity.this, SpeedTestActivity.class);
            startActivity(intent);
        });

        // Redirigir a chat de Soporte Técnico en WhatsApp
        whatsappImage.setOnClickListener(view -> {
            String url = "https://api.whatsapp.com/send?phone=3112448956&text=Hola,%20necesito%20asistencia%20técnica";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });

        // Redirigir a chat de Asesoría Comercial en WhatsApp
        whatsappImage2.setOnClickListener(view -> {
            String url = "https://api.whatsapp.com/send?phone=3158048478&text=Hola,%20necesito%20asesoría%20comercial";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });

        // Redirigir a WhatsApp noticias de Asesoría Avidtel
        iconWhatsapp.setOnClickListener(view -> {
            String url = "https://whatsapp.com/channel/0029Vak01r7K0IBfdlQRM701";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });

        // Redirigir a Facebook Avidtel
        iconFacebook.setOnClickListener(view -> {
            String url = "https://www.facebook.com/AvidtelNet";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });

        // Redirigir a Instagram Avidtel
        iconInstagram.setOnClickListener(view -> {
            String url = "https://www.instagram.com/avidtelnet/";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });
    }
}