package thiagoaires.tagpol.controle.Fragments.homescreen;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.security.Key;

import thiagoaires.tagpol.Modelo.Deputado;
import thiagoaires.tagpol.Modelo.Deputados;
import thiaires.tagpol.R;
import thiagoaires.tagpol.controle.Adapter.DeputadoAdapter;
import thiagoaires.tagpol.controle.Controlador;

public class DeputadoFragment extends Fragment{
    private Controlador c;
    private View v;
    private RecyclerView recycler;
    private DeputadoAdapter deputadoAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Deputados deputados;
    private int col;
    private boolean flag_pin;
    private Context context;
    private TextView filtroNome;
    private TextView filtroPartido;
    private Deputado deputado;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        context = getContext();
        v = inflater.inflate(R.layout.deputado_fragment, container, false);
        filtroNome = (TextView) v.findViewById(R.id.filtroNome);
        filtroPartido = (TextView) v.findViewById(R.id.filtroPartido);
        flag_pin = false; //por padrao é false
        deputado = new Deputado(); //por padrao é ninguem
        filtroNome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filtrarNome(editable.toString());
            }
        });

        filtroPartido.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filtrarPartido(editable.toString());
            }
        });
        return v;
    }

    private void filtrarPartido(String texto){
        deputadoAdapter.filtrarPartido(texto);
    }


    private void filtrarNome(String texto){
        deputadoAdapter.filtrarNome(texto);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("TAG", "onstart: entrei");
        Bundle b = getArguments();
        if (b != null) {
            c = (Controlador) b.getSerializable("controlador");
            Log.d("CTRL", "deputado fragment: controlador" + c.getDeputados().getDados().size());
            try {
                flag_pin = (boolean) b.getSerializable("flag_pin"); // se tiver altera
                deputado = (Deputado) b.getSerializable("dep1"); // se tiver altera
            } catch (Exception e){
                e.printStackTrace();
            }
        }else {
            Log.i("CTRL", "bundle controlador nulo fragment deputado");
            c = new Controlador();
            c.carregaDeputados();
        }

        deputados = c.getDeputadosPorEstado();
        Log.i("DEP", "size deputados: " + deputados.getDados().size());
        recycler = (RecyclerView) v.findViewById(R.id.recycler);
        Configuration cf = getResources().getConfiguration();
        if(cf.orientation == Configuration.ORIENTATION_LANDSCAPE) col = 5;
        else col = 3;
        layoutManager = new GridLayoutManager(context, col, GridLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);
        deputadoAdapter = new DeputadoAdapter(context, deputados);
        deputadoAdapter.setFlag_pin(flag_pin, deputado);
        deputadoAdapter.setControlador(c);
        recycler.setAdapter(deputadoAdapter);
        recycler.requestFocus();
    }
}
