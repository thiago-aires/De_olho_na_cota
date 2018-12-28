package thiagoaires.tagpol.controle.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thiagoaires.tagpol.ClienteWsCamara.InicializadorRetrofit;
import thiagoaires.tagpol.Modelo.Deputado;
import thiagoaires.tagpol.Modelo.Deputados;
import thiagoaires.tagpol.View.Visualization_just_webview;
import thiagoaires.tagpol.controle.Controlador;
import thiaires.tagpol.R;
import thiagoaires.tagpol.View.DetalhesDeputado;


public class DeputadoAdapter extends RecyclerView.Adapter<DeputadoAdapter.cardViewHolder>{
    private Context context;
    private Deputados deputados;
    private Deputados backupListaDeputados;
    private LayoutInflater mLayoutInflater;
    private boolean flag_pin; //flag para saber se veio da comparação
    private Controlador c;
    private Deputado auxdep;

    public void filtrarPartido(String texto){
        Deputados novo = new Deputados();
        //filtrar deputados aqui
        if (texto.equals("")){
            deputados = backupListaDeputados;
        } else{
            for(Deputados.Dado d : deputados.getDados()){
                //Log.d("TAG", "filtrar: nome: " + d.getNome() + ", texto: " + texto);
                if(d.getSiglaPartido().toLowerCase().contains(texto.toLowerCase())){
                    novo.addDado(d);
                }
            }
            deputados = novo;
        }
        notifyDataSetChanged();
    }

    public void filtrarNome(String texto){
        Deputados novo = new Deputados();
        //filtrar deputados aqui
        if (texto.equals("")){
            deputados = backupListaDeputados;
        } else{
            for(Deputados.Dado d : deputados.getDados()){
                //Log.d("TAG", "filtrar: nome: " + d.getNome() + ", texto: " + texto);
                if(d.getNome().toLowerCase().contains(texto.toLowerCase())){
                    novo.addDado(d);
                }
            }
            deputados = novo;
        }
        notifyDataSetChanged();
    }

    public void restauraDeputados(){
        this.deputados = backupListaDeputados;
    }

    public void setContext(Context context){
        this.context = context;
    }

    public Deputados getDeputados() {
        return deputados;
    }

    public void setDeputados(Deputados deputados){
        this.deputados = deputados;
        this.backupListaDeputados = deputados;
    }


    public DeputadoAdapter(Context mContext, Deputados deputados) {
        this.context = mContext;
        this.deputados = deputados;
        this.backupListaDeputados = deputados;
        this.flag_pin = false;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setFlag_pin(boolean f, Deputado deputado){
        this.flag_pin = f;
        this.auxdep = deputado;
        if(flag_pin) restauraDeputados();
    }

    @Override
    public cardViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.card_deputado, viewGroup, false);
        cardViewHolder mvh = new cardViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(cardViewHolder holder, final int position) {
        Deputados.Dado d = this.deputados.getDados().get(position);
        String[] nome = d.getNome().split("\\s");
        for(int i=0;i<nome.length;i++)
            nome[i] = nome[i].substring(0,1).toUpperCase().concat(nome[i].substring(1).toLowerCase());
        final String aux;
        if(nome.length > 1)
            if(nome[1].toUpperCase().equals("DE") ||
                    nome[1].toUpperCase().equals("DO") ||
                    nome[1].toUpperCase().equals("DA"))
                aux = nome[0] + " " + nome[1] + "\n" + nome[2];
            else aux = nome[0] + "\n" + nome[1];
        else
            aux = nome[0];

        holder.img.setImageURI(d.getUrlFoto());
        holder.nomeDeputado.setText(aux);
        holder.partidoDeputado.setText(d.getSiglaPartido());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Deputados.Dado a = deputados.getDados().get(position);

                if(!flag_pin) {

                    try {
                        final Call<Deputado> call = new InicializadorRetrofit().getCamaraService().getDeputado(a.getId()); //cria uma requisição
                        call.enqueue(new Callback<Deputado>() { // enqueue significa uma requisição assincrona pois pode demorar e nao pode travar o app
                            @Override
                            public void onFailure(Call<Deputado> call, Throwable t) {
                                // metodo caso falhe a requisição
                                System.err.println("ERRO na chamada, exceção: " + t.toString());
                                Toast.makeText(context, "Servidor da Câmara dos Deputados indisponível", Toast.LENGTH_LONG).show();
                            }

                            @SuppressLint("ResourceType")
                            @Override
                            public void onResponse(Call<Deputado> call, Response<Deputado> rspns) {
                                try {
                                    Deputado da = rspns.body();
                                    //System.out.println("da.tostring " + da.toString());
                                    Bundle extras = new Bundle();
                                    extras.putSerializable("dep", da);
                                    extras.putSerializable("controlador", c);
                                    Intent i = new Intent(context, DetalhesDeputado.class);
                                    i.putExtras(extras);
                                    context.startActivity(i);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    // comparação
                    //Toast.makeText(context, "QUEM DISSE Q NAO VAI TER COMPARISON " + auxdep.getDados().getNomeCivil() + " " + a.getNome(), Toast.LENGTH_SHORT).show();
                    Bundle extras = new Bundle();
                    extras.putSerializable("iddep1", a.getId() + "");
                    extras.putSerializable("nomedep1", a.getNome());
                    extras.putSerializable("iddep2", auxdep.getDados().getId() + "");
                    extras.putSerializable("nomedep2", auxdep.getDados().getNomeCivil());
                    extras.putSerializable("controlador", c);
                    Intent i = new Intent(context, Visualization_just_webview.class);
                    i.putExtras(extras);
                    context.startActivity(i);
                }
            }
        });
    }

    private String formataNome(String nome) {
        String[] n = nome.split("\\s");
        String res = "";

        for(String aux : n){
            char[] c = (aux.toLowerCase()).toCharArray();
            c[0] = (char) (c[0] + 32);
            res+= c.toString() + " ";
        }

        return res;
    }


    @Override
    public int getItemCount() {
        return this.deputados.getDados().size();
    }

    public void setControlador(Controlador controlador) {
        this.c = controlador;
    }


    public class cardViewHolder extends RecyclerView.ViewHolder {
        public TextView nomeDeputado;
        public TextView partidoDeputado;
        public CardView cardView;
        public SimpleDraweeView img;

        public cardViewHolder(View itemView) {
            super(itemView);
            nomeDeputado = (TextView) itemView.findViewById(R.id.nomeDeputado);
            partidoDeputado = (TextView) itemView.findViewById(R.id.partidoDeputado);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            img = (SimpleDraweeView) itemView.findViewById(R.id.imgDeputadoFresco);
        }
    }
}
