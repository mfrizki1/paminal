package id.calocallo.sicape.network

import id.calocallo.sicape.network.request.AddPersonelReq
import id.calocallo.sicape.network.request.LoginReq
import id.calocallo.sicape.network.response.LoginResp
import retrofit2.Call
import retrofit2.http.*

interface Api {
    companion object {
        const val ACCEPT = "Accept: application/json"
        const val LOGIN = "login"
        const val PERSONEL = "personel"
        const val UPD_PERSONEL = "personel/{id_personel}"
        const val RIWAYAT = "riwayat"
        const val PENDIDIKAN = "${RIWAYAT}/pendidikan"
    }

    @Headers("Accept: application/json")
    @POST(LOGIN)
    fun login(@Body loginReq: LoginReq): Call<LoginResp>

//    @GET(PERSONEL)
//    fun showPersonel(): Call<>

//    @POST(PERSONEL)
//    fun addPersonel(@Body addPersonelReq: AddPersonelReq): Call<>

//    @PATCH(UPD_PERSONEL)
//    fun updatePersonel(
//        @Path("id_personel") id_persone: String,
//        @Body addPersonelReq: AddPersonelReq
//    ): Call<>

//    @DELETE("personel")
//    fun deletePersonel(): Call<>
}