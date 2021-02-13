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

    @GET("v1/3d2d96a4")
    fun getCatpers(): Call<ArrayList<CatpersResp>>

    @GET("v1/07f03497")
    fun getSkhp(): Call<ArrayList<SkhpResp>>

    @GET("v1/491bcab8")
    fun getWanjak(): Call<ArrayList<WanjakResp>>

    @GET("v1/953c2a3e")
    fun getLapbul(): Call<ArrayList<LapBulResp>>

    @GET("v1/ab70f006")
    fun getAnev(): Call<ArrayList<AnevResp>>

    @GET("v1/4a245c34")
    fun getFilterKesatuan(): Call<ArrayList<AnevResp>>

    @GET("v1/0af80f7f")
    fun getFilterPangkat(): Call<ArrayList<FilterPangkatResp>>

    @GET("v1/911a895e")
    fun getFilterPelanggaran(): Call<ArrayList<FilterPelanggaranResp>>

    @GET("v1/b5ae1dad")
    fun getAdmin(): Call<ArrayList<AdminResp>>

    @GET("v1/af0598a5")
    fun getOperator(): Call<ArrayList<OperatorResp>>

    @GET("v1/cbbe4742")
    fun getPolresAll(): Call<ArrayList<PolresResp>>

    @GET("v1/ab0e07a3")
    fun getSatResBjm(): Call<ArrayList<SatPolresResp>>

    @GET("v1/7512d391")
    fun getUnitSekBjm(): Call<ArrayList<UnitPolsekResp>>

    @GET("v1/3691308f")
    fun getPolsek(): Call<ArrayList<PolsekResp>>


}
