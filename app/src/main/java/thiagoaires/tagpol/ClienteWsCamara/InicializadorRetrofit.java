/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thiagoaires.tagpol.ClienteWsCamara;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * @author thiaires
 */
public class InicializadorRetrofit {
    private final Retrofit retrofit;

    public InicializadorRetrofit (){
        this.retrofit = new Retrofit.Builder() //instancia o objeto retrofit
                .baseUrl("https://dadosabertos.camara.leg.br/api/v2/") // define url base do webservice
                .addConverterFactory(GsonConverterFactory.create()) // defini o conversor da resposta no caso para Json
                .build(); // manda construir
    }

    public CamaraService getCamaraService(){
        return this.retrofit.create(CamaraService.class); //vou retornar o objeto retrofit já com o serviço criado.
    }
}
