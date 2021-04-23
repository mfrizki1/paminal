package id.calocallo.sicape.network

import id.calocallo.sicape.network.request.AdminReq
import id.calocallo.sicape.network.request.SatkerReq
import id.calocallo.sicape.network.request.SettingReq
import id.calocallo.sicape.network.response.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiSatker {
    @Headers(ApiPersonel.ACCEPT)
    @GET("satuan/kerja/{jenis_polisi}")
     fun listSatker(
        @Header("Authorization") tokenBearer: String, @Path("jenis_polisi") jenis_polisi: String
    ): Call<ArrayList<SatKerResp>>


    @Headers(ApiPersonel.ACCEPT)
    @POST("satuan/kerja/{jenis_polisi}")
     fun addSatker(
        @Header("Authorization") token: String,
        @Path("jenis_polisi") jenis_polisi: String,
        @Body satkerReq: SatkerReq
    ): Call<Base1Resp<SatKerResp>>

    @Headers(ApiPersonel.ACCEPT)
    @PATCH("satuan/kerja/{id_satker}/{jenis_polisi}")
     fun updSatker(
        @Header("Authorization") token: String,
        @Path("id_satker") id_satker: Int,
        @Path("jenis_polisi") jenis_polisi: String,
        @Body satkerReq: SatkerReq
    ): Call<Base1Resp<SatKerResp>>

    @Headers(ApiPersonel.ACCEPT)
    @DELETE("satuan/kerja/{id_satker}")
    fun delSatker(
        @Header("Authorization") token: String?,
        @Path("id_satker") id_satker: Int?
    ): Call<BaseResp>

    @Headers(ApiPersonel.ACCEPT)
    @PATCH("setting")
     fun updPolda(
        @Header("Authorization") token: String,
        @Body settingReq: SettingReq
    ): Call<Base1Resp<SettingResp>>
}