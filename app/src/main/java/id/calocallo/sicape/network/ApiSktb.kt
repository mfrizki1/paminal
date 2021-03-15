package id.calocallo.sicape.network

import id.calocallo.sicape.network.request.RpphReq
import id.calocallo.sicape.network.request.SktbReq
import id.calocallo.sicape.network.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiSktb {
    @Headers(ApiLp.ACCEPT)
    @GET("sktb")
    fun getListSktb(
        @Header("Authorization") tokenBearer: String
    ): Call<ArrayList<SktbMinResp>>

    @Headers(ApiLp.ACCEPT)
    @GET("sktb/{id_sktb}/generate/document")
    fun docSktb(
        @Header("Authorization") tokenBearer: String,
        @Path("id_sktb") id_sktb: Int?
    ): Call<Base1Resp<AddSktbResp>>
    @Headers(ApiLp.ACCEPT)
    @GET("sktb/{id_sktb}")
    fun detailSktb(
        @Header("Authorization") tokenBearer: String,
        @Path("id_sktb") id_sktb: Int?
    ): Call<SktbResp>

    @Headers(ApiLp.ACCEPT)
    @POST("sktb")
    fun addSktb(
        @Header("Authorization") tokenBearer: String,
        @Body SktbReq: SktbReq
    ): Call<Base1Resp<AddSktbResp>>/*Data sktb saved succesfully*/

    @Headers(ApiLp.ACCEPT)
    @PATCH("sktb/{id_sktb}")
    fun updSktb(
        @Header("Authorization") tokenBearer: String,
        @Path("id_sktb") id_sktb: Int?,
        @Body SktbReq: SktbReq
    ): Call<Base1Resp<AddSktbResp>>/*Data sktb updated succesfully*/

    @Headers(ApiLp.ACCEPT)
    @DELETE("sktb/{id_sktb}")
    fun delSktb(
        @Header("Authorization") tokenBearer: String,
        @Path("id_sktb") id_Sktb: Int?
    ): Call<BaseResp>/*Data sktb removed succesfully*/
}