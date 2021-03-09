package id.calocallo.sicape.network

import id.calocallo.sicape.model.AddLhpResp
import id.calocallo.sicape.model.LhpResp
import id.calocallo.sicape.network.request.*
import id.calocallo.sicape.network.response.*
import retrofit2.Call
import retrofit2.http.*
/*ENDPOINT LHP*/
interface ApiLhp {
    companion object {
        const val LHP = "lhp"
        const val LHP_ID = "lhp/{id_lhp}"
        const val GENERATE_LHP = "lhp/{id_lhp}/generate/document"

        const val SAKSI_LHP ="lhp/{id_lhp}}/saksi"
        const val UPD_SAKSI_LHP ="lhp/saksi/{id_saksi}"

        const val REF_PENYELIDIK_LHP ="lhp/{id_lhp}/referensi/penyelidikan"
        const val UPD_REF_PENYELIDIK_LHP ="lhp/referensi/penyelidikan/{id_penyelidikan}"

        const val PENYELIDIK_LHP ="lhp/{id_lhp}/personel/penyelidik"
        const val UPD_PENYELIDIK_LHP ="lhp/personel/penyelidik/{id_pers_lidik}"
    }

    @Headers(ApiLp.ACCEPT)
    @GET(LHP)
    fun getLhpAll(
        @Header("Authorization") tokenBearer: String
    ): Call<ArrayList<LhpMinResp>>

    @Headers(ApiLp.ACCEPT)
    @GET(LHP_ID)
    fun getLhpById(
        @Header("Authorization") tokenBearer: String,
        @Path("id_lhp") id_lhp: Int?
    ): Call<LhpResp>

    @Headers(ApiLp.ACCEPT)
    @GET(GENERATE_LHP)
    fun generateDokLhp(
        @Header("Authorization") tokenBearer: String,
        @Path("id_lhp") id_lhp: Int?
    ): Call<Base1Resp<AddLhpResp>>

    @Headers(ApiLp.ACCEPT)
    @POST(LHP)
    fun addLhp(
        @Header("Authorization") tokenBearer: String,
        @Body lhpReq: LhpReq
    ): Call<Base1Resp<AddLhpResp>>

    @Headers(ApiLp.ACCEPT)
    @PATCH(LHP_ID)
    fun updLhp(
        @Header("Authorization") token: String,
        @Path("id_lhp") id_lhp: Int?,
        @Body editLhpReq: EditLhpReq
    ): Call<Base1Resp<AddLhpResp>>

    @Headers(ApiLp.ACCEPT)
    @DELETE(LHP_ID)
    fun delLhp(
        @Header("Authorization") token: String,
        @Path("id_lhp") id_lhp: Int?
    ): Call<BaseResp>

    @Headers(ApiLp.ACCEPT)
    @GET(SAKSI_LHP)
    fun getAllSaksiLhp(
        @Header("Authorization") token: String,
        @Path("id_lhp") id_lhp: Int?
    ): Call<ArrayList<SaksiLhpResp>>


    @Headers(ApiLp.ACCEPT)
    @POST(SAKSI_LHP)
    fun addSaksiLhp(
        @Header("Authorization") token: String,
        @Path("id_lhp") id_lhp: Int?,
        @Body saksiLhpReq: SaksiLhpReq
    ): Call<Base1Resp<AddSaksiLhpResp>>

    @Headers(ApiLp.ACCEPT)
    @PATCH(UPD_SAKSI_LHP)
    fun updSaksiLhp(
        @Header("Authorization") token: String,
        @Path("id_saksi") id_saksi: Int?,
        @Body saksiLhpReq: SaksiLhpReq
    ): Call<Base1Resp<AddSaksiLhpResp>>

    @Headers(ApiLp.ACCEPT)
    @DELETE(UPD_SAKSI_LHP)
    fun delSaksiLhp(
        @Header("Authorization") token: String,
        @Path("id_saksi") id_saksi: Int?
    ): Call<BaseResp>

    @Headers(ApiLp.ACCEPT)
    @GET(REF_PENYELIDIK_LHP)
    fun getRefPenyelidikan(
        @Header("Authorization") token: String,
        @Path("id_lhp") id_lhp: Int?
    ): Call<ArrayList<RefPenyelidikanResp>>

    @Headers(ApiLp.ACCEPT)
    @GET(UPD_REF_PENYELIDIK_LHP)
    fun detailRefPenyelidikan(
        @Header("Authorization") token: String,
        @Path("id_penyelidikan") id_penyelidikan: Int?
    ): Call<RefPenyelidikanResp>

    @Headers(ApiLp.ACCEPT)
    @POST(REF_PENYELIDIK_LHP)
    fun addRefPenyelidikan(
        @Header("Authorization") token: String,
        @Path("id_lhp") id_lhp: Int?,
        @Body addRefPenyelidikanReq: RefPenyelidikanReq
    ): Call<Base1Resp<AddRefPenyelidikanResp>>

    @Headers(ApiLp.ACCEPT)
    @PATCH(UPD_REF_PENYELIDIK_LHP)
    fun updRefPenyelidikan(
        @Header("Authorization") token: String,
        @Path("id_penyelidikan") id_penyelidikan: Int?,
        @Body addRefPenyelidikanReq: RefPenyelidikanReq
    ): Call<Base1Resp<AddRefPenyelidikanResp>>

    @Headers(ApiLp.ACCEPT)
    @DELETE(UPD_REF_PENYELIDIK_LHP)
    fun delRefPenyelidikan(
        @Header("Authorization") token: String,
        @Path("id_penyelidikan") id_penyelidikan: Int?
    ): Call<BaseResp>

    @Headers(ApiLp.ACCEPT)
    @GET(PENYELIDIK_LHP)
    fun getAllPersLidik(
        @Header("Authorization") token: String,
        @Path("id_lhp") id_lhp: Int?
    ): Call<ArrayList<PersonelPenyelidikResp>>

    @Headers(ApiLp.ACCEPT)
    @GET(UPD_PENYELIDIK_LHP)
    fun detailPersLidik(
        @Header("Authorization") token: String,
        @Path("id_pers_lidik") id_pers_lidik: Int?
    ): Call<DetailPersLidikResp>

    @Headers(ApiLp.ACCEPT)
    @POST(PENYELIDIK_LHP)
    fun addPersLidik(
        @Header("Authorization") token: String,
        @Path("id_lhp") id_lhp: Int?,
        @Body personelPenyelidikReq: PersonelPenyelidikReq
    ): Call<Base1Resp<AddPersLidikResp>>

    @Headers(ApiLp.ACCEPT)
    @PATCH(UPD_PENYELIDIK_LHP)
    fun updPersLidik(
        @Header("Authorization") token: String,
        @Path("id_pers_lidik") id_pers_lidik: Int?,
        @Body personelPenyelidikReq: PersonelPenyelidikReq
    ): Call<Base1Resp<AddPersLidikResp>>

    @Headers(ApiLp.ACCEPT)
    @DELETE(UPD_PENYELIDIK_LHP)
    fun delPersLidik(
        @Header("Authorization") token: String,
        @Path("id_pers_lidik") id_pers_lidik: Int?
    ): Call<BaseResp>
}