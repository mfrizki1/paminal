package id.calocallo.sicape.network

import id.calocallo.sicape.model.ParentListPendDinas
import id.calocallo.sicape.model.ParentListPendOther
import id.calocallo.sicape.model.ParentListPendUmum
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.request.AddPersonelReq
import id.calocallo.sicape.network.request.LoginReq
import id.calocallo.sicape.network.response.AddPersonelResp
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.network.response.LoginResp
import retrofit2.Call
import retrofit2.http.*

interface Api {
    companion object {
        const val ACCEPT = "Accept: application/json"
        const val LOGIN = "login"
        const val PERSONEL = "personel"
        const val UPD_PERSONEL = "personel/{id_personel}"
        const val DELETE_PERSONEL = "personel/{id_personel}"
        const val RIWAYAT = "riwayat"
        const val PENDIDIKAN_MANY = "${RIWAYAT}/pendidikan/many"
        const val PENDIDIKAN = "${RIWAYAT}/pendidikan"
        const val AUTH = "auth"
        const val GET_USER = "${AUTH}/profile"
        const val LOGOUT = "${AUTH}/logout"
    }

    @Headers(ACCEPT)
    @POST(LOGIN)
    fun login(@Body loginReq: LoginReq): Call<LoginResp>

    @Headers(ACCEPT)
    @GET(GET_USER)
    fun getUser(@Header("Authorization") tokenBearer: String): Call<PersonelModel>

    @Headers(ACCEPT)
    @GET(LOGOUT)
    fun logout(@Header("Authorization") token: String): Call<BaseResp>

    @Headers(ACCEPT)
    @GET(PERSONEL)
    fun showPersonel(@Header("Authorization") tokenBearer: String): Call<ArrayList<PersonelModel>>

    @Headers(ACCEPT)
    @POST(PERSONEL)
    fun addPersonel(
        @Header("Authorization") tokenBearer: String,
        @Body addPersonelRequest: AddPersonelReq
    ): Call<AddPersonelResp>

    @Headers(ACCEPT)
    @PATCH(UPD_PERSONEL)
    fun updatePersonel(
        @Header("Authorization") token: String,
        @Path("id_personel") id_persone: String,
        @Body addPersonelReq: AddPersonelReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @DELETE(DELETE_PERSONEL)
    fun deletePersonel(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @POST(PENDIDIKAN_MANY)
    fun addPend(
        @Header("Authorization") token: String,
        @Body parentListPendUmum: ParentListPendUmum,
        parentListPendDinas: ParentListPendDinas,
        parentListPendOther: ParentListPendOther
    ): Call<BaseResp>

}