package thiagoaires.tagpol.controle.Fragments.Detalhes;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.soloader.DoNotOptimize;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import thiagoaires.tagpol.Modelo.Deputado;
import thiagoaires.tagpol.controle.Fragments.loadingFragment;
import thiaires.tagpol.R;

public class AnaliseFragment extends Fragment {
    private Deputado deputado;
    private WebView webView;
    private TextView partidoDeputado;
    private TextView nomeDeputado;
    private TextView idLegislatura;
    private TextView dtInicio;
    private TextView dtFim;
    private TextView tot;
    private ScrollView s;
    private TextView bolsa;
    private TextView salmin;
    private TextView combustivel;
    private TextView transporte;
    private TextView alimentacao;
    private TextView manutencao;
    private TextView assinatura;
    private TextView apoio;
    private TextView telefonia;
    private TextView postal;
    private TextView hospedagem;
    private TextView seguranca;
    private TextView cursos;
    private TextView moradia;
    private boolean f;
    private SimpleDraweeView imgDeputado;
    private ArrayList<categoria> gastos;
    private ArrayList<categoria> colapsada; //essa variavel tem as categorias de forma colapsada, porem os itens de despesa sao null
    private ano analiseAnual;

    public void openLoading(){
        loadingFragment f = new loadingFragment();
        f.show(getActivity().getSupportFragmentManager().beginTransaction(), "loading");
    }


    public void closeLoading(){
        loadingFragment f = (loadingFragment) getActivity().getSupportFragmentManager().findFragmentByTag("loading");
        if(f != null) {f.dismiss(); getActivity().getSupportFragmentManager().beginTransaction().remove(f);}

        Log.d("CTRL", "closeLoading: colapsada " + colapsada.toString());
    }

    @SuppressLint("StringFormatMatches")
    private void attAnalise() {

        Log.d("CTRL", "attAnalise: " + total + " " + totalGas);
        Locale l = new Locale("pt", "BR");

        String n = deputado.getDados().getUltimoStatus().getNome();
        String a = NumberFormat.getCurrencyInstance(l).format(Math.floor(total));

        tot.setText(getString(R.string.total, n, a));
        a =  Math.round((total/954)) + "";
        salmin.setText(getString(R.string.sm, a));
        a = Math.round((total/89)) + "";
        bolsa.setText(getString(R.string.bf, a));
        a = NumberFormat.getCurrencyInstance(l).format(Math.floor(totalGas));
        n = Math.round((totalGas/4.62)) + "";
        combustivel.setText(getString(R.string.gas, a, n));
        Log.d("CTRL", "attAnalise: " + analiseAnual.toString());
        calcularMediaCategorias();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.analise_fragment, container, false);

        Bundle b = getArguments();
        if (b != null) {
            deputado = (Deputado) b.getSerializable("dep");
        } else {
            deputado = new Deputado();
        }
        Log.d("CTRL", "DEPUTADO: " + deputado.getDados().toString());
        webView = (WebView) v.findViewById(R.id.webView);
        gastos = new ArrayList<>();
        total = 0.0;
        f = false;
        analiseAnual = new ano();
        totalGas = 0.0;
        colapsada = new ArrayList<>();
        bind(v);
        atualizaTela();
        return v;
    }

    public class ano {
        public class catAnual {
            private Double[] valor; // array com os valores da categoria

            public catAnual(Double[] valor) {
                this.valor = valor;
            }

            public Double[] getValor() {
                return valor;
            }

            public void setValor(Double[] valor) {
                this.valor = valor;
            }
        }
        private ArrayList<catAnual> a;

        public ano(){
            this.a = new ArrayList<>();
        }
        public ano(Double[] ano){
            this.a = new ArrayList<>();
            this.a.add(new catAnual(ano));
        }

        public void addCatAnual(Double[] a){
            this.a.add(new catAnual(a));
        }

        public ArrayList<catAnual> getA() {
            return a;
        }

        public void setA(ArrayList<catAnual> a) {
            this.a = a;
        }
    }

    public void calcularMediaCategorias(){
        //Log.d("CTRL", "calcularMediaCategorias: COLPSO" + colapsada.toString());
        transporte.setVisibility(View.INVISIBLE);
        alimentacao.setVisibility(View.INVISIBLE);
        manutencao.setVisibility(View.INVISIBLE);
        assinatura.setVisibility(View.INVISIBLE);
        apoio.setVisibility(View.INVISIBLE);
        telefonia.setVisibility(View.INVISIBLE);
        postal.setVisibility(View.INVISIBLE);
        hospedagem.setVisibility(View.INVISIBLE);
        seguranca.setVisibility(View.INVISIBLE);
        cursos.setVisibility(View.INVISIBLE);
        moradia.setVisibility(View.INVISIBLE);
        ArrayList<Double> medias = new ArrayList<>();
        Locale l = new Locale("pt", "BR");
        for(categoria c : colapsada) {
            Log.d("CTRL", "calcularMediaCategorias: " + c.nome + " " + c.total);
            Double m = ((c.total/4)/12);
            medias.add(m);
            if(c.nome.toLowerCase().contains("transporte")){
                transporte.setText(getString(R.string.transporte, NumberFormat.getCurrencyInstance(l).format(m)));
                transporte.setVisibility(View.VISIBLE);
            }
            if(c.nome.toLowerCase().contains("alimenta")) {
                alimentacao.setText(getString(R.string.alimentacao, NumberFormat.getCurrencyInstance(l).format(m)));
                alimentacao.setVisibility(View.VISIBLE);
            }
            if(c.nome.toLowerCase().contains("manutenc")){
                manutencao.setText(getString(R.string.manutencao, NumberFormat.getCurrencyInstance(l).format(m)));
                manutencao.setVisibility(View.VISIBLE);
            }
            if(c.nome.toLowerCase().contains("assinatura")) {
                assinatura.setText(getString(R.string.assinatura, NumberFormat.getCurrencyInstance(l).format(m)));
                assinatura.setVisibility(View.VISIBLE);
            }
            if(c.nome.toLowerCase().contains("apoio")) {
                apoio.setText(getString(R.string.apoio, NumberFormat.getCurrencyInstance(l).format(m)));
                apoio.setVisibility(View.VISIBLE);
            }
            if(c.nome.toLowerCase().contains("telefonia")){
                telefonia.setText(getString(R.string.telefonia, NumberFormat.getCurrencyInstance(l).format(m)));
                telefonia.setVisibility(View.VISIBLE);
            }
            if(c.nome.toLowerCase().contains("postal")){
                postal.setText(getString(R.string.postal, NumberFormat.getCurrencyInstance(l).format(m)));
                postal.setVisibility(View.VISIBLE);
            }
            if(c.nome.toLowerCase().contains("hospedagem")){
                hospedagem.setText(getString(R.string.hospedagem, NumberFormat.getCurrencyInstance(l).format(m)));
                hospedagem.setVisibility(View.VISIBLE);
            }
            if(c.nome.toLowerCase().contains("seguran")){
                seguranca.setText(getString(R.string.seguranca, NumberFormat.getCurrencyInstance(l).format(m)));
                seguranca.setVisibility(View.VISIBLE);
            }
            if(c.nome.toLowerCase().contains("cursos")) {
                cursos.setText(getString(R.string.cursos, NumberFormat.getCurrencyInstance(l).format(m)));
                cursos.setVisibility(View.VISIBLE);
            }
            if(c.nome.toLowerCase().contains("moradia")){
                moradia.setText(getString(R.string.moradia, NumberFormat.getCurrencyInstance(l).format(m)));
                moradia.setVisibility(View.VISIBLE);
            }

            //Log.d("CTRL", "calcularMediaCategorias: por mes" + c.nome + " " + c.total + "  " + m);
        }



    }

    public class categoria{
        public class item_despesa{
            private String tipoDespesa;
            private String cnpjFornecedor;
            private String nomeFornecedor;
            private String valor;
            private int mes;
            private int ano;

            public item_despesa(String tipoDespesa, String cnpjFornecedor, String nomeFornecedor, String valor, int mes, int ano){
                this.tipoDespesa = tipoDespesa;
                this.cnpjFornecedor = cnpjFornecedor;
                this.nomeFornecedor = nomeFornecedor;
                this.valor = valor;
                this.mes = mes;
                this.ano = ano;
            }

            public String getTipoDespesa() {
                return tipoDespesa;
            }

            public void setTipoDespesa(String tipoDespesa) {
                this.tipoDespesa = tipoDespesa;
            }

            public String getCnpjFornecedor() {
                return cnpjFornecedor;
            }

            public void setCnpjFornecedor(String cnpjFornecedor) {
                this.cnpjFornecedor = cnpjFornecedor;
            }

            public String getValor() {
                return valor;
            }

            public String getNomeFornecedor() {
                return nomeFornecedor;
            }

            public void setNomeFornecedor(String nomeFornecedor) {
                this.nomeFornecedor = nomeFornecedor;
            }

            public void setValor(String valor) {
                this.valor = valor;
            }

            public int getMes() {
                return mes;
            }

            public void setMes(int mes) {
                this.mes = mes;
            }

            public int getAno() {
                return ano;
            }

            public void setAno(int ano) {
                this.ano = ano;
            }

            @Override
            public String toString() {
                return "Tipo Despesa: " + tipoDespesa +
                        "CNPJ fornecedor: " + cnpjFornecedor +
                        "Nome fornecedor: " + nomeFornecedor +
                        "Valor: " + valor +
                        "Mês: " + mes +
                        "Ano: " + ano;
            }
        }

        @Override
        public String toString() {
            return "categoria{" +
                    ", nome='" + nome + '\'' +
                    ", porcentagem=" + porcentagem +
                    ", total=" + total +
                    ", qtdItens=" + qtdItens +
                    "lista_despesas=" + lista_despesas +
                    '}';
        }

        private ArrayList<item_despesa> lista_despesas;

        private String nome;
        private Double porcentagem;
        private Double total;
        private int qtdItens;

        public categoria(String nome, String porcentagem, String total, int qtdItens){
            this.nome = nome;
            this.porcentagem = Double.parseDouble(porcentagem);
            this.total = Double.parseDouble(total);
            this.qtdItens = qtdItens;
            this.lista_despesas = new ArrayList<>();
        }

        public categoria(String nome){
            this.nome = nome;
            this.lista_despesas = new ArrayList<>();
        }

        public void setItemDespesa(String tipoDespesa, String cnpjFornecedor, String nomeFornecedor, String valor, int mes, int ano){
            this.lista_despesas.add(new item_despesa(tipoDespesa, cnpjFornecedor, nomeFornecedor, valor, mes, ano));
        }

        public ArrayList<item_despesa> getLista_despesas() {
            return lista_despesas;
        }

        public void setLista_despesas(ArrayList<item_despesa> lista_despesas) {
            this.lista_despesas = lista_despesas;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public Double getPorcentagem() {
            return porcentagem;
        }

        public void setPorcentagem(Double porcentagem) {
            this.porcentagem = porcentagem;
        }

        public Double getTotal() {
            return total;
        }

        public void setTotal(Double total) {
            this.total = total;
        }

        public int getQtdItens() {
            return qtdItens;
        }

        public void setQtdItens(int qtdItens) {
            this.qtdItens = qtdItens;
        }
    }

    public class WebAppInterface {
        private Context context;

        public WebAppInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public String getDadosDeputado() {
            return deputado.getDados().getId() + ";" + deputado.getDados().getUltimoStatus().getIdLegislatura();
        }

        @JavascriptInterface
        public void finalizar(){
            closeLoading();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @JavascriptInterface
        public void addCategoria(String nome){
            gastos.add(new categoria(nome));
            //Log.d("CTRL", "addCategoria: " + nome);
        }

        @JavascriptInterface
        public void categoriaColapsada(String nome, String porcentagem, String total, int qtdItens){
            Log.d("CTRL", "categoriaColapsada: " + nome + " " + total);
            colapsada.add(new categoria(nome, porcentagem, total, qtdItens));
        }

        @JavascriptInterface
        public void addDespesa(String tipoDespesa, String cnpjFornecedor, String nomeFornecedor, String valor, int mes, int ano) {
            //Log.d("CTRL", "addDespesa: " + tipoDespesa + cnpjFornecedor + nomeFornecedor + valor + ano + mes);
            for (categoria c : gastos) {
                //Log.d("CTRL", "addDespesa: " + tipoDespesa + " = " + c.getNome());
                if (c.getNome().equals(tipoDespesa)) {
                    //Log.d("CTRL", "addDespesa: " + valor);
                    gastos.get(gastos.indexOf(c)).setItemDespesa(tipoDespesa, cnpjFornecedor, nomeFornecedor, valor, mes, ano);
                }
            }
        }

        @JavascriptInterface
        public void setTotal(String t, String tcombustivel){
            total = Double.parseDouble(t);
            totalGas = Double.parseDouble(tcombustivel);
            Log.d("CTRL", "setTotal: " + total + " " + totalGas);
        }

        @JavascriptInterface
        public void setAnaliseAnual(Double[] a){
            analiseAnual.addCatAnual(a);
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("JavascriptInterface")
    @Override
    public void onResume() {
        super.onResume();
        carregaWebView();
        openLoading();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void carregaWebView(){
        final WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setAllowFileAccess(true);
        ws.setDomStorageEnabled(true);
        ws.setAllowContentAccess(true);
        ws.setAllowFileAccessFromFileURLs(true);
        ws.setAllowUniversalAccessFromFileURLs(true);
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(new AnaliseFragment.WebAppInterface(getContext()), "Android");
        webView.loadUrl("file:///android_asset/analise/index.html");
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void bind(View v){
        imgDeputado = (SimpleDraweeView) v.findViewById(R.id.imgDeputadoFresco);
        partidoDeputado = (TextView) v.findViewById(R.id.partidoDeputado);
        nomeDeputado = (TextView) v.findViewById(R.id.nomeDeputado);
        idLegislatura = (TextView) v.findViewById(R.id.idLegislatura);
        dtInicio = (TextView) v.findViewById(R.id.dtInicio);
        dtFim = (TextView) v.findViewById(R.id.dtFim);
        tot = (TextView) v.findViewById(R.id.total);
        salmin = (TextView) v.findViewById(R.id.sm);
        bolsa = (TextView) v.findViewById(R.id.bf);
        combustivel = (TextView) v.findViewById(R.id.gas);
        transporte = (TextView) v.findViewById(R.id.transporte);
        alimentacao = (TextView) v.findViewById(R.id.alimentacao);
        manutencao = (TextView) v.findViewById(R.id.manutencao);
        assinatura = (TextView) v.findViewById(R.id.assinatura);
        apoio = (TextView) v.findViewById(R.id.apoio);
        telefonia = (TextView) v.findViewById(R.id.telefonia);
        postal = (TextView) v.findViewById(R.id.postal);
        hospedagem = (TextView) v.findViewById(R.id.hospedagem);
        seguranca = (TextView) v.findViewById(R.id.seguranca);
        cursos = (TextView) v.findViewById(R.id.cursos);
        moradia = (TextView) v.findViewById(R.id.moradia);

        transporte.setText(getString(R.string.transporte, "R$ 0,00"));
        alimentacao.setText(getString(R.string.alimentacao, "R$ 0,00"));
        manutencao.setText(getString(R.string.manutencao, "R$ 0,00"));
        assinatura.setText(getString(R.string.assinatura, "R$ 0,00"));
        apoio.setText(getString(R.string.apoio, "R$ 0,00"));
        telefonia.setText(getString(R.string.telefonia, "R$ 0,00"));
        postal.setText(getString(R.string.postal, "R$ 0,00"));
        hospedagem.setText(getString(R.string.hospedagem, "R$ 0,00"));
        seguranca.setText(getString(R.string.seguranca, "R$ 0,00"));
        cursos.setText(getString(R.string.cursos, "R$ 0,00"));
        moradia.setText(getString(R.string.moradia, "R$ 0,00"));

        s = (ScrollView) v.findViewById(R.id.s);
        s.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                if(!f){
                    f = true;
                    attAnalise();
                }
            }
        });
    }

    private void atualizaTela(){
        imgDeputado.setImageURI(deputado.getDados().getUltimoStatus().getUrlFoto());
        partidoDeputado.setText(deputado.getDados().getUltimoStatus().getSiglaPartido());
        nomeDeputado.setText(deputado.getDados().getUltimoStatus().getNome());
        String a = deputado.getDados().getUltimoStatus().getIdLegislatura() + " ";
        idLegislatura.setText(a);
        dtInicio.setText(deputado.getDados().getUltimoStatus().getData());
        dtFim.setText("31/12/2018");
    }

    private Double total;
    private Double totalGas;
    private String gastoTotal;

    private String sm;
    private String bf;
    private String gas;
}
