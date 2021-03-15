package id.calocallo.sicape.network

import id.calocallo.sicape.network.request.PutKkeReq
import id.calocallo.sicape.network.request.SkhdReq
import id.calocallo.sicape.network.request.TindDisiplinReq
import id.calocallo.sicape.network.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiSkhd {
    /*------------------SKHD-------------------*/
    @Headers(ApiLp.ACCEPT)
    @GET("skhd")
    fun getSkhd(
        @Header("Authorization") tokenBearer: String
    ): Call<ArrayList<SkhdMinResp>>

    @Headers(ApiLp.ACCEPT)
    @GET("skhd/{id_skhd}/generate/document")
    fun docSkhd(
        @Header("Authorization") tokenBearer: String,
        @Path("id_skhd") id_skhd: Int?
    ): Call<Base1Resp<AddSkhdResp>>

    @Headers(ApiLp.ACCEPT)
    @GET("skhd/{id_skhd}")
    fun detailSkhd(
        @Header("Authorization") tokenBearer: String,
        @Path("id_skhd") id_skhd: Int?
    ): Call<SkhdResp>

    @Headers(ApiLp.ACCEPT)
    @POST("skhd")
    fun addSkhd(
        @Header("Authorization") tokenBearer: String,
        @Body skhdReq: SkhdReq?
    ): Call<Base1Resp<AddSkhdResp>> /*Data skhd saved succesfully*/

    @Headers(ApiLp.ACCEPT)
    @PATCH("skhd/{id_skhd}")
    fun updSkhd(
        @Header("Authorization") tokenBearer: String,
        @Path("id_skhd") id_skhd: Int?,
        @Body skhdReq: SkhdReq?
    ): Call<Base1Resp<AddSkhdResp>> /*Data skhd updated succesfully*/

    @Headers(ApiLp.ACCEPT)
    @DELETE("skhd/{id_skhd}")
    fun delSkhd(
        @Header("Authorization") tokenBearer: String,
        @Path("id_skhd") id_skhd: Int?
    ): Call<BaseResp> /*Data skhd removed succesfully */


    /*------------------PUT KKE-------------------*/
    @Headers(ApiLp.ACCEPT)
    @GET("putkke")
    fun getPutKke(
        @Header("Authorization") tokenBearer: String
    ): Call<ArrayList<PutKkeMinResp>>

    @Headers(ApiLp.ACCEPT)
    @GET("putkke/{id_putkke}/generate/document")
    fun docPutKke(
        @Header("Authorization") tokenBearer: String,
        @Path("id_putkke") id_putkke: Int?
    ): Call<Base1Resp<AddPutKkeResp>>

    @Headers(ApiLp.ACCEPT)
    @GET("putkke/{id_putkke}")
    fun detailPutKke(
        @Header("Authorization") tokenBearer: String,
        @Path("id_putkke") id_putkke: Int?
    ): Call<PutKkeResp>

    @Headers(ApiLp.ACCEPT)
    @POST("putkke")
    fun addPutKke(
        @Header("Authorization") tokenBearer: String,
        @Body putKkeReq: PutKkeReq?
    ): Call<Base1Resp<AddPutKkeResp>> /*Data putkke saved succesfully*/

    @Headers(ApiLp.ACCEPT)
    @PATCH("putkke/{id_putkke}")
    fun updPutKke(
        @Header("Authorization") tokenBearer: String,
        @Path("id_putkke") id_putkke: Int?,
        @Body putKkeReq: PutKkeReq?
    ): Call<Base1Resp<AddPutKkeResp>> /*Data putkke updated succesfully*/

    @Headers(ApiLp.ACCEPT)
    @DELETE("putkke/{id_putkke}")
    fun delPutKke(
        @Header("Authorization") tokenBearer: String,
        @Path("id_putkke") id_putkke: Int?
    ): Call<BaseResp> /*Data putkke removed succesfully */


    /*------------------TINDAKAN DISIPLIN-------------------*/
    @Headers(ApiLp.ACCEPT)
    @GET("tindakan/disiplin")
    fun getPersonelTindDispl(
        @Header("Authorization") tokenBearer: String
    ): Call<ArrayList<TindDisplMinResp>>

    @Headers(ApiLp.ACCEPT)
    @GET("tindakan/disiplin/{id_tind_displ}")
    fun detailPersonelTindDispl(
        @Header("Authorization") tokenBearer: String,
        @Path("id_tind_displ") id_tind_displ: Int?
    ): Call<TindDisplResp>

    @Headers(ApiLp.ACCEPT)
    @POST("tindakan/disiplin")
    fun addPersonelTindDispl(
        @Header("Authorization") tokenBearer: String,
        @Body tindDisiplinReq: TindDisiplinReq?
    ): Call<Base1Resp<AddTindDisplResp>> /*Data tindakan disiplin saved succesfully*/

    @Headers(ApiLp.ACCEPT)
    @PATCH("tindakan/disiplin/{id_tind_displ}")
    fun updPersonelTindDispl(
        @Header("Authorization") tokenBearer: String,
        @Path("id_tind_displ") id_tind_displ: Int?,
        @Body tindDisiplinReq: TindDisiplinReq?
    ): Call<Base1Resp<AddTindDisplResp>> /*Data tindakan disiplin updated succesfully*/

    @Headers(ApiLp.ACCEPT)
    @DELETE("tindakan/disiplin")
    fun delPersonelTindDispl(
        @Header("Authorization") tokenBearer: String,
        @Path("id_tind_displ") id_tind_displ: Int?
    ): Call<BaseResp> /*Data tindakan disiplin removed succesfully*/
}