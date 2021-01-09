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

        const val SATUAN_KERJA = "satuan/kerja"

        const val PERSONEL = "personel/identitas"
        const val UPD_PERSONEL = "personel/{id_personel}/identitas"
        const val DELETE_PERSONEL = "personel/{id_personel}"

        const val RIWAYAT_PERSONEL = "personel/{id_personel}/riwayat/{nama_riwayat}"

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
        const val UPD_ALAMAT = "personel/riwayat/alamat/{id_alamat}"

        const val ORGANISASI_SINGLE = "personel/{id_personel}/riwayat/organisasi"
        const val UPD_ORGANISASI = "personel/riwayat/organisasi/{id_organisasi}"

        const val PERJUANGAN_SINGLE = "personel/{id_personel}/riwayat/perjuangan"
        const val UPD_PERJUANGAN = "personel/riwayat/perjuangan/{id_perjuangan}"

        const val PENGHARGAAN_SINGLE = "personel/{id_personel}/riwayat/penghargaan"
        const val UPD_PENGHARGAAN = "personel/riwayat/penghargaan/{id_penghargaan}"

        const val KELUARGA =
            "personel/{id_personel}/keluarga/{status_keluarga}" //ayah-->mertua-perempuan

        const val ANAK_SINGLE = "personel/{id_personel}/anak"
        const val UPD_ANAK = "personel/anak/{id_anak}"

        const val SAUDARA_SINGLE = "personel/{id_personel}/saudara"
        const val UPD_SAUDARA = "personel/saudara/{id_saudara}"

        const val ORANGS_SINGLE = "personel/{id_personel}/orang/{nama_orangs}"
        const val UPD_ORANGS = "personel/orang/{nama_orangs}/{id_orangs}"

        const val TOKOH_SINGLE = "personel/{id_personel}/tokoh/dikagumi"
        const val UPD_TOKOH = "personel/tokoh/dikagumi/{id_tokoh}"

        const val SAHABAT_SINGLE = "personel/{id_personel}/sahabat"
        const val UPD_SAHABAT = "personel/sahabat/{id_sahabat}"

        const val MED_INFO_SINGLE = "personel/{id_personel}/media/disenangi"
        const val UPD_MED_INFO = "personel/media/disenangi/{id_med_info}"

        const val MED_SOS_SINGLE = "personel/{id_personel}/media/sosial"
        const val UPD_MED_SOS = "personel/media/sosial/{id_med_sos}"


        const val SIGNALEMENT = "personel/{id_personel}/signalement"

        const val RELASI_SINGLE = "personel/{id_personel}/relasi/{jenis_relasi}"
        const val UPD_RELASI = "personel/relasi/{id_relasi}"

        const val DIHUKUM_SINGLE = "personel/{id_personel}/pernah/dihukum"
        const val UPD_DIHUKUM = "personel/pernah/dihukum/{id_dihukum}"

        const val FOTO_MUKA = "file/foto/upload/foto_muka"
        const val FOTO_KANAN = "file/foto/upload/foto_kanan"
        const val FOTO_KIRI = "file/foto/upload/foto_kiri"

        const val FOTO_SINGLE = "personel/{id_personel}/foto"

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
    @GET(SATUAN_KERJA)
    fun showSatker(@Header("Authorization") tokenBearer: String): Call<ArrayList<SatKerResp>>

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
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @PATCH(UPD_PEKERJAAN_LUAR)
    fun updatePekerjaanLuar(
        @Header("Authorization") token: String,
        @Path("id_pekerjaan") id_pekerjaan: String,
        @Body pekerjaanLuarReq: PekerjaanODinasReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @DELETE(UPD_PEKERJAAN_LUAR)
    fun deletePekerjaanLuar(
        @Header("Authorization") token: String,
        @Path("id_pekerjaan") id_pekerjaan: String
    ): Call<BaseResp>

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
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @PATCH(UPD_ALAMAT)
    fun updateAlamatSingle(
        @Header("Authorization") token: String,
        @Path("id_alamat") id_alamat: String,
        @Body alamatReq: AlamatReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @DELETE(UPD_ALAMAT)
    fun deleteAlamat(
        @Header("Authorization") token: String,
        @Path("id_alamat") id_alamat: String
    ): Call<BaseResp>

    //    <------------------ORGANISASI, PERJUANGAN CITA-CITA, PENGHARGAAN----------------->
    /**
     * ORGANISASI
     */
    @Headers(ACCEPT)
    @GET(ORGANISASI_SINGLE)
    fun showOrganisasi(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String
    ): Call<ArrayList<OrganisasiResp>>

    @Headers(ACCEPT)
    @POST(ORGANISASI_SINGLE)
    fun addOrganisasiSingle(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String,
        @Body organisasiReq: OrganisasiReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @PATCH(UPD_ORGANISASI)
    fun updateOrganisasiSingle(
        @Header("Authorization") token: String,
        @Path("id_organisasi") id_organisasi: String,
        @Body organisasiReq: OrganisasiReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @DELETE(UPD_ORGANISASI)
    fun deleteOrganisasi(
        @Header("Authorization") token: String,
        @Path("id_organisasi") id_organisasi: String
    ): Call<BaseResp>

    /**
     * PERJUANGAN CITA-CITA
     */
    @Headers(ACCEPT)
    @GET(PERJUANGAN_SINGLE)
    fun showPerjuangan(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String
    ): Call<ArrayList<PerjuanganResp>>

    @Headers(ACCEPT)
    @POST(PERJUANGAN_SINGLE)
    fun addPerjuanganSingle(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String,
        @Body perjuanganCitaReq: PerjuanganCitaReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @PATCH(UPD_PERJUANGAN)
    fun updatePerjuanganSingle(
        @Header("Authorization") token: String,
        @Path("id_perjuangan") id_perjuangan: String,
        @Body perjuanganCitaReq: PerjuanganCitaReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @DELETE(UPD_PERJUANGAN)
    fun deletePerjuangan(
        @Header("Authorization") token: String,
        @Path("id_perjuangan") id_perjuangan: String
    ): Call<BaseResp>


    /**
     * PENGHARGAAN
     */
    @Headers(ACCEPT)
    @GET(PENGHARGAAN_SINGLE)
    fun showPenghargaan(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String
    ): Call<ArrayList<PenghargaanResp>>

    @Headers(ACCEPT)
    @POST(PENGHARGAAN_SINGLE)
    fun addPenghargaanSingle(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String,
        @Body penghargaanReq: PenghargaanReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @PATCH(UPD_PENGHARGAAN)
    fun updatePenghargaanSingle(
        @Header("Authorization") token: String,
        @Path("id_penghargaan") id_penghargaan: String,
        @Body penghargaanReq: PenghargaanReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @DELETE(UPD_PENGHARGAAN)
    fun deletePenghargaan(
        @Header("Authorization") token: String,
        @Path("id_penghargaan") id_penghargaan: String
    ): Call<BaseResp>

    //    <------------------KELUARGA----------------->
    @Headers(ACCEPT)
    @GET(KELUARGA)
    fun showKeluarga(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String,
        @Path("status_keluarga") status_keluarga: String
    ): Call<KeluargaResp>

    @Headers(ACCEPT)
    @POST(KELUARGA)
    fun addKeluargaSingle(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String,
        @Path("status_keluarga") status_keluarga: String,
        @Body keluargaReq: KeluargaReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @PATCH(KELUARGA)
    fun updateKeluargaSingle(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String,
        @Path("status_keluarga") status_keluarga: String,
        @Body keluargaReq: KeluargaReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @DELETE(KELUARGA)
    fun deleteKeluarga(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String,
        @Path("status_keluarga") status_keluarga: String
    ): Call<BaseResp>

    //    <------------------ANAK----------------->
    @Headers(ACCEPT)
    @GET(ANAK_SINGLE)
    fun showAnak(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String
    ): Call<ArrayList<AnakResp>>

    @Headers(ACCEPT)
    @POST(ANAK_SINGLE)
    fun addAnakSingle(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String,
        @Body anakReq: AnakReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @PATCH(UPD_ANAK)
    fun updateAnakSingle(
        @Header("Authorization") token: String,
        @Path("id_anak") id_anak: String,
        @Body anakReq: AnakReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @DELETE(UPD_ANAK)
    fun deleteAnak(
        @Header("Authorization") token: String,
        @Path("id_anak") id_anak: String
    ): Call<BaseResp>

    //    <------------------SAUDARA----------------->
    @Headers(ACCEPT)
    @GET(SAUDARA_SINGLE)
    fun showSaudara(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String
    ): Call<ArrayList<SaudaraResp>>

    @Headers(ACCEPT)
    @POST(SAUDARA_SINGLE)
    fun addSaudaraSingle(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String,
        @Body saudaraReq: SaudaraReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @PATCH(UPD_SAUDARA)
    fun updateSaudaraSingle(
        @Header("Authorization") token: String,
        @Path("id_saudara") id_saudara: String,
        @Body saudaraReq: SaudaraReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @DELETE(UPD_SAUDARA)
    fun deleteSaudara(
        @Header("Authorization") token: String,
        @Path("id_saudara") id_saudara: String
    ): Call<BaseResp>

    //    <------------------ORANGS----------------->
    @Headers(ACCEPT)
    @GET(ORANGS_SINGLE)
    fun showOrangs(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String,
        @Path("nama_orangs") nama_orangs: String
    ): Call<ArrayList<OrangsResp>>

    @Headers(ACCEPT)
    @POST(ORANGS_SINGLE)
    fun addOrangsSingle(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String,
        @Path("nama_orangs") nama_orangs: String,
        @Body orangsReq: OrangsReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @PATCH(UPD_ORANGS)
    fun updateOrangsSingle(
        @Header("Authorization") token: String,
        @Path("nama_orangs") nama_orangs: String,
        @Path("id_orangs") id_orangs: String,
        @Body orangsReq: OrangsReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @DELETE(UPD_ORANGS)
    fun deleteOrangs(
        @Header("Authorization") token: String,
        @Path("nama_orangs") nama_orangs: String,
        @Path("id_orangs") id_orangs: String
    ): Call<BaseResp>

    //    <------------------TOKOH----------------->
    @Headers(ACCEPT)
    @GET(TOKOH_SINGLE)
    fun showTokoh(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String
    ): Call<ArrayList<TokohResp>>

    @Headers(ACCEPT)
    @POST(TOKOH_SINGLE)
    fun addTokohSingle(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String,
        @Body tokohReq: TokohReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @PATCH(UPD_TOKOH)
    fun updateTokohSingle(
        @Header("Authorization") token: String,
        @Path("id_tokoh") id_tokoh: String,
        @Body tokohReq: TokohReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @DELETE(UPD_TOKOH)
    fun deleteTokoh(
        @Header("Authorization") token: String,
        @Path("id_tokoh") id_tokoh: String
    ): Call<BaseResp>

    //    <------------------SAHABAT----------------->
    @Headers(ACCEPT)
    @GET(SAHABAT_SINGLE)
    fun showSahabat(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String
    ): Call<ArrayList<SahabatResp>>

    @Headers(ACCEPT)
    @POST(SAHABAT_SINGLE)
    fun addSahabatSingle(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String,
        @Body sahabatReq: SahabatReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @PATCH(UPD_SAHABAT)
    fun updateSahabatSingle(
        @Header("Authorization") token: String,
        @Path("id_sahabat") id_sahabat: String,
        @Body sahabatReq: SahabatReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @DELETE(UPD_SAHABAT)
    fun deleteSahabat(
        @Header("Authorization") token: String,
        @Path("id_sahabat") id_sahabat: String
    ): Call<BaseResp>

    //    <------------------MEDIA INFORMASI----------------->
    @Headers(ACCEPT)
    @GET(MED_INFO_SINGLE)
    fun showMedInfo(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String
    ): Call<ArrayList<MedInfoResp>>

    @Headers(ACCEPT)
    @POST(MED_INFO_SINGLE)
    fun addMedInfoSingle(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String,
        @Body medInfoReq: MedInfoReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @PATCH(UPD_MED_INFO)
    fun updateMedInfoSingle(
        @Header("Authorization") token: String,
        @Path("id_med_info") id_med_info: String,
        @Body medInfoReq: MedInfoReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @DELETE(UPD_MED_INFO)
    fun deleteMedInfo(
        @Header("Authorization") token: String,
        @Path("id_med_info") id_med_info: String
    ): Call<BaseResp>

    //    <------------------MEDIA SOSIAL----------------->
    @Headers(ACCEPT)
    @GET(MED_SOS_SINGLE)
    fun showMedSos(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String
    ): Call<ArrayList<MedSosResp>>

    @Headers(ACCEPT)
    @POST(MED_SOS_SINGLE)
    fun addMedSosSingle(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String,
        @Body medSosReq: MedSosReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @PATCH(UPD_MED_SOS)
    fun updateMedSosSingle(
        @Header("Authorization") token: String,
        @Path("id_med_sos") id_med_sos: String,
        @Body medSosReq: MedSosReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @DELETE(UPD_MED_SOS)
    fun deleteMedSos(
        @Header("Authorization") token: String,
        @Path("id_med_sos") id_med_sos: String
    ): Call<BaseResp>

    //    <------------------SIGNALEMENT----------------->

    @Headers(ACCEPT)
    @GET(SIGNALEMENT)
    fun getSignalement(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String
    ): Call<SignalementModel>

    @Headers(ACCEPT)
    @PATCH(SIGNALEMENT)
    fun updateSignalement(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String,
        @Body signalementModel: SignalementModel
    ): Call<BaseResp>

    //    <------------------RELASI----------------->
    @Headers(ACCEPT)
    @GET(RELASI_SINGLE)
    fun showRelasi(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String,
        @Path("jenis_relasi") jenis_relasi: String
    ): Call<ArrayList<RelasiResp>>

    @Headers(ACCEPT)
    @POST(RELASI_SINGLE)
    fun addSingleRelasi(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String,
        @Path("jenis_relasi") jenis_relasi: String,
        @Body relasiReq: RelasiReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @PATCH(UPD_RELASI)
    fun updateSingleRelasi(
        @Header("Authorization") token: String,
        @Path("id_relasi") id_relasi: String,
        @Body relasiReq: RelasiReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @DELETE(UPD_RELASI)
    fun deleteSingleRelasi(
        @Header("Authorization") token: String,
        @Path("id_relasi") id_relasi: String
    ): Call<BaseResp>


    //    <------------------DIHUKUM----------------->
    @Headers(ACCEPT)
    @GET(DIHUKUM_SINGLE)
    fun showDihukum(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String
    ): Call<ArrayList<PernahDihukumResp>>

    @Headers(ACCEPT)
    @POST(DIHUKUM_SINGLE)
    fun addSingleDihukum(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String,
        @Body hukumanReq: HukumanReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @PATCH(UPD_DIHUKUM)
    fun updateSingleDihukum(
        @Header("Authorization") token: String,
        @Path("id_dihukum") id_dihukum: String,
        @Body hukumanReq: HukumanReq
    ): Call<BaseResp>

    @Headers(ACCEPT)
    @DELETE(UPD_DIHUKUM)
    fun deleteSingleDihukum(
        @Header("Authorization") token: String,
        @Path("id_dihukum") id_dihukum: String
    ): Call<BaseResp>

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

    @Headers(ACCEPT)
    @GET(FOTO_SINGLE)
    fun getFoto(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String
    ): Call<FotoModel>

    @Headers(ACCEPT)
    @PATCH(FOTO_SINGLE)
    fun updateFoto(
        @Header("Authorization") token: String,
        @Path("id_personel") id_personel: String,
        @Body editFotoReq: EditFotoReq
    ): Call<BaseResp>

}
