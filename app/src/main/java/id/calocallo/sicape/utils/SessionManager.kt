package id.calocallo.sicape.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.calocallo.sicape.R
import id.calocallo.sicape.model.*
import id.calocallo.sicape.network.request.*

class SessionManager(context: Context) {
    private lateinit var parentListPendUmum: ParentListPendUmum
    private lateinit var parentListPendDinas: ParentListPendDinas
    private lateinit var listUmum: ArrayList<AddPendidikanModel>
    private lateinit var listDinas: ArrayList<PendDinasModel>
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    private var prefsPers: SharedPreferences = context.getSharedPreferences(context.getString(R.string.DATA), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
        const val HAK_AKSES = "hak_akses"
        const val USER = "user"
        const val ID_PERSONEL = "id_personel"
        const val isUserLogin = "userLogin"

        const val PERSONEL = "PERSONEL"
        const val UMUM = "umum"
        const val DINAS = "dinas"
        const val LAIN = "LAIN"
        const val PEKERJAAN = "pekerjaan"
        const val DILUAR_DINAS = "DILUAR_DINAS"
        const val ALAMAT = "ALAMAT"
        const val ORGANISASI = "ORGANISASI"
        const val PENGHARGAAN = "PENGHARGAAN"
        const val PERJUANGAN = "PERJUANGAN"
        const val PASANGAN = "PASANGAN"
        const val AYAH = "AYAH"
        const val AYAH_TIRI = "AYAH_TIRI"
        const val IBU = "IBU"
        const val IBU_TIRI = "IBU_TIRI"
        const val MERTUA_LAKI = "MERTUA_LAKI"
        const val MERTUA_PEREMPUAN = "MERTUA_PEREMPUAN"
        const val ANAK = "ANAK"
        const val SAUDARA = "SAUDARA"
        const val ORANG_BERJASA = "ORANG_BERJASA"
        const val ORANG_DISEGANI = "ORANG_DISEGANI"
        const val TOKOH = "TOKOH"
        const val SAHABAT = "SAHABAT"
        const val MEDIA = "MEDIA"
        const val MEDSOS = "MEDSOS"
        const val SIGNALEMENT = "SIGNALEMENT"
        const val FOTO = "FOTO"
        const val RELASI = "RELASI"
        const val HUKUMAN = "HUKUMAN"
        const val CATPER = "CATPER"
    }

    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    fun saveID(id_personel: Int) {
        val editor = prefs.edit()
        editor.putInt(ID_PERSONEL, id_personel)
        editor.apply()
    }

    fun fetchID(): Int {
        return prefs.getInt(ID_PERSONEL, 0)
    }

    fun saveHakAkses(hak_akses: String) {
        val editor = prefs.edit()
        editor.putString(HAK_AKSES, hak_akses)
        editor.apply()
    }

    fun fetchHakAkses(): String? {
        return prefs.getString(HAK_AKSES, null)
    }

    fun saveUser(obj: PersonelModel?) {
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(obj)
        editor.putString(USER, json)
        editor.commit()
    }

    fun fetchUser(): PersonelModel? {
        val emptyUser = Gson().toJson(PersonelModel)
        return Gson().fromJson(
            prefs.getString(USER, emptyUser),
            object : TypeToken<PersonelModel>() {}.type
        )
    }

    fun getUserLogin(): Boolean {
        return prefs.getBoolean(isUserLogin, false)
    }

    fun setUserLogin(isLogin: Boolean) {
        val editor = prefs.edit()
        editor.putBoolean(isUserLogin, isLogin)
        editor.commit()
    }

    fun clearUser() {
        val editor = prefs.edit()
        editor.remove(isUserLogin)
        editor.commit()
    }

    fun clearAllAPP(){
        val editor = prefs.edit()
        editor.clear()
        editor.commit()
    }
    fun clearAllPers(){
        val editor = prefsPers.edit()
        editor.clear()
        editor.commit()
    }

    /**
     * PERSONEL
     */
    fun setPersonel(obj: AddPersonelReq) {
        val editor = prefsPers.edit()
        val gson = Gson()
        val json = gson.toJson(obj)
        editor.putString(PERSONEL, json)
        editor.commit()
    }

    fun getPersonel(): AddPersonelReq {
        val emptyJson = Gson().toJson(AddPersonelReq())
        return Gson().fromJson(
            prefsPers.getString(PERSONEL, emptyJson),
            object : TypeToken<AddPersonelReq>() {}.type
        )
    }

    fun setPendUmum(obj: ArrayList<AddPendidikanModel>) {
        val editor = prefsPers.edit()
        val gson = Gson()
        val json = gson.toJson(obj)
        editor.putString(UMUM, json)
        editor.commit()
    }

    fun getPendUmum(): ArrayList<AddPendidikanModel> {
        val emptyPendUmum = Gson().toJson(ArrayList<AddPendidikanModel>())
        return Gson().fromJson(
            prefsPers.getString(UMUM, emptyPendUmum),
            object : TypeToken<ArrayList<AddPendidikanModel>>() {}.type
        )
    }


    fun setPendDinas(obj: ArrayList<AddPendidikanModel>) {
        val editor = prefsPers.edit()
        val gson = Gson()
        val json = gson.toJson(obj)
        editor.putString(DINAS, json)
        editor.commit()
    }


    fun getPendDinas(): ArrayList<AddPendidikanModel> {
        val emptyDinas = Gson().toJson(ArrayList<AddPendidikanModel>())
        return Gson().fromJson(
            prefsPers.getString(DINAS, emptyDinas),
            object : TypeToken<ArrayList<AddPendidikanModel>>() {}.type
        )
    }

    fun setPendOther(obj: ArrayList<AddPendidikanModel>) {
        val editor = prefsPers.edit()
        val gson = Gson()
        val json = gson.toJson(obj)
        editor.putString(LAIN, json)
        editor.commit()
    }


    fun getPendOther(): ArrayList<AddPendidikanModel> {
        val emptyDinas = Gson().toJson(ArrayList<AddPendidikanModel>())
        return Gson().fromJson(
            prefsPers.getString(LAIN, emptyDinas),
            object : TypeToken<ArrayList<AddPendidikanModel>>() {}.type
        )
    }

    fun setPekerjaan(obj: ArrayList<AddSinglePekerjaanReq>) {
        val editor = prefsPers.edit()
        val gson = Gson()
        val json = gson.toJson(obj)
        editor.putString(PEKERJAAN, json)
        editor.commit()
    }

    fun getPekerjaan(): ArrayList<AddSinglePekerjaanReq> {
        val emptyPekerjaan = Gson().toJson(ArrayList<AddSinglePekerjaanReq>())
        return Gson().fromJson(
            prefsPers.getString(PEKERJAAN, emptyPekerjaan),
            object : TypeToken<ArrayList<AddSinglePekerjaanReq>>() {}.type
        )
    }

    fun clearPekerjaan() {
        val editor = prefsPers.edit()
        editor.remove(PEKERJAAN)
        editor.commit()
    }


    fun setPekerjaanDiluar(obj: ArrayList<PekerjaanODinasReq>) {
        val editor = prefsPers.edit()
        val gson = Gson()
        val json = gson.toJson(obj)
        editor.putString(DILUAR_DINAS, json)
        editor.commit()
    }

    fun getPekerjaanDiluar(): ArrayList<PekerjaanODinasReq> {
        val emptyPekerjaan = Gson().toJson(ArrayList<PekerjaanODinasReq>())
        return Gson().fromJson(
            prefsPers.getString(DILUAR_DINAS, emptyPekerjaan),
            object : TypeToken<ArrayList<PekerjaanODinasReq>>() {}.type
        )
    }

    fun setAlamat(obj: ArrayList<AlamatReq>) {
        val editor = prefsPers.edit()
        val json = Gson().toJson(obj)
        editor.putString(ALAMAT, json)
        editor.commit()
    }

    fun getAlamat(): ArrayList<AlamatReq> {
        val emptyJson = Gson().toJson(ArrayList<AlamatReq>())
        return Gson().fromJson(
            prefsPers.getString(ALAMAT, emptyJson),
            object : TypeToken<ArrayList<AlamatReq>>() {}.type
        )
    }

    fun setOrganisasi(obj: ArrayList<OrganisasiReq>) {
        val editor = prefsPers.edit()
        val json = Gson().toJson(obj)
        editor.putString(ORGANISASI, json)
        editor.commit()
    }

    fun getOrganisasi(): ArrayList<OrganisasiReq> {
        val emptyJson = Gson().toJson(ArrayList<OrganisasiReq>())
        return Gson().fromJson(
            prefsPers.getString(ORGANISASI, emptyJson),
            object : TypeToken<ArrayList<OrganisasiReq>>() {}.type
        )
    }

    fun setPerjuanganCita(obj: ArrayList<PerjuanganCitaReq>) {
        val editor = prefsPers.edit()
        val json = Gson().toJson(obj)
        editor.putString(PERJUANGAN, json)
        editor.commit()
    }

    fun getPerjuanganCita(): ArrayList<PerjuanganCitaReq> {
        val emptyJson = Gson().toJson(ArrayList<PerjuanganCitaReq>())
        return Gson().fromJson(
            prefsPers.getString(PERJUANGAN, emptyJson),
            object : TypeToken<ArrayList<PerjuanganCitaReq>>() {}.type
        )
    }

    fun setPenghargaan(obj: ArrayList<PenghargaanReq>) {
        val editor = prefsPers.edit()
        val json = Gson().toJson(obj)
        editor.putString(PENGHARGAAN, json)
        editor.commit()
    }

    fun getPenghargaan(): ArrayList<PenghargaanReq> {
        val emptyJson = Gson().toJson(ArrayList<PenghargaanReq>())
        return Gson().fromJson(
            prefsPers.getString(PENGHARGAAN, emptyJson),
            object : TypeToken<ArrayList<PenghargaanReq>>() {}.type
        )
    }

    fun setPasangan(obj: PasanganReq) {
        val editor = prefsPers.edit()
        val json = Gson().toJson(obj)
        editor.putString(PASANGAN, json)
        editor.commit()
    }

    fun getPasangan(): PasanganReq {
        val emptyJson = Gson().toJson(PasanganReq())
        return Gson().fromJson(
            prefsPers.getString(PASANGAN, emptyJson),
            object : TypeToken<PasanganReq>() {}.type
        )
    }

    fun setAyahKandung(obj: AyahReq) {
        val editor = prefsPers.edit()
        val json = Gson().toJson(obj)
        editor.putString(AYAH, json)
        editor.commit()
    }

    fun getAyahKandung(): AyahReq {
        val emptyJson = Gson().toJson(AyahReq())
        return Gson().fromJson(
            prefsPers.getString(AYAH, emptyJson),
            object : TypeToken<AyahReq>() {}.type
        )
    }

    fun setAyahTiri(obj: AyahTiriReq) {
        val editor = prefsPers.edit()
        val json = Gson().toJson(obj)
        editor.putString(AYAH_TIRI, json)
        editor.commit()
    }

    fun getAyahTiri(): AyahTiriReq {
        val emptyJson = Gson().toJson(AyahTiriReq())
        return Gson().fromJson(
            prefsPers.getString(AYAH_TIRI, emptyJson),
            object : TypeToken<AyahTiriReq>() {}.type
        )
    }

    fun setIbu(obj: IbuReq) {
        val editor = prefsPers.edit()
        val json = Gson().toJson(obj)
        editor.putString(IBU, json)
        editor.commit()
    }

    fun getIbu(): IbuReq {
        val emptyJson = Gson().toJson(IbuReq())
        return Gson().fromJson(
            prefsPers.getString(IBU, emptyJson),
            object : TypeToken<IbuReq>() {}.type
        )
    }

    fun setIbuTiri(obj: IbuTiriReq) {
        val editor = prefsPers.edit()
        val json = Gson().toJson(obj)
        editor.putString(IBU_TIRI, json)
        editor.commit()
    }

    fun getIbuTiri(): IbuTiriReq {
        val emptyJson = Gson().toJson(IbuTiriReq())
        return Gson().fromJson(
            prefsPers.getString(IBU_TIRI, emptyJson),
            object : TypeToken<IbuTiriReq>() {}.type
        )
    }

    fun setMertuaLaki(obj: MertuaLakiReq) {
        val editor = prefsPers.edit()
        val json = Gson().toJson(obj)
        editor.putString(MERTUA_LAKI, json)
        editor.commit()
    }

    fun getMertuaLaki(): MertuaLakiReq {
        val emptyJson = Gson().toJson(MertuaLakiReq())
        return Gson().fromJson(
            prefsPers.getString(MERTUA_LAKI, emptyJson),
            object : TypeToken<MertuaLakiReq>() {}.type
        )
    }

    fun setMertuaPerempuan(obj: MertuaPerempuanReq) {
        val editor = prefsPers.edit()
        val json = Gson().toJson(obj)
        editor.putString(MERTUA_PEREMPUAN, json)
        editor.commit()
    }

    fun getMertuaPerempuan(): MertuaPerempuanReq {
        val emptyJson = Gson().toJson(MertuaPerempuanReq())
        return Gson().fromJson(
            prefsPers.getString(MERTUA_PEREMPUAN, emptyJson),
            object : TypeToken<MertuaPerempuanReq>() {}.type
        )
    }

    fun setAnak(obj: ArrayList<AnakReq>) {
        val editor = prefsPers.edit()
        val json = Gson().toJson(obj)
        editor.putString(ANAK, json)
        editor.commit()
    }

    fun getAnak(): ArrayList<AnakReq> {
        val emptyJson = Gson().toJson(ArrayList<AnakReq>())
        return Gson().fromJson(
            prefsPers.getString(ANAK, emptyJson),
            object : TypeToken<ArrayList<AnakReq>>() {}.type
        )
    }

    fun setSaudara(obj: ArrayList<SaudaraReq>) {
        val editor = prefsPers.edit()
        val json = Gson().toJson(obj)
        editor.putString(SAUDARA, json)
        editor.commit()
    }

    fun getSaudara(): ArrayList<SaudaraReq> {
        val emptyJson = Gson().toJson(ArrayList<SaudaraReq>())
        return Gson().fromJson(
            prefsPers.getString(SAUDARA, emptyJson),
            object : TypeToken<ArrayList<SaudaraReq>>() {}.type
        )
    }

    fun setOrangBerjasa(obj: ArrayList<OrangsReq>) {
        val editor = prefsPers.edit()
        val json = Gson().toJson(obj)
        editor.putString(ORANG_BERJASA, json)
        editor.commit()
    }

    fun getOrangBerjasa(): ArrayList<OrangsReq> {
        val emptyJson = Gson().toJson(ArrayList<OrangsReq>())
        return Gson().fromJson(
            prefsPers.getString(ORANG_BERJASA, emptyJson),
            object : TypeToken<ArrayList<OrangsReq>>() {}.type
        )
    }

    fun setOrangDisegani(obj: ArrayList<OrangsReq>) {
        val editor = prefsPers.edit()
        val json = Gson().toJson(obj)
        editor.putString(ORANG_DISEGANI, json)
        editor.commit()
    }

    fun getOrangDisegani(): ArrayList<OrangsReq> {
        val emptyJson = Gson().toJson(ArrayList<OrangsReq>())
        return Gson().fromJson(
            prefsPers.getString(ORANG_DISEGANI, emptyJson),
            object : TypeToken<ArrayList<OrangsReq>>() {}.type
        )
    }

    fun setTokoh(obj: ArrayList<TokohReq>) {
        val editor = prefsPers.edit()
        val json = Gson().toJson(obj)
        editor.putString(TOKOH, json)
        editor.commit()
    }

    fun getTokoh(): ArrayList<TokohReq> {
        val emptyJson = Gson().toJson(ArrayList<TokohReq>())
        return Gson().fromJson(
            prefsPers.getString(TOKOH, emptyJson),
            object : TypeToken<ArrayList<TokohReq>>() {}.type
        )
    }

    fun setSahabat(obj: ArrayList<KawanDekatReq>) {
        val editor = prefsPers.edit()
        val json = Gson().toJson(obj)
        editor.putString(SAHABAT, json)
        editor.commit()
    }

    fun getSahabat(): ArrayList<KawanDekatReq> {
        val emptyJson = Gson().toJson(ArrayList<KawanDekatReq>())
        return Gson().fromJson(
            prefsPers.getString(SAHABAT, emptyJson),
            object : TypeToken<ArrayList<KawanDekatReq>>() {}.type
        )
    }

    fun setMediaInfo(obj: ArrayList<MedInfoReq>) {
        val editor = prefsPers.edit()
        val json = Gson().toJson(obj)
        editor.putString(MEDIA, json)
        editor.commit()
    }

    fun getMediaInfo(): ArrayList<MedInfoReq> {
        val emptyJson = Gson().toJson(ArrayList<MedInfoReq>())
        return Gson().fromJson(
            prefsPers.getString(MEDIA, emptyJson),
            object : TypeToken<ArrayList<MedInfoReq>>() {}.type
        )
    }

    fun setMedsos(obj: ArrayList<MedsosReq>) {
        val editor = prefsPers.edit()
        val json = Gson().toJson(obj)
        editor.putString(MEDSOS, json)
        editor.commit()
    }

    fun getMedsos(): ArrayList<MedsosReq> {
        val emptyJson = Gson().toJson(ArrayList<MedsosReq>())
        return Gson().fromJson(
            prefsPers.getString(MEDSOS, emptyJson),
            object : TypeToken<ArrayList<MedsosReq>>() {}.type
        )
    }

    fun setSignalement(obj: SignalementModel) {
        val editor = prefsPers.edit()
        val json = Gson().toJson(obj)
        editor.putString(SIGNALEMENT, json)
        editor.commit()
    }

    fun getSignalement(): SignalementModel {
        val emptyJson = Gson().toJson(SignalementModel())
        return Gson().fromJson(
            prefsPers.getString(SIGNALEMENT, emptyJson),
            object : TypeToken<SignalementModel>() {}.type
        )
    }

    fun setFoto(obj: FotoReq) {
        val editor = prefsPers.edit()
        val json = Gson().toJson(obj)
        editor.putString(FOTO, json)
        editor.commit()
    }

    fun getFoto(): FotoReq {
        val emptyJson = Gson().toJson(FotoReq())
        return Gson().fromJson(
            prefsPers.getString(FOTO, emptyJson),
            object : TypeToken<FotoReq>() {}.type
        )
    }

    fun setRelasi(obj: ArrayList<RelasiReq>) {
        val editor = prefsPers.edit()
        val json = Gson().toJson(obj)
        editor.putString(RELASI, json)
        editor.commit()
    }

    fun getRelasi(): ArrayList<RelasiReq> {
        val emptyJson = Gson().toJson(ArrayList<RelasiReq>())
        return Gson().fromJson(
            prefsPers.getString(RELASI, emptyJson),
            object : TypeToken<ArrayList<RelasiReq>>() {}.type
        )
    }

    fun setHukuman(obj: ArrayList<HukumanReq>) {
        val editor = prefsPers.edit()
        val json = Gson().toJson(obj)
        editor.putString(HUKUMAN, json)
        editor.commit()
    }

    fun getHukuman(): ArrayList<HukumanReq> {
        val emptyJson = Gson().toJson(ArrayList<HukumanReq>())
        return Gson().fromJson(
            prefsPers.getString(HUKUMAN, emptyJson),
            object : TypeToken<ArrayList<HukumanReq>>() {}.type
        )
    }

    fun setCatpers(obj: CatatanPersReq) {
        val editor = prefsPers.edit()
        val json = Gson().toJson(obj)
        editor.putString(CATPER, json)
        editor.commit()
    }

    fun getCatpers(): CatatanPersReq{
        val emptyJson = Gson().toJson(CatatanPersReq())
        return Gson().fromJson(
            prefsPers.getString(CATPER, emptyJson),
            object : TypeToken<CatatanPersReq>() {}.type
        )
    }
}