package id.calocallo.sicape.network

import id.calocallo.sicape.network.request.FilterReq
import id.calocallo.sicape.network.request.SkhpReq
import id.calocallo.sicape.network.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiSkhp {
    @Headers(ApiLp.ACCEPT)
    @GET("skhp")
    fun getListSkhp(
        @Header("Authorization") tokenBearer: String
    ): Call<ArrayList<SkhpMinResp>>

    @Headers(ApiLp.ACCEPT)
    @GET("skhp/{id_skhp}")
    fun detailSkhp(
        @Header("Authorization") tokenBearer: String,
        @Path("id_skhp") id_skhp: Int?
    ): Call<SkhpResp>

    @Headers(ApiLp.ACCEPT)
    @POST("skhp")
    fun addSkhp(
        @Header("Authorization") tokenBearer: String,
        @Body skhpReq: SkhpReq
    ): Call<Base1Resp<AddSkhpResp>>/*Data skhp saved succesfully*/

    @Headers(ApiLp.ACCEPT)
    @PATCH("skhp/{id_skhp}")
    fun updSkhp(
        @Header("Authorization") tokenBearer: String,
        @Path("id_skhp") id_skhp: Int?, @Body skhpReq: SkhpReq
    ): Call<Base1Resp<AddSkhpResp>>/*Data skhp updated succesfully*/

    @Headers(ApiLp.ACCEPT)
    @DELETE("skhp/{id_skhp}")
    fun dellSkhp(
        @Header("Authorization") tokenBearer: String,
        @Path("id_skhp") id_skhp: Int?
    ): Call<BaseResp>
}