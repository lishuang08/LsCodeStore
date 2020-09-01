package ls.yylx.lscodestore.basemodule.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


/**
 * Created by ls on 2019/9/24.
 */
interface ApiResponse<T> {

    @GET
    @FormUrlEncoded
    fun get(@Url url: String, @FieldMap maps: Map<String, String>): Call<ResponseBody>

    @POST
    @FormUrlEncoded
    fun <T> post(@Url url: String, @FieldMap body: Map<String, String>): Call<T>

    @GET
    fun download(@Url url: String  ): Call<ResponseBody>
}

