package ls.yylx.lscodestore.basemodule.network

import ls.yylx.lscodestore.basemodule.db.SpeciesBack
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface GbifService {


    @GET("species")
    fun getSpecies(
            @Query("offset") offset: Int = 0,
            @Query("limit") limit: Int = 1000,
            @QueryMap() map: Map<String, String> = emptyMap()
    ): Call<SpeciesBack>



    @GET("species")
    fun getSpeciesBox(
            @Query("offset") offset: Int  = 0,
            @Query("limit") limit: Int = 1000,
            @QueryMap() map: Map<String, String> = emptyMap()
    ): Call<SpeciesBack>

    //    uuid|shortname
    @GET("species/root/{search}")
    fun getSpeciesRoot(
            @Path("search") search: String

//            @Query("offset") offset: Int = 0,
//            @Query("limit") limit: Int = 1000,
    ): Call<SpeciesBack>



    @GET("species/{int}")
    fun getSingleSpecie(
            @Path("int") search: String


//            @Query("offset") offset: Int = 0,
//            @Query("limit") limit: Int = 1000,
    ): Call<String>



}