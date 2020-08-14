package ls.yylx.lscodestore.basemodule.network

import ls.yylx.lscodestore.basemodule.db.CatApiBreedsBack
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCatApi {

    @GET("breeds")
    fun getAllBreeds(): Call<CatApiBreedsBack>

    @GET("breeds/search")
    fun searchBreeds(
        @Query("q") breedId: String,
    ): Call<CatApiBreedsBack>


}