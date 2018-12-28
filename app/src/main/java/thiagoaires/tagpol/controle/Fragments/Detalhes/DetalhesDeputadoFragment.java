package thiagoaires.tagpol.controle.Fragments.Detalhes;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import thiagoaires.tagpol.Modelo.Deputado;
import thiagoaires.tagpol.Modelo.Despesas;
import thiaires.tagpol.R;

public class DetalhesDeputadoFragment extends Fragment {
    private Deputado deputado;
    private SimpleDraweeView imgDeputado;
    private TextView nomeDeputado;
    private TextView partidoDeputado;
    private TextView estadoDeputado;
    private TextView telefoneGabinete;
    private TextView emailGabinete;
    private TextView condicaoEleitoral;
    private TextView cpfDeputado;
    private TextView dataNascimento;
    private TextView ufNascimento;
    private TextView municipioNascimento;
    private TextView escolaridade;
    private TextView site;
    private TextView redesocial;
    private TextView situacao;
    private TextView descricaoStatus;
    private Despesas despesas;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.detalhes_deputado_fragment, container, false);
        context = getContext();

        imgDeputado = (SimpleDraweeView) v.findViewById(R.id.imgDeputado);
        nomeDeputado = (TextView) v.findViewById(R.id.nomeDeputado);
        partidoDeputado = (TextView) v.findViewById(R.id.partidoDeputado);
        estadoDeputado = (TextView) v.findViewById(R.id.estadoDeputado);
        telefoneGabinete = (TextView) v.findViewById(R.id.telefoneGabinete);
        emailGabinete = (TextView) v.findViewById(R.id.emailGabinete);
        condicaoEleitoral = (TextView) v.findViewById(R.id.condicaoEleitoral);
        cpfDeputado = (TextView) v.findViewById(R.id.cpfDeputado);
        dataNascimento = (TextView) v.findViewById(R.id.dataNascimentoDeputado);
        ufNascimento= (TextView) v.findViewById(R.id.ufNascimentoDeputado);
        municipioNascimento= (TextView) v.findViewById(R.id.municipioNascimentoDeputado);
        escolaridade= (TextView) v.findViewById(R.id.escolaridadeDeputado);
        site= (TextView) v.findViewById(R.id.siteDeputado);
        redesocial= (TextView) v.findViewById(R.id.redeSocialDeputado);
        situacao= (TextView) v.findViewById(R.id.situacaoDeputado);
        descricaoStatus= (TextView) v.findViewById(R.id.descricaoStatus);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle b = getArguments();

        if (b != null) {
            deputado = (Deputado) b.getSerializable("dep");
        } else {
            Toast.makeText(context, "OPS, ocorreu algum problema!!", Toast.LENGTH_LONG).show();
            deputado = new Deputado();
        }
        atualizaTela();
    }

    private void atualizaTela() {
        try {
            imgDeputado.setImageURI(deputado.getDados().getUltimoStatus().getUrlFoto());
            if (!deputado.getDados().getNomeCivil().equals(""))
                nomeDeputado.setText(("Nome Deputado: " + deputado.getDados().getNomeCivil()));
            if (!deputado.getDados().getUltimoStatus().getSiglaPartido().equals(""))
                partidoDeputado.setText(deputado.getDados().getUltimoStatus().getSiglaPartido().toUpperCase());
            if (!deputado.getDados().getUltimoStatus().getSiglaUf().equals(""))
                estadoDeputado.setText(("Eleito pelo estado: " + deputado.getDados().getUltimoStatus().getSiglaUf().toUpperCase()));
            if (!deputado.getDados().getUltimoStatus().getGabinete().getTelefone().equals(""))
                telefoneGabinete.setText(("Telefone do Gabinete: " + deputado.getDados().getUltimoStatus().getGabinete().getTelefone()));
            if (!deputado.getDados().getUltimoStatus().getGabinete().getEmail().equals(""))
                emailGabinete.setText(("Email do Gabinete: " + deputado.getDados().getUltimoStatus().getGabinete().getEmail().toLowerCase()));
            if (!deputado.getDados().getUltimoStatus().getCondicaoEleitoral().equals(""))
                condicaoEleitoral.setText(("Condição Eleitoral: " + deputado.getDados().getUltimoStatus().getCondicaoEleitoral()));
            if (!deputado.getDados().getCpf().equals(""))
                cpfDeputado.setText(("CPF: " + deputado.getDados().getCpf()));
            if (!deputado.getDados().getDataNascimento().equals(""))
                dataNascimento.setText(("Data de Nascimento: " + deputado.getDados().getDataNascimento()));
            if (!deputado.getDados().getUfNascimento().equals(""))
                ufNascimento.setText(("Estado de Nascimento: " + deputado.getDados().getUfNascimento().toUpperCase()));
            if (!deputado.getDados().getMunicipioNascimento().equals(""))
                municipioNascimento.setText(("Município de nascimento: " + deputado.getDados().getMunicipioNascimento()));
            if (!deputado.getDados().getEscolaridade().equals(""))
                escolaridade.setText(("Escolaridade: " + deputado.getDados().getEscolaridade()));
            site.setText(deputado.getDados().getUrlWebsite());
            if (!deputado.getDados().getRedeSocial().isEmpty())
                redesocial.setText(("Rede Social: " + deputado.getDados().getRedeSocial().toString()));
            situacao.setText(deputado.getDados().getUltimoStatus().getSituacao());
            descricaoStatus.setText(deputado.getDados().getUltimoStatus().getDescricaoStatus());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}