package id.calocallo.sicape.network

import id.calocallo.sicape.network.request.Sp4Req
import id.calocallo.sicape.network.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiSp4 {
    @Headers(ApiLp.ACCEPT)
    @GET("sp4")
    fun getListSp4(
        @Header("Authorization") tokenBearer: String
    ): Call<ArrayList<Sp4MinResp>>

    @Headers(ApiLp.ACCEPT)
    @GET("sp4/{id_sp4}")
    fun detailSp4(
        @Header("Authorization") tokenBearer: String,
        @Path("id_sp4") id_sp4: Int?
    ): Call<Sp4Resp>

    @Headers(ApiLp.ACCEPT)
    @POST("sp4")
    fun addSp4(
        @Header("Authorization") tokenBearer: String,
        @Body Sp4Req: Sp4Req
    ): Call<Base1Resp<AddSp4Resp>>/*Data sp4 saved succesfully*/

    @Headers(ApiLp.ACCEPT)
    @PATCH("sp4/{id_sp4}")
    fun updSp4(
        @Header("Authorization") tokenBearer: String,
        @Path("id_sp4") id_sp4: Int?,
        @Body Sp4Req: Sp4Req
    ): Call<Base1Resp<AddSp4Resp>>/*Data sp4 updated succesfully*/

    @Headers(ApiLp.ACCEPT)
    @DELETE("sp4/{id_sp4}")
    fun delSp4(
        @Header("Authorization") tokenBearer: String,
        @Path("id_sp4") id_Sp4: Int?
    ): Call<BaseResp>/*Data sp4 removed succesfully*/
}