package id.calocallo.sicape.network

import id.calocallo.sicape.model.PutKkeOnRpphModel
import id.calocallo.sicape.model.SkhdOnRpsModel
import id.calocallo.sicape.network.response.RpphResp
import id.calocallo.sicape.network.response.RpsResp
import id.calocallo.sicape.network.response.SktbResp
import retrofit2.Call
import retrofit2.http.GET

interface DummyApi {

    @GET("v1/8754f1b7")
    fun getSkhdOnRps(): Call<ArrayList<SkhdOnRpsModel>>

    @GET("v1/cd882765")
    fun getRps(): Call<ArrayList<RpsResp>>


    @GET("v1/0b23c533")
    fun getRpph(): Call<ArrayList<RpphResp>>

    @GET("v1/c1d3f140")
    fun getPutKkeOnRpph(): Call<ArrayList<PutKkeOnRpphModel>>

    @GET("v1/1950e0a6")
    fun getSktb(): Call<ArrayList<SktbResp>>
}
