package thiagoaires.tagpol.controle.Fragments.homescreen;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import thiaires.tagpol.R;
import thiagoaires.tagpol.controle.Adapter.EstadoAdapter;
import thiagoaires.tagpol.controle.Controlador;

public class EstadosFragment extends Fragment {
    private Controlador c;
    private View v;
    private RecyclerView recycler;
    private RecyclerView.LayoutManager layoutManager;
    private Context context;
    private Activity a;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        v = inflater.inflate(R.layout.estados_fragment, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle b = getArguments();
        if (b != null) {
            c = (Controlador) b.getSerializable("controlador");
            Log.d("CTRL", "estado fragment: controlador" + c.getDeputados().getDados().size());
        }

        recycler = (RecyclerView) v.findViewById(R.id.recycler);
        Configuration c = getResources().getConfiguration();
        int col = 3;
        if(c.orientation == Configuration.ORIENTATION_LANDSCAPE) col = 5;
        layoutManager = new GridLayoutManager(context, col, GridLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);
        EstadoAdapter estadoAdapter = new EstadoAdapter(context, getActivity());
        recycler.setAdapter(estadoAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        a = getActivity();
    }
}
