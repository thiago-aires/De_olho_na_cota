package thiagoaires.tagpol.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import thiaires.tagpol.R;
import thiagoaires.tagpol.controle.Controlador;

public class SplashScreen extends AppCompatActivity {
    private Controlador c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        c = new Controlador();
        c.carregaDeputados();

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, homeScreen.class);
                i.putExtra("controlador", c);
                startActivity(i);
            }
        }, 6500);
    }
}
