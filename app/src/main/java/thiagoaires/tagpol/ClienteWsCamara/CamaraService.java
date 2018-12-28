/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thiagoaires.tagpol.ClienteWsCamara;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import thiagoaires.tagpol.Modelo.Deputado;
import thiagoaires.tagpol.Modelo.Deputados;
import thiagoaires.tagpol.Modelo.Despesas;

/**
 *
 * @author thiaires
 */
public interface CamaraService {
                                                // finaliza o endpoint do web service para fazer a requisição GET,
    @GET("deputados?&ordem=ASC&ordenarPor=nome&itens=100") //  concatena com a base url definida no inicializador retrofit
    Call<Deputados> getDeputados(@Query("pagina") int num);             //faz a chamada do metodo getDeputados
    /*
            o retrofit já retorna um List<Object> no caso do tipo Deputado
     */

    @GET("deputados/{id}") //  concatena com a base url definida no inicializador retrofit
    Call<Deputado> getDeputado(@Path("id") int id);

    @GET("deputados/{id}/despesas?ordem=ASC&itens=100")
    Call<Despesas> getDespesas(@Path("id") int id, @Query("ano") String ano, @Query("mes") String mes, @Query("pagina") int pagina);
}
