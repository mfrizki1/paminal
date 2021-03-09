package id.calocallo.sicape.network

import id.calocallo.sicape.network.request.*
import id.calocallo.sicape.network.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiLp {
    companion object {
        const val ACCEPT = "Accept: application/json"

        const val JENIS_LP = "lp/{jenis_lp}"
        const val GENERATE_LP = "lp/{id_lp}/generate/document"
        const val LP_WITH_ID = "lp/{id_lp}"
        const val LP_PIDANA = "lp/pidana"
        const val LP_KKE = "lp/kode/etik"
        const val LP_DISIPLIN = "lp/disiplin"

        const val PASAL = "pasal"
        const val PASAL_WITH_ID = "pasal/{id_pasal}"

        const val PASAL_DILANGGAR = "lp/{id_pasal}/pasal/dilanggar"
        const val UPD_PASAL_DILANGGAR = "lp/pasal/dilanggar/{id_pasal_dilanggar}"
      
        const val SAKSI_LP = "lp/{id_lp}/saksi/kode/etik"
        const val UPD_SAKSI_LP = "lp/saksi/kode/etik/{id_saksi}"


    }

    @Headers(ACCEPT)
    @GET(JENIS_LP)
    fun getLpByJenis(
        @Header("Authorization") tokenBearer: String,
        @Path("jenis_lp") jenis_lp: String
    ): Call<ArrayList<LpMinResp>>

    @Headers(ACCEPT)
    @GET(LP_WITH_ID)
    fun getLpById(
        @Header("Authorization") tokenBearer: String,
        @Path("id_lp") id_lp: Int?
    ): Call<LpResp>


    @Headers(ACCEPT)
    @GET(GENERATE_LP)
    fun generateLp(
        @Header("Authorization") tokenBearer: String,
        @Path("id_lp") id_lp: Int?
    ): Call<Base1Resp<DokLpResp>>

    @Headers(ACCEPT)
    @DELETE(LP_WITH_ID)
    fun delLp(
        @Header("Authorization") tokenBearer: String,
        @Path("id_lp") id_lp: Int?
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @POST(LP_PIDANA)
    fun addLpPidana(
        @Header("Authorization") tokenBearer: String,
        @Body lpPidanaReq: LpPidanaReq
    ): Call<Base1Resp<DokLpResp>>

    @Headers(ACCEPT)
    @POST(LP_DISIPLIN)
    fun addLpDisiplin(
        @Header("Authorization") tokenBearer: String,
        @Body lpDisiplinReq: LpDisiplinReq
    ): Call<Base1Resp<DokLpResp>>

    @Headers(ACCEPT)
    @POST(LP_KKE)
    fun addLpKke(
        @Header("Authorization") tokenBearer: String,
        @Body lpKkeReq: LpKkeReq
    ): Call<Base1Resp<DokLpResp>>

    @Headers(ACCEPT)
    @PATCH("$LP_PIDANA/{id_lp}")
    fun updLpPidana(
        @Header("Authorization") tokenBearer: String,
        @Path("id_lp") id_lp: Int?,
        @Body lpPidanaReq: LpPidanaReq
    ): Call<Base1Resp<DokLpResp>>

    @Headers(ACCEPT)
    @PATCH("$LP_DISIPLIN/{id_lp}")
    fun updLpDisiplin(
        @Header("Authorization") tokenBearer: String,
        @Path("id_lp") id_lp: Int?,
        @Body editLpDisiplinReq: LpDisiplinReq
    ): Call<Base1Resp<DokLpResp>>

    @Headers(ACCEPT)
    @PATCH("$LP_KKE/{id_lp}")
    fun updLpKke(
        @Header("Authorization") tokenBearer: String,
        @Path("id_lp") id_lp: Int?,
        @Body editLpKkeReq: LpKkeReq
    ): Call<Base1Resp<DokLpResp>>


    /*PASAL*/
    @Headers(ACCEPT)
    @GET(PASAL)
    fun getAllPasal(@Header("Authorization") tokenBearer: String): Call<ArrayList<PasalResp>>

    @Headers(ACCEPT)
    @POST(PASAL)
    fun addPasal(
        @Header("Authorization") tokenBearer: String,
        @Body pasalReq: PasalReq
    ): Call<Base1Resp<AddPasalResp>>

    @Headers(ACCEPT)
    @PATCH(PASAL_WITH_ID)
    fun updPasal(
        @Header("Authorization") tokenBearer: String,
        @Path("id_pasal") id_pasal: Int,
        @Body pasalReq: PasalReq
    ): Call<Base1Resp<AddPasalResp>>

    @Headers(ACCEPT)
    @GET(PASAL_WITH_ID)
    fun getPasalById(
        @Header("Authorization") tokenBearer: String,
        @Path("id_pasal") id_pasal: Int
    ): Call<PasalResp>

    @Headers(ACCEPT)
    @DELETE(PASAL_WITH_ID)
    fun delPasal(
        @Header("Authorization") tokenBearer: String,
        @Path("id_pasal") id_pasal: Int
    ): Call<BaseResp>

    /*PASAL DILANGGAR*/
    @Headers(ACCEPT)
    @GET(PASAL_DILANGGAR)
    fun getPasalDilanggar(
        @Header("Authorization") tokenBearer: String,
        @Path("id_pasal") id_pasal: Int
    ): Call<ArrayList<PasalDilanggarResp>>

    @Headers(ACCEPT)
    @POST(PASAL_DILANGGAR)
    fun addPasalDilanggar(
        @Header("Authorization") tokenBearer: String,
        @Path("id_pasal") id_pasal: Int,
        @Body idPasal: ListIdPasalReq
    ): Call<Base1Resp<AddPasalDilanggarResp>>

    @Headers(ACCEPT)
    @PATCH(UPD_PASAL_DILANGGAR)
    fun updPasalDilanggar(
        @Header("Authorization") tokenBearer: String,
        @Path("id_pasal_dilanggar") id_pasal_dilanggar: Int,
        @Body idPasal: ListIdPasalReq
    ): Call<Base1Resp<AddPasalDilanggarResp>>

    @Headers(ACCEPT)
    @DELETE(UPD_PASAL_DILANGGAR)
    fun delPasalDilanggar(
        @Header("Authorization") tokenBearer: String,
        @Path("id_pasal_dilanggar") id_pasal_dilanggar: Int
    ): Call<BaseResp>

    /*SAKSI*/

    @Headers(ACCEPT)
    @GET("lp/{id_lp}/saksi")
    fun getSaksiAllByLp(
        @Header("Authorization") tokenBearer: String,
        @Path("id_lp") id_lp: Int
    ): Call<ArrayList<LpSaksiResp>>

    @Headers(ACCEPT)
    @GET("lp/saksi/{id_saksi}")
    fun getSaksiById(
        @Header("Authorization") tokenBearer: String,
        @Path("id_saksi") id_saksi: Int?
    ): Call<LpSaksiResp>

    @Headers(ACCEPT)
    @POST("lp/{id_lp}/saksi/personel")
    fun addSaksiPersonel(
        @Header("Authorization") tokenBearer: String,
        @Path("id_lp") id_lp: Int?,
        @Body saksiReq: SaksiLpReq
    ): Call<Base1Resp<AddSaksiPersonelResp>>

    @Headers(ACCEPT)
    @POST("lp/{id_lp}/saksi/sipil")
    fun addSaksiSipil(
        @Header("Authorization") tokenBearer: String,
        @Path("id_lp") id_lp: Int?,
        @Body saksiReq: SaksiLpReq
    ): Call<Base1Resp<AddSaksiSipilResp>>
    @Headers(ACCEPT)
    @PATCH("lp/saksi/{id_lp}/personel")
    fun updSaksiPersonel(
        @Header("Authorization") tokenBearer: String,
        @Path("id_lp") id_lp: Int?,
        @Body saksiReq: SaksiLpReq
    ): Call<Base1Resp<AddSaksiPersonelResp>>

    @Headers(ACCEPT)
    @PATCH("lp/saksi/{id_lp}/sipil")
    fun updSaksiSipil(
        @Header("Authorization") tokenBearer: String,
        @Path("id_lp") id_lp: Int?,
        @Body saksiReq: SaksiLpReq
    ): Call<Base1Resp<AddSaksiSipilResp>>


    @Headers(ACCEPT)
    @DELETE("lp/saksi/{id_saksi}")
    fun delSaksiSingle(
        @Header("Authorization") tokenBearer: String,
        @Path("id_saksi") id_saksi: Int
    ): Call<BaseResp>
}