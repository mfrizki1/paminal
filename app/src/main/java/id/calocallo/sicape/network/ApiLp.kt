package id.calocallo.sicape.network

import id.calocallo.sicape.network.request.*
import id.calocallo.sicape.network.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiLp {
    companion object {
        const val ACCEPT = "Accept: application/json"
    }

    @Headers(ACCEPT)
    @GET("lp/{jenis_lp}")
    fun getLpByJenis(
        @Header("Authorization") tokenBearer: String,
        @Path("jenis_lp") jenis_lp: String
    ): Call<ArrayList<LpMinResp>>

    @Headers(ACCEPT)
    @GET("lp/{id_lp}")
    fun getLpById(
        @Header("Authorization") tokenBearer: String,
        @Path("id_lp") id_lp: Int?
    ): Call<LpPidanaResp>


    @Headers(ACCEPT)
    @GET("lp/{id_lp}/generate/document")
    fun generateLp(
        @Header("Authorization") tokenBearer: String,
        @Path("id_lp") id_lp: Int?
    ): Call<Base1Resp<DokLpResp>>

    @Headers(ACCEPT)
    @DELETE("lp/{id_lp}")
    fun delLp(
        @Header("Authorization") tokenBearer: String,
        @Path("id_lp") id_lp: Int?
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @POST("lp/pidana")
    fun addLpPidana(
        @Header("Authorization") tokenBearer: String,
        @Body lpPidanaReq: LpPidanaReq
    ):Call<Base1Resp<DokLpResp>>

    @Headers(ACCEPT)
    @POST("lp/disiplin")
    fun addLpDisiplin(
        @Header("Authorization") tokenBearer: String,
        @Body lpDisiplinReq: LpDisiplinReq
    ):Call<Base1Resp<DokLpResp>>

    @Headers(ACCEPT)
    @POST("lp/kode/etik")
    fun addLpKke(
        @Header("Authorization") tokenBearer: String,
        @Body lpKkeReq: LpKkeReq
    ):Call<Base1Resp<DokLpResp>>

    @Headers(ACCEPT)
    @PATCH("lp/{id_lp}")
    fun updLpPidana(
        @Header("Authorization") tokenBearer: String,
        @Path("id_lp") id_lp: Int?,
        @Body lpPidanaReq: EditLpPidanaReq
    ): Call<Base1Resp<DokLpResp>>

    @Headers(ACCEPT)
    @PATCH("lp/{id_lp}")
    fun updLpDisiplin(
        @Header("Authorization") tokenBearer: String,
        @Path("id_lp") id_lp: Int?,
        @Body editLpDisiplinReq: EditLpDisiplinReq
    ): Call<Base1Resp<DokLpResp>>

    @Headers(ACCEPT)
    @PATCH("lp/{id_lp}")
    fun updLpKke(
        @Header("Authorization") tokenBearer: String,
        @Path("id_lp") id_lp: Int?,
        @Body editLpKkeReq: EditLpKkeReq
    ): Call<Base1Resp<DokLpResp>>


    /*PASAL*/
    @Headers(ACCEPT)
    @GET("pasal")
    fun getAllPasal(@Header("Authorization") tokenBearer: String): Call<ArrayList<PasalResp>>

    @Headers(ACCEPT)
    @POST("pasal")
    fun addPasal(
        @Header("Authorization") tokenBearer: String,
        @Body pasalReq: PasalReq
    ): Call<Base1Resp<AddPasalResp>>

    @Headers(ACCEPT)
    @PATCH("pasal/{id_pasal}")
    fun updPasal(
        @Header("Authorization") tokenBearer: String,
        @Path("id_pasal") id_pasal: Int,
        @Body pasalReq: PasalReq
    ): Call<Base1Resp<AddPasalResp>>

    @Headers(ACCEPT)
    @GET("pasal/{id_pasal}")
    fun getPasalById(
        @Header("Authorization") tokenBearer: String,
        @Path("id_pasal") id_pasal: Int
    ): Call<PasalResp>

    @Headers(ACCEPT)
    @DELETE("pasal/{id_pasal}")
    fun delPasal(
        @Header("Authorization") tokenBearer: String,
        @Path("id_pasal") id_pasal: Int
    ): Call<BaseResp>

    /*PASAL DILANGGAR*/
    @Headers(ACCEPT)
    @GET("lp/{id_pasal}/pasal/dilanggar")
    fun getPasalDilanggar(
        @Header("Authorization") tokenBearer: String,
        @Path("id_pasal") id_pasal: Int
    ): Call<ArrayList<PasalDilanggarResp>>

    @Headers(ACCEPT)
    @POST("lp/{id_pasal}/pasal/dilanggar")
    fun addPasalDilanggar(
        @Header("Authorization") tokenBearer: String,
        @Path("id_pasal") id_pasal: Int,
        @Body idPasal: ListIdPasalReq
    ): Call<Base1Resp<AddPasalDilanggarResp>>

    @Headers(ACCEPT)
    @PATCH("lp/pasal/dilanggar/{id_pasal_dilanggar}}")
    fun updPasalDilanggar(
        @Header("Authorization") tokenBearer: String,
        @Path("id_pasal_dilanggar") id_pasal_dilanggar: Int,
        @Body idPasal: ListIdPasalReq
    ): Call<Base1Resp<AddPasalDilanggarResp>>

    @Headers(ACCEPT)
    @DELETE("lp/pasal/dilanggar/{id_pasal_dilanggar}")
    fun delPasalDilanggar(
        @Header("Authorization") tokenBearer: String,
        @Path("id_pasal_dilanggar") id_pasal_dilanggar: Int
    ): Call<BaseResp>

    /*SAKSI*/

    @Headers(ACCEPT)
    @GET("lp/{id_lp}/saksi/kode/etik")
    fun getSaksiByIdLp(
        @Header("Authorization") tokenBearer: String,
        @Path("id_lp") id_lp: Int
    ): Call<ArrayList<LpSaksiResp>>

    @Headers(ACCEPT)
    @POST("lp/{id_lp}/saksi/kode/etik")
    fun addSaksiByIdLp(
        @Header("Authorization") tokenBearer: String,
        @Path("id_lp") id_lp: Int,
        @Body saksiReq: SaksiReq
    ): Call<Base1Resp<AddSaksiResp>>

    @Headers(ACCEPT)
    @PATCH("lp/saksi/kode/etik/{id_saksi}")
    fun updSaksiSingle(
        @Header("Authorization") tokenBearer: String,
        @Path("id_saksi") id_saksi: Int,
        @Body saksiReq: SaksiReq
    ): Call<Base1Resp<AddSaksiResp>>

    @Headers(ACCEPT)
    @DELETE("lp/saksi/kode/etik/{id_saksi}")
    fun delSaksiSingle(
        @Header("Authorization") tokenBearer: String,
        @Path("id_saksi") id_saksi: Int
    ): Call<BaseResp>
}