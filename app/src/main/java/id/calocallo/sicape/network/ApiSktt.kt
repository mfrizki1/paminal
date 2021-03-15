package id.calocallo.sicape.network

import id.calocallo.sicape.network.request.SkttReq
import id.calocallo.sicape.network.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiSktt {
    @Headers(ApiLp.ACCEPT)
    @GET("sktt")
    fun getListSktt(
        @Header("Authorization") tokenBearer: String
    ): Call<ArrayList<SkttMinResp>>

    @Headers(ApiLp.ACCEPT)
    @GET("sktt/{id_sktt}/generate/document")
    fun docSktt(
        @Header("Authorization") tokenBearer: String,
        @Path("id_sktt") id_sktt: Int?
    ): Call<Base1Resp<AddSkttResp>>

    @Headers(ApiLp.ACCEPT)
    @GET("sktt/{id_sktt}")
    fun detailSktt(
        @Header("Authorization") tokenBearer: String,
        @Path("id_sktt") id_sktt: Int?
    ): Call<SkttResp>

    @Headers(ApiLp.ACCEPT)
    @POST("sktt")
    fun addSktt(
        @Header("Authorization") tokenBearer: String,
        @Body SkttReq: SkttReq
    ): Call<Base1Resp<AddSkttResp>>/*Data sktt saved succesfully*/

    @Headers(ApiLp.ACCEPT)
    @PATCH("sktt/{id_sktt}")
    fun updSktt(
        @Header("Authorization") tokenBearer: String,
        @Path("id_sktt") id_sktt: Int?,
        @Body SkttReq: SkttReq
    ): Call<Base1Resp<AddSkttResp>>/*Data sktt updated succesfully*/

    @Headers(ApiLp.ACCEPT)
    @DELETE("sktt/{id_sktt}")
    fun delSktt(
        @Header("Authorization") tokenBearer: String,
        @Path("id_sktt") id_Sktt: Int?
    ): Call<BaseResp>/*Data sktt removed succesfully*/
}