package thiagoaires.tagpol.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.facebook.drawee.backends.pipeline.Fresco;

import thiaires.tagpol.R;
import thiagoaires.tagpol.controle.Controlador;
import thiagoaires.tagpol.controle.Fragments.homescreen.DeputadoFragment;
import thiagoaires.tagpol.controle.Fragments.homescreen.EstadosFragment;
import thiagoaires.tagpol.controle.Fragments.homescreen.SobreFragment;

public class homeScreen extends AppCompatActivity  implements BottomNavigationView.OnNavigationItemSelectedListener{
    private Controlador c;
    private BottomNavigationView navigation;

    @Override
    public void onBackPressed() {
        Bundle b = new Bundle();
        b.putSerializable("controlador", c);
        Fragment f = new EstadosFragment();
        f.setArguments(b);
        loadFragment(f);
        navigation.setSelectedItemId(R.id.navigation_estados);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_home_screen);

        c = (Controlador) getIntent().getSerializableExtra("controlador");
        Log.d("CTRL", "homescreen: controlador" + c.getDeputados().getDados().size());

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        Bundle b = new Bundle();
        b.putSerializable("controlador", c);

        int item = R.id.navigation_estados;
        if(savedInstanceState != null){
            item = savedInstanceState.getInt("item");
        }

        navigation.setSelectedItemId(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("item", navigation.getSelectedItemId());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Bundle b = new Bundle();
        b.putInt("item", navigation.getSelectedItemId());

    }

    public void setEstado(String siglaUf){
        c.setEstado(siglaUf);
        Bundle b = new Bundle();
        b.putSerializable("controlador", c);
        Fragment fragment = new DeputadoFragment();
        fragment.setArguments(b);
        loadFragment(fragment);
        navigation.setSelectedItemId(R.id.navigation_deputados);
    }

    private boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentLayout, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_deputados:
                fragment = new DeputadoFragment();
                break;
            case R.id.navigation_estados:
                fragment = new EstadosFragment();
                break;
            case R.id.navigation_sobre:
                fragment = new SobreFragment();
                break;
        }
        Bundle b = new Bundle();
        b.putSerializable("controlador", c);
        fragment.setArguments(b);
        return loadFragment(fragment);
    }
}