package id.calocallo.sicape.network

import id.calocallo.sicape.network.request.FilterReq
import id.calocallo.sicape.network.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiCatpers {
    @Headers(ApiLp.ACCEPT)
    @POST("catpers")
    fun getListCatpers(
        @Header("Authorization") tokenBearer: String,
        @Body filterReq: FilterReq
    ): Call<ArrayList<CatpersLapbulResp>>

    @Headers(ApiLp.ACCEPT)
    @POST("catpers/generate/document")
    fun dokCatpers(
        @Header("Authorization") tokenBearer: String,
        @Body filreReq: FilterReq
    ): Call<Base1Resp<DokCatpersResp>>

    @Headers(ApiLp.ACCEPT)
    @POST("lapbul")
    fun getListLapbul(
        @Header("Authorization") tokenBearer: String,
        @Body filterReq: FilterReq
    ): Call<ArrayList<CatpersLapbulResp>>

    @Headers(ApiLp.ACCEPT)
    @POST("lapbul/generate/document")
    fun docLapbul(
        @Header("Authorization") tokenBearer: String,
        @Body filterReq: FilterReq
    ): Call<Base1Resp<DokLapbulResp>>


    @Headers(ApiLp.ACCEPT)
    @POST("anev")
    fun getListAnev(
        @Header("Authorization") tokenBearer: String,
        @Body filterReq: FilterReq
    ): Call<BaseAnev>
}