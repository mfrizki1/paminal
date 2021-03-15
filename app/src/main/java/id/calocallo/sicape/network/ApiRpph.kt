package id.calocallo.sicape.network
import id.calocallo.sicape.network.request.RpphReq
import id.calocallo.sicape.network.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiRpph {
    @Headers(ApiLp.ACCEPT)
    @GET("rpph")
    fun getListRpph(
        @Header("Authorization") tokenBearer: String
    ): Call<ArrayList<RpphMinResp>>

    @Headers(ApiLp.ACCEPT)
    @GET("rpph/{id_rpph}/generate/document")
    fun docRpph(
        @Header("Authorization") tokenBearer: String,
        @Path("id_rpph") id_rpph: Int?
    ): Call<Base1Resp<AddRpphResp>>

    @Headers(ApiLp.ACCEPT)
    @GET("rpph/{id_rpph}")
    fun detailRpph(
        @Header("Authorization") tokenBearer: String,
        @Path("id_rpph") id_rpph: Int?
    ): Call<RpphResp>

    @Headers(ApiLp.ACCEPT)
    @POST("rpph")
    fun addRpph(
        @Header("Authorization") tokenBearer: String,
        @Body RpphReq: RpphReq
    ): Call<Base1Resp<AddRpphResp>>/*Data rpph saved succesfully*/

    @Headers(ApiLp.ACCEPT)
    @PATCH("rpph/{id_rpph}")
    fun updRpph(
        @Header("Authorization") tokenBearer: String,
        @Path("id_rpph") id_rpph: Int?,
        @Body RpphReq: RpphReq
    ): Call<Base1Resp<AddRpphResp>>/*Data rpph updated succesfully*/

    @Headers(ApiLp.ACCEPT)
    @DELETE("rpph/{id_rpph}")
    fun delRpph(
        @Header("Authorization") tokenBearer: String,
        @Path("id_rpph") id_rpph: Int?
    ): Call<BaseResp>/*Data rpph removed succesfully*/
}