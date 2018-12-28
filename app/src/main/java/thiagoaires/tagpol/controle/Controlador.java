package thiagoaires.tagpol.controle;

import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thiagoaires.tagpol.ClienteWsCamara.InicializadorRetrofit;
import thiagoaires.tagpol.Modelo.Deputados;
import thiagoaires.tagpol.controle.Adapter.DeputadoAdapter;

public class Controlador implements Serializable {
    private Deputados deputados;
    private String siglaUF;


    public Controlador(){
        this.deputados = new Deputados();
        this.siglaUF = "BR";
    }

    public Deputados getDeputados() {
        return deputados;
    }

    public void carregaDeputados(){
        String last = "";
        String self = "";
        for (Deputados.Link l : deputados.getLinks())
            if (l.getRel().equals("last"))
                last = l.getHref();
        for (Deputados.Link l : deputados.getLinks())
            if (l.getRel().equals("self"))
                self = l.getHref();
        int l=getPagina(last);
        int s=getPagina(self);
        if(s == 0 && l == 0)
            retrofit(1);
        else if(s+1<=l)
            retrofit(s+1);
    }

    private void retrofit(final int pag){
        try{
            Log.d("CTRL", "retrofit " + pag);
            Call<Deputados> call = new InicializadorRetrofit().getCamaraService().getDeputados(pag); //cria uma requisição
            call.enqueue(new Callback<Deputados>() { // enqueue significa uma requisição assincrona pois pode demorar e nao pode travar o app
                @Override
                public void onFailure(Call<Deputados> call, Throwable t) {
                    // metodo caso falhe a requisição
                    Log.d("CTRL", "ERRO na chamada, exceção: " + t.toString());
                }

                @Override
                public void onResponse(Call<Deputados> call, Response<Deputados> rspns) {
                    try {
                        //System.out.println("onresponse");
                        Deputados aux = rspns.body();
                      //  Log.i("DEP", "links" + aux.getLinks().toString());
                        addDeputado(aux);
                        carregaDeputados();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getPagina(String link){
        if(link.equals(""))
            return 0;

        int i = link.indexOf("pagina=");
        link = link.substring(i, link.length());
        String[] links = link.split("&");
        String pagina = links[0].substring(7,links[0].length());

        //Log.i("DEP", "link pagina " + pagina);
        return Integer.parseInt(pagina);
    }

    public void addDeputado(Deputados aux){
        //Log.i("DEP", "addDeputados count atual" + deputados.getDados().size());
        //Log.i("DEP", aux.toString());
        for(Deputados.Dado d : aux.getDados()){
            deputados.addDado(d);
        }
        deputados.setLinks(aux.getLinks());
        organizaNomeAlfabetico();
    }

    public void setEstado(String siglaUf){
        this.siglaUF = siglaUf;
    }

    public Deputados getDeputadosPorEstado(){
        if(siglaUF.equals("BR")){
            return deputados;
        }

        Deputados r = new Deputados();

        for(Deputados.Dado d : deputados.getDados()){
            if(d.getSiglaUf().toUpperCase().equals(siglaUF.toUpperCase())) {
                //Log.i("DEP", d.toString() +"\n");
                r.addDado(d);
            }
        }

        //Log.i("DEP", "\n\ncount: " + r.getDados().size());
        return r;
    }

    public void organizaNomeAlfabetico() {
        Collections.sort(this.deputados.getDados(), new Comparator<Deputados.Dado>() {
            @Override
            public int compare(Deputados.Dado deputado, Deputados.Dado t1) {
                return deputado.getNome().compareToIgnoreCase(t1.getNome());
            }
        });
    }
}
