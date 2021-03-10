package id.calocallo.sicape.network

import id.calocallo.sicape.network.request.RpsReq
import id.calocallo.sicape.network.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiRps {
    @Headers(ApiLp.ACCEPT)
    @GET("rps")
    fun getListRps(
        @Header("Authorization") tokenBearer: String
    ): Call<ArrayList<RpsMinResp>>

    @Headers(ApiLp.ACCEPT)
    @GET("rps/{id_rps}")
    fun detailRps(
        @Header("Authorization") tokenBearer: String,
        @Path("id_rps") id_rps: Int?
    ): Call<RpsResp>

    @Headers(ApiLp.ACCEPT)
    @POST("rps")
    fun addRps(
        @Header("Authorization") tokenBearer: String,
        @Body rpsReq: RpsReq
    ): Call<Base1Resp<AddRpsResp>>/*Data rps saved succesfully*/

    @Headers(ApiLp.ACCEPT)
    @PATCH("rps/{id_rps}")
    fun updRps(
        @Header("Authorization") tokenBearer: String,
        @Path("id_rps") id_rps: Int?,
        @Body rpsReq: RpsReq
    ): Call<Base1Resp<AddRpsResp>>/*Data rps updated succesfully*/

    @Headers(ApiLp.ACCEPT)
    @DELETE("rps/{id_rps}")
    fun delRps(
        @Header("Authorization") tokenBearer: String,
        @Path("id_rps") id_rps: Int?
    ): Call<BaseResp>/*Data rps removed succesfully*/
}