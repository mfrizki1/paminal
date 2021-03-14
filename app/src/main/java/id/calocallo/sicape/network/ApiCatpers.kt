package id.calocallo.sicape.network

import id.calocallo.sicape.network.request.AnevReq
import id.calocallo.sicape.network.request.FilterReq
import id.calocallo.sicape.network.response.AnevResp
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.BaseAnev
import id.calocallo.sicape.network.response.CatpersLapbulResp
import retrofit2.Call
import retrofit2.http.*

interface ApiCatpers {
    @Headers(ApiLp.ACCEPT)
    @POST("catpers")
    fun getListCatpers(
        @Header("Authorization") tokenBearer: String,
        @Body filterReq : FilterReq
    ): Call<ArrayList<CatpersLapbulResp>>

    @Headers(ApiLp.ACCEPT)
    @GET("catpers/document")
    fun dokCatpers(
        @Header("Authorization") tokenBearer: String
    ): Call<ArrayList<CatpersLapbulResp>>

  @Headers(ApiLp.ACCEPT)
    @POST("lapbul")
    fun getListLapbul(
        @Header("Authorization") tokenBearer: String,
        @Body filterReq : FilterReq
    ): Call<ArrayList<CatpersLapbulResp>>

    @Headers(ApiLp.ACCEPT)
    @GET("lapbul/document")
    fun dokLapbul(
        @Header("Authorization") tokenBearer: String
    ): Call<ArrayList<CatpersLapbulResp>>

    @Headers(ApiLp.ACCEPT)
    @POST("anev")
    fun getListAnev(
        @Header("Authorization") tokenBearer: String,
        @Body filterReq : FilterReq
    ): Call<BaseAnev>
}