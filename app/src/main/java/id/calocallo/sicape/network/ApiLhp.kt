package id.calocallo.sicape.network

import id.calocallo.sicape.model.AddLhpResp
import id.calocallo.sicape.model.LhpResp
import id.calocallo.sicape.network.request.*
import id.calocallo.sicape.network.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiLhp {
    companion object {
        const val LHP = "lhp"
        const val LHP_ID = "lhp/{id_lhp}"
    }

    @Headers(ApiLp.ACCEPT)
    @GET("lhp/proses")
    fun getLhpOnSkhd(
        @Header("Authorization") tokenBearer: String
    ): Call<ArrayList<LhpMinResp>>


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
    @GET("lhp/{id_lhp}/generate/document")
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
    @GET("lhp/{id_lhp}}/saksi")
    fun getAllSaksiLhp(
        @Header("Authorization") token: String,
        @Path("id_lhp") id_lhp: Int?
    ): Call<ArrayList<SaksiLhpResp>>

    @Headers(ApiLp.ACCEPT)
    @GET("lhp/saksi/{id_saksi}")
    fun detailSaksiLhp(
        @Header("Authorization") token: String,
        @Path("id_saksi") id_saksi: Int?
    ): Call<DetailSaksiLhpResp>


    @Headers(ApiLp.ACCEPT)
    @POST("lhp/{id_lhp}/saksi/{jenis_saksi}")
    fun addSaksiLhp(
        @Header("Authorization") token: String,
        @Path("id_lhp") id_lhp: Int?,
        @Path("jenis_saksi") jenis_saksi: String?,
        @Body saksiLhpReq: SaksiLhpReq
    ): Call<Base1Resp<AddSaksiLhpResp>>

    @Headers(ApiLp.ACCEPT)
    @PATCH("lhp/saksi/{id_saksi}/{jenis_saksi}")
    fun updSaksiLhp(
        @Header("Authorization") token: String,
        @Path("id_saksi") id_saksi: Int?,
        @Path("jenis_saksi") jenis_saksi: String?,
        @Body saksiLhpReq: SaksiLhpReq
    ): Call<Base1Resp<AddSaksiLhpResp>>

    @Headers(ApiLp.ACCEPT)
    @DELETE("lhp/saksi/{id_saksi}")
    fun delSaksiLhp(
        @Header("Authorization") token: String,
        @Path("id_saksi") id_saksi: Int?
    ): Call<BaseResp>

    @Headers(ApiLp.ACCEPT)
    @GET("lhp/{id_lhp}/referensi/penyelidikan")
    fun getRefPenyelidikan(
        @Header("Authorization") token: String,
        @Path("id_lhp") id_lhp: Int?
    ): Call<ArrayList<RefPenyelidikanResp>>

    @Headers(ApiLp.ACCEPT)
    @GET("lhp/referensi/penyelidikan/{id_penyelidikan}")
    fun detailRefPenyelidikan(
        @Header("Authorization") token: String,
        @Path("id_penyelidikan") id_penyelidikan: Int?
    ): Call<RefPenyelidikanResp>

    @Headers(ApiLp.ACCEPT)
    @POST("lhp/{id_lhp}/referensi/penyelidikan")
    fun addRefPenyelidikan(
        @Header("Authorization") token: String,
        @Path("id_lhp") id_lhp: Int?,
        @Body addRefPenyelidikanReq: RefPenyelidikanReq
    ): Call<Base1Resp<AddRefPenyelidikanResp>>


    @Headers(ApiLp.ACCEPT)
    @PATCH("lp/{id_lp}/keterangan/terlapor")
    fun updRefPenyelidikan(
        @Header("Authorization") token: String,
        @Path("id_lp") id_lp: Int?,
        @Body addRefPenyelidikanReq: RefPenyelidikanReq
    ): Call<Base1Resp<DokLpResp>>

    @Headers(ApiLp.ACCEPT)
    @DELETE("lhp/referensi/penyelidikan/{id_penyelidikan}")
    fun delRefPenyelidikan(
        @Header("Authorization") token: String,
        @Path("id_penyelidikan") id_penyelidikan: Int?
    ): Call<BaseResp>

    @Headers(ApiLp.ACCEPT)
    @GET("lhp/{id_lhp}/personel/penyelidik")
    fun getAllPersLidik(
        @Header("Authorization") token: String,
        @Path("id_lhp") id_lhp: Int?
    ): Call<ArrayList<PersonelPenyelidikResp>>

    @Headers(ApiLp.ACCEPT)
    @GET("lhp/personel/penyelidik/{id_pers_lidik}")
    fun detailPersLidik(
        @Header("Authorization") token: String,
        @Path("id_pers_lidik") id_pers_lidik: Int?
    ): Call<DetailPersLidikResp>

    @Headers(ApiLp.ACCEPT)
    @POST("lhp/{id_lhp}/personel/penyelidik")
    fun addPersLidik(
        @Header("Authorization") token: String,
        @Path("id_lhp") id_lhp: Int?,
        @Body personelPenyelidikReq: PersonelPenyelidikReq
    ): Call<Base1Resp<AddPersLidikResp>>

    @Headers(ApiLp.ACCEPT)
    @PATCH("lhp/personel/penyelidik/{id_pers_lidik}")
    fun updPersLidik(
        @Header("Authorization") token: String,
        @Path("id_pers_lidik") id_pers_lidik: Int?,
        @Body personelPenyelidikReq: PersonelPenyelidikReq
    ): Call<Base1Resp<AddPersLidikResp>>

    @Headers(ApiLp.ACCEPT)
    @DELETE("lhp/personel/penyelidik/{id_pers_lidik}")
    fun delPersLidik(
        @Header("Authorization") token: String,
        @Path("id_pers_lidik") id_pers_lidik: Int?
    ): Call<BaseResp>

    @Headers(ApiLp.ACCEPT)
    @GET("lhp/{id_lhp}/personel/terlapor")
    fun listTerlapor(
        @Header("Authorization") token: String,
        @Path("id_lhp") id_lhp: Int?
    ): Call<ArrayList<PersonelPenyelidikResp>>

    @Headers(ApiLp.ACCEPT)
    @POST("lhp/{id_lhp}/personel/terlapor")
    fun addSingleTerlapor(
        @Header("Authorization") token: String,
        @Path("id_lhp") id_lhp: Int?,
        @Body ketTerlaporReq: KetTerlaporReq
    ): Call<Base1Resp<KetTerlaporLhpResp>>

    @Headers(ApiLp.ACCEPT)
    @GET("lhp/personel/terlapor/{id_terlapor}")
    fun detailPersTerlapor(
        @Header("Authorization") token: String,
        @Path("id_terlapor") id_terlapor: Int?
    ): Call<PersonelPenyelidikResp>

    @Headers(ApiLp.ACCEPT)
    @PATCH("lhp/personel/terlapor/{id_terlapor}")
    fun updPersTerlapor(
        @Header("Authorization") token: String,
        @Path("id_terlapor") id_terlapor: Int?,
        @Body ketTerlaporReq: KetTerlaporReq
    ): Call<Base1Resp<KetTerlaporLhpResp>>

    @Headers(ApiLp.ACCEPT)
    @DELETE("lhp/personel/terlapor/{id_terlapor}")
    fun delPersTerlapor(
        @Header("Authorization") token: String,
        @Path("id_terlapor") id_terlapor: Int?
    ): Call<BaseResp>


}