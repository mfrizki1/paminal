package id.calocallo.sicape.network

import id.calocallo.sicape.model.*
import id.calocallo.sicape.network.request.*
import id.calocallo.sicape.network.response.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface Api {
    companion object {
        const val ACCEPT = "Accept: application/json"
        const val LOGIN = "login"

        const val PERSONEL = "personel"
        const val UPD_PERSONEL = "personel/{id_personel}"
        const val DELETE_PERSONEL = "personel/{id_personel}"

        const val PENDIDIKAN_MANY = "riwayat/pendidikan/{id_personel}/many"
//        const val PENDIDIKAN_SINGLE = "${RIWAYAT}/pendidikan/{id_personel}/{jenis}"
        const val PENDIDIKAN_SINGLE = "personel/{id_personel}/riwayat/pendidikan/{jenis}"
        const val UPD_PENDIDIKAN = "personel/riwayat/pendidikan/{id_riwayat_pendidikan}"

        const val PEKERJAAN_SINGLE = "personel/{id_personel}/riwayat/pekerjaan"
        const val PEKERJAAN_MANY =
            "riwayat/pekerjaan/{id_personel}/many" //Data riwayat pekerjaan saved succesfully
        const val UPD_PEKERJAAN = "personel/riwayat/pekerjaan/{id_riwayat_pekerjaan}"

        const val PEKERJAAN_LUAR_SINGLE = "personel/{id_personel}/pekerjaan/diluar/dinas"
        const val UPD_PEKERJAAN_LUAR = "personel/pekerjaan/diluar/dinas/{id_pekerjaan}"

        const val ALAMAT_SINGLE = "personel/{id_personel}/riwayat/alamat"
        const val UPD_ALAMAT = "personle/riwayat/{id_alamat}}"

        const val FOTO_MUKA = "file/foto/upload/foto_muka"
        const val FOTO_KANAN = "file/foto/upload/foto_kanan"
        const val FOTO_KIRI = "file/foto/upload/foto_kiri"

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
    fun addAllPersonel(
        @Header("Authorization") tokenBearer: String,
        @Body personelRequest: AllPersonelModel
    ): Call<BaseResp>

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
    fun addPendMany(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String,
        @Body addPendReq: AddPendReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @GET(PENDIDIKAN_SINGLE)
    fun showPendByJenis(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String,
        @Path("jenis") jenis_pend: String
    ): Call<ArrayList<PendidikanModel>>


    @Headers(ACCEPT)
    @GET(PENDIDIKAN_SINGLE)
    fun addPendSingle(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String,
        @Path("jenis") jenis_pend: String
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @PATCH(UPD_PENDIDIKAN)
    fun updatePend(
        @Header("Authorization") token: String,
        @Path("id_riwayat_pendidikan") id_riwayat_pendidikan: String,
        @Body singlePendReq: SinglePendReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @DELETE(UPD_PENDIDIKAN)
    fun deletePend(
        @Header("Authorization") token: String,
        @Path("id_riwayat_pendidikan") id_riwayat_pendidikan: String
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @POST(PENDIDIKAN_SINGLE)
    fun addSinglePend(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String,
        @Path("jenis") jenis_pend: String,
        @Body singlePendReq: SinglePendReq
    ): Call<BaseResp>


//    <------------------PEKERJAAN----------------->


    @Headers(ACCEPT)
    @GET(PEKERJAAN_SINGLE)
    fun showPekerjaan(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String
    ): Call<ArrayList<PekerjaanModel>>

    @Headers(ACCEPT)
    @POST(PEKERJAAN_MANY)
    fun addPekerjaanMany(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String,
        @Body addPekerjaanReq: AddPekerjaanReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @POST(PEKERJAAN_SINGLE)
    fun addPekerjaanSingle(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String,
        @Body singlePekerjaanReq: AddSinglePekerjaanReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @PATCH(UPD_PEKERJAAN)
    fun updatePekerjaan(
        @Header("Authorization") token: String,
        @Path("id_riwayat_pekerjaan") id_riwayat_pekerjaan: String,
        @Body singlePekerjaanReq: AddSinglePekerjaanReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @DELETE(UPD_PEKERJAAN)
    fun deletePekerjaan(
        @Header("Authorization") token: String,
        @Path("id_riwayat_pekerjaan") id_riwayat_pekerjaan: String
    ): Call<BaseResp>


    @Headers(ACCEPT)
    @GET(PEKERJAAN_LUAR_SINGLE)
    fun showPekerjaanLuar(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String
    ): Call<ArrayList<PekerjaanLuarResp>>

    @Headers(ACCEPT)
    @POST(PEKERJAAN_LUAR_SINGLE)
    fun addPekerjaanLuar(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String,
        @Body pekerjaanODinasReq: PekerjaanODinasReq
    ):Call<BaseResp>

    @Headers(ACCEPT)
    @PATCH(UPD_PEKERJAAN_LUAR)
    fun updatePekerjaanLuar(
        @Header("Authorization") token: String,
        @Path("id_pekerjaan") id_pekerjaan: String,
        @Body pekerjaanLuarReq: PekerjaanODinasReq
    ):Call<BaseResp>

    @Headers(ACCEPT)
    @DELETE(UPD_PEKERJAAN_LUAR)
    fun deletePekerjaanLuar(
        @Header("Authorization") token: String,
        @Path("id_pekerjaan") id_pekerjaan: String
    ):Call<BaseResp>

    //    <------------------ALAMATT----------------->
    @Headers(ACCEPT)
    @GET(ALAMAT_SINGLE)
    fun showAlamat(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String
    ): Call<ArrayList<AlamatResp>>

    @Headers(ACCEPT)
    @POST(ALAMAT_SINGLE)
    fun addAlamatSingle(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String,
        @Body alamatReq: AlamatReq
    ):Call<BaseResp>

    @Headers(ACCEPT)
    @PATCH(UPD_ALAMAT)
    fun updateAlamatSingle(
        @Header("Authorization") token: String,
        @Path("id_alamat") id_alamat: String,
        @Body alamatReq: AlamatReq
    ):Call<BaseResp>

    @Headers(ACCEPT)
    @DELETE(UPD_ALAMAT)
    fun deleteAlamat(
        @Header("Authorization") token: String,
        @Path("id_alamat") id_alamat: String
    ):Call<BaseResp>



    //    <------------------FOTO----------------->
    @Multipart
    @Headers(ACCEPT)
    @POST(FOTO_MUKA)
    fun uploadMuka(
        @Header("Authorization") token: String,
        @Part foto: MultipartBody.Part
    ): Call<Base1Resp<FotoResp>>

    @Multipart
    @Headers(ACCEPT)
    @POST(FOTO_KIRI)
    fun uploadKiri(
        @Header("Authorization") token: String,
        @Part foto: MultipartBody.Part
    ): Call<Base1Resp<FotoResp>>

    @Multipart
    @Headers(ACCEPT)
    @POST(FOTO_KANAN)
    fun uploadKanan(
        @Header("Authorization") token: String,
        @Part foto: MultipartBody.Part
    ): Call<Base1Resp<FotoResp>>


}