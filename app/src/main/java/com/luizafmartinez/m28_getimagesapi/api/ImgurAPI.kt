package com.luizafmartinez.m28_getimagesapi.api

import com.luizafmartinez.m28_getimagesapi.api.model.Resultado
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImgurAPI {

    //Passar a rota
    @GET("gallery/search/") //?q=cats
    suspend fun pesquisarImagensGaleria(
        @Query("q") q : String
    ) : Response< Resultado >





}