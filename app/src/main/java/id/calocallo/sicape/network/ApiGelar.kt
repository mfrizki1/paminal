package id.calocallo.sicape.network

import id.calocallo.sicape.network.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiGelar {
    @Headers(ApiLp.ACCEPT)
    @GET("lhg")
    fun getListLhg(
        @Header("Authorization") tokenBearer: String
    ): Call<ArrayList<LhgMinResp>>

    @Headers(ApiLp.ACCEPT)
    @GET("lhg/{id_lhg}")
    fun detailLhg(
        @Header("Authorization") tokenBearer: String,
        @Path("id_lhg") id_lhg: Int?
    ): Call<LhgResp>

    @Headers(ApiLp.ACCEPT)
    @POST("lhg")
    fun addLhg(
        @Header("Authorization") tokenBearer: String,
        @Body lhgResp: LhgResp
    ): Call<Base1Resp<AddLhgResp>>/*Data lhg saved succesfully*/

    @Headers(ApiLp.ACCEPT)
    @PATCH("lhg/{id_lhg}")
    fun updLhg(
        @Header("Authorization") tokenBearer: String,
        @Path("id_lhg") id_lhg: Int?,
        @Body lhgResp: LhgResp
    ): Call<Base1Resp<AddLhgResp>>/*Data lhg updated succesfully*/

    @Headers(ApiLp.ACCEPT)
    @DELETE("lhg/{id_lhg}")
    fun delLhg(
        @Header("Authorization") tokenBearer: String
    ): Call<BaseResp>
    /*Data lhg removed succesfully*/
    /*Data lhg has been used as reference in another data*/

    /*------------------------------------------------------------------------*/


    @Headers(ApiLp.ACCEPT)
    @GET("lhg/{id_lhg}/peserta/gelar")
    fun getListPesertaLhg(
        @Header("Authorization") tokenBearer: String,
        @Path("id_lhg") id_lhg: Int?
    ): Call<ArrayList<PesertaLhgResp>>

    @Headers(ApiLp.ACCEPT)
    @POST("lhg/{id_lhg}/peserta/gelar")
    fun addPesertaLhg(
        @Header("Authorization") tokenBearer: String,
        @Path("id_lhg") id_lhg: Int?,
        @Body pesertaLhgResp: PesertaLhgResp
    ): Call<Base1Resp<AddPesertaLhgResp>>/*Data peserta gelar saved succesfully*/

    @Headers(ApiLp.ACCEPT)
    @PATCH("lhg/peserta/gelar/{id_peserta}")
    fun updPesertaLhg(
        @Header("Authorization") tokenBearer: String,
        @Path("id_peserta") id_peserta: Int?,
        @Body pesertaLhgResp: PesertaLhgResp
    ): Call<Base1Resp<AddPesertaLhgResp>>/*Data peserta gelar updated succesfully*/

    @Headers(ApiLp.ACCEPT)
    @DELETE("lhg/peserta/gelar/{id_peserta}")
    fun delPesertaLhg(
        @Header("Authorization") tokenBearer: String,
        @Path("id_peserta") id_peserta: Int?
    ): Call<BaseResp>/*Data peserta gelar removed succesfully*/

}