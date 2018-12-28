package thiagoaires.tagpol.controle.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import thiaires.tagpol.R;
import thiagoaires.tagpol.View.homeScreen;

public class EstadoAdapter extends RecyclerView.Adapter<EstadoAdapter.cardViewHolder>{
    private ArrayList<estado> estados;
    private LayoutInflater mLayoutInflater;
    private Context context;
    private Activity a;

    public EstadoAdapter(Context context, Activity a) {
        this.a = a;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        estados = new ArrayList<>();
        estados.add(new estado("Brasil", R.drawable.brasil, "BR"));
        estados.add(new estado("Acre", R.drawable.acre, "AC"));
        estados.add(new estado("Alagoas", R.drawable.alagoas,"AL"));
        estados.add(new estado("Amapa", R.drawable.amapa,"AP"));
        estados.add(new estado("Amazonas", R.drawable.amazonas, "AM"));
        estados.add(new estado("Bahia", R.drawable.bahia, "BA"));
        estados.add(new estado("Brasília", R.drawable.brasilia, "DF"));
        estados.add(new estado("Ceará", R.drawable.ceara, "CE"));
        estados.add(new estado("Espírito\nSanto", R.drawable.espiritosanto, "ES"));
        estados.add(new estado("Goiás", R.drawable.goias, "GO"));
        estados.add(new estado("Maranhão", R.drawable.maranhao, "MA"));
        estados.add(new estado("Mato\nGrosso", R.drawable.matogrosso, "MT"));
        estados.add(new estado("Mato\nGrosso do Sul", R.drawable.matogrossodosul, "MS"));
        estados.add(new estado("Minas\nGerais", R.drawable.minasgerais, "MG"));
        estados.add(new estado("Pará", R.drawable.para, "PA"));
        estados.add(new estado("Paraíba", R.drawable.paraiba, "PB"));
        estados.add(new estado("Paraná", R.drawable.parana, "PR"));
        estados.add(new estado("Pernambuco", R.drawable.pernambuco, "PE"));
        estados.add(new estado("Piauí", R.drawable.piaui, "PI"));
        estados.add(new estado("Rio de\nJaneiro", R.drawable.riodejaneiro, "RJ"));
        estados.add(new estado("Rio Grande\ndo Norte", R.drawable.riograndedonorte, "RN"));
        estados.add(new estado("Rio Grande\ndo Sul", R.drawable.riograndedosul, "RS"));
        estados.add(new estado("Rondônia", R.drawable.rondonia, "RO"));
        estados.add(new estado("Roraima", R.drawable.roraima, "RR"));
        estados.add(new estado("Santa\nCatarina", R.drawable.santacatarina, "SC"));
        estados.add(new estado("São\nPaulo", R.drawable.saopaulo, "SP"));
        estados.add(new estado("Sergipe", R.drawable.sergipe, "SE"));
        estados.add(new estado("Tocantis", R.drawable.tocantins, "TO"));
    }

    @NonNull
    @Override
    public EstadoAdapter.cardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.card_estado, parent, false);
        cardViewHolder mvh = new cardViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull EstadoAdapter.cardViewHolder holder, final int position) {
        holder.nomeEstado.setText(estados.get(position).getNome());
        holder.img.setActualImageResource(estados.get(position).getImg());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeScreen h = (homeScreen) a;
                h.setEstado(estados.get(position).getSiglaUf());
            }
        });
    }

    @Override
    public int getItemCount() {
        return estados.size();
    }

    public class cardViewHolder extends RecyclerView.ViewHolder {
        public TextView nomeEstado;
        public CardView cardView;
        public SimpleDraweeView img;

        public cardViewHolder(View itemView) {
            super(itemView);
            nomeEstado = (TextView) itemView.findViewById(R.id.nomeEstado);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            img = (SimpleDraweeView) itemView.findViewById(R.id.estado);
        }
    }

    public class estado{
        private String nome;

        private String siglaUf;

        public String getSiglaUf() {
            return siglaUf;
        }

        public void setSiglaUf(String siglaUf) {
            this.siglaUf = siglaUf;
        }

        public estado(String nome, int img, String siglaUf) {
            this.nome = nome;
            this.siglaUf = siglaUf;
            this.img = img;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public int getImg() {
            return img;
        }

        public void setImg(int img) {
            this.img = img;
        }

        private int img;
    }
}
