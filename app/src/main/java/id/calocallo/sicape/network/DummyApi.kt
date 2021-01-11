package id.calocallo.sicape.network

import id.calocallo.sicape.model.PutKkeOnRpphModel
import id.calocallo.sicape.model.SkhdOnRpsModel
import id.calocallo.sicape.model.SktbOnSp3
import id.calocallo.sicape.model.SkttOnSp3
import id.calocallo.sicape.network.response.*
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

    @GET("v1/93babfa8")
    fun getSktt(): Call<ArrayList<SkttResp>>

    @GET("v1/5fdd423a")
    fun getSp3(): Call<ArrayList<Sp3Resp>>

    //lp without lhp
    @GET("v1/0c6c4a1b")
    fun getLpNotHaveLhp(): Call<ArrayList<LpCustomResp>>

    //lp with lhp but without skhd/putkke/sktt
    @GET("v1/0c6c4a1b")
    fun getLpNotHaveSktbPutkkeSktt(): Call<ArrayList<LpCustomResp>>

    /*sktb without sp4*/
    @GET("v1/2f89f427")
    fun getSktbWithoutSp3(): Call<ArrayList<SktbOnSp3>>

    /*sktt without sp4*/
    @GET("v1/b827c5db")
    fun getSkttWithoutSp3(): Call<ArrayList<SkttOnSp3>>


}
