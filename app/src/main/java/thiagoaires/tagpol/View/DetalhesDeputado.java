package thiagoaires.tagpol.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.facebook.drawee.backends.pipeline.Fresco;

import thiagoaires.tagpol.Modelo.Deputado;
import thiagoaires.tagpol.controle.Controlador;
import thiagoaires.tagpol.controle.Fragments.Detalhes.AnaliseFragment;
import thiaires.tagpol.R;
import thiagoaires.tagpol.controle.Fragments.Detalhes.DetalhesDeputadoFragment;
import thiagoaires.tagpol.controle.Fragments.Detalhes.VisualizacoesFragment;

public class DetalhesDeputado extends AppCompatActivity  implements BottomNavigationView.OnNavigationItemSelectedListener{
    private Deputado deputado;
    private Controlador c;
    private BottomNavigationView navigation;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.detalhesDeputado:
                fragment = new DetalhesDeputadoFragment();
                break;
            case R.id.visualizacoes:
                fragment = new VisualizacoesFragment();
                break;
            case R.id.analise:
                fragment = new AnaliseFragment();
                break;
        }
        Bundle b = new Bundle();
        b.putSerializable("dep", deputado);
        b.putSerializable("controlador", c);
        Log.i("FRAG", deputado.toString());
        if (fragment != null) {
            fragment.setArguments(b);
        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentLayout, fragment, "current")
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("TAG", "onCreate: carregou");
        Fresco.initialize(this);
        setContentView(R.layout.activity_detalhes_deputado);
        deputado = (Deputado) getIntent().getSerializableExtra("dep");
        c = (Controlador) getIntent().getSerializableExtra("controlador");
        Log.d("CTRL", "detalhes deputado: controlador" + c.getDeputados().getDados().size());
        int item = R.id.detalhesDeputado;

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);


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

}
