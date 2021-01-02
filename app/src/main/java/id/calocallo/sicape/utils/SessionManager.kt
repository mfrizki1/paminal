package id.calocallo.sicape.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.calocallo.sicape.R
import id.calocallo.sicape.model.*
import id.calocallo.sicape.network.request.*
import id.calocallo.sicape.network.response.LpPasalResp
import id.calocallo.sicape.network.response.LpSaksiResp

class SessionManager(context: Context) {
    private lateinit var parentListPendUmum: ParentListPendUmum
    private lateinit var parentListPendDinas: ParentListPendDinas
    private lateinit var listUmum: ArrayList<AddPendidikanModel>
    private lateinit var listDinas: ArrayList<PendDinasModel>
    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    private var prefsPers: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.DATA), Context.MODE_PRIVATE)
    private var prefsLP: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.LP), Context.MODE_PRIVATE)

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

    fun clearAllAPP() {
        val editor = prefs.edit()
        editor.clear()
        editor.commit()
    }

    fun clearAllPers() {
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

    fun setSahabat(obj: ArrayList<SahabatReq>) {
        val editor = prefsPers.edit()
        val json = Gson().toJson(obj)
        editor.putString(SAHABAT, json)
        editor.commit()
    }

    fun getSahabat(): ArrayList<SahabatReq> {
        val emptyJson = Gson().toJson(ArrayList<SahabatReq>())
        return Gson().fromJson(
            prefsPers.getString(SAHABAT, emptyJson),
            object : TypeToken<ArrayList<SahabatReq>>() {}.type
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

    fun setMedsos(obj: ArrayList<MedSosReq>) {
        val editor = prefsPers.edit()
        val json = Gson().toJson(obj)
        editor.putString(MEDSOS, json)
        editor.commit()
    }

    fun getMedsos(): ArrayList<MedSosReq> {
        val emptyJson = Gson().toJson(ArrayList<MedSosReq>())
        return Gson().fromJson(
            prefsPers.getString(MEDSOS, emptyJson),
            object : TypeToken<ArrayList<MedSosReq>>() {}.type
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

    fun getCatpers(): CatatanPersReq {
        val emptyJson = Gson().toJson(CatatanPersReq())
        return Gson().fromJson(
            prefsPers.getString(CATPER, emptyJson),
            object : TypeToken<CatatanPersReq>() {}.type
        )
    }

//    <!-----------LP-----------/>

    fun setJenisLP(jenis: String) {
        val editor = prefsLP.edit()
        editor.putString("JENIS_LP", jenis)
        editor.apply()
    }

    fun getJenisLP(): String? {
        return prefsLP.getString("JENIS_LP", null)
    }

    fun setNoLP(noLP: String) {
        val editor = prefsLP.edit()
        editor.putString("NO_LP", noLP)
        editor.apply()
    }


    fun getNoLP(): String? {
        return prefsLP.getString("NO_LP", null)
    }

    fun setTglBuatLp(tgl: String){
        val editor = prefsLP.edit()
        editor.putString("TGL_LP", tgl)
        editor.apply()
    }
    fun getTglBuatLp():String?{
        return prefsLP.getString("TGL_LP", null)
    }

    fun setKotaBuatLp(kota: String){
        val editor = prefsLP.edit()
        editor.putString("KOTA_LP", kota)
        editor.apply()
    }

    fun getKotaBuatLp():String?{
        return prefsLP.getString("KOTA_LP", null)
    }

    fun setNamaPimpBidLp(namaPimp: String){
        val editor = prefsLP.edit()
        editor.putString("NAMAPIMP_LP", namaPimp)
        editor.apply()
    }

    fun getNamaPimpBidLp():String?{
        return prefsLP.getString("NAMAPIMP_LP", null)
    }

    fun setPangkatPimpBidLp(pangkatPimp: String){
        val editor = prefsLP.edit()
        editor.putString("PANGKATPIMP_LP", pangkatPimp)
        editor.apply()
    }

    fun getPangkatPimpBidLp():String?{
        return prefsLP.getString("PANGKATPIMP_LP", null)
    }

    fun setJabatanPimpBidLp(jabatanPimp: String){
        val editor = prefsLP.edit()
        editor.putString("JABATANPIMP_LP", jabatanPimp)
        editor.apply()
    }

    fun getJabatanPimpBidLp():String?{
        return prefsLP.getString("JABATANPIMP_LP", null)
    }

    fun setNrpPimpBidLp(nrpPimp: String){
        val editor = prefsLP.edit()
        editor.putString("NRPPIMP_LP", nrpPimp)
        editor.apply()
    }

    fun getNrpPimpBidLp():String?{
        return prefsLP.getString("NRPPIMP_LP", null)
    }

    fun setIDPersonelTerlapor(id_terlapor: Int) {
        val editor = prefsLP.edit()
        editor.putInt("ID_TERLAPOR", id_terlapor)
        editor.apply()
    }

    fun getIDPersonelTerlapor(): Int? {
        return prefsLP.getInt("ID_TERLAPOR", 0)
    }

    fun setIDPersonelPelapor(id_pelapor: Int) {
        val editor = prefsLP.edit()
        editor.putInt("ID_PELAPOR", id_pelapor)
        editor.apply()
    }

    fun getIDPersonelPelapor(): Int? {
        return prefsLP.getInt("ID_PELAPOR", 0)
    }

    fun setIdSipilPelapor(id_sipil_pelapor: Int) {
        val editor = prefsLP.edit()
        editor.putInt("ID_SIPIL_PELAPOR", id_sipil_pelapor)
        editor.apply()
    }

    fun getIdSipilPelapor(): Int? {
        return prefsLP.getInt("ID_SIPIL_PELAPOR", 0)
    }

    fun setIdPelanggaran(id_pelanggaran: Int) {
        val editor = prefsLP.edit()
        editor.putInt("ID_PELANGGARAN", id_pelanggaran)
        editor.apply()
    }

    fun getIdPelanggaran(): Int? {
        return prefsLP.getInt("ID_PELANGGARAN", 0)
    }

    fun setAlatBuktiLP(alat_bukti: String) {
        val editor = prefsLP.edit()
        editor.putString("ALAT_BUKI", alat_bukti)
        editor.apply()
    }

    fun getAlatBukiLP(): String? {
        return prefsLP.getString("ALAT_BUKTI", null)
    }

    fun setKetLP(ket: String) {
        val editor = prefsLP.edit()
        editor.putString("KET", ket)
        editor.apply()
    }

    fun getKetLP(): String? {
        return prefsLP.getString("KET", null)
    }


    fun setListPasalLP(listPasal: ArrayList<LpPasalResp>) {
        val editor = prefsLP.edit()
        val json = Gson().toJson(listPasal)
        editor.putString("LIST_PASAL", json)
        editor.commit()
    }

    fun getListPasalLP(): ArrayList<LpPasalResp> {
        val emptyJson = Gson().toJson(ArrayList<LpPasalResp>())
        return Gson().fromJson(
            prefsLP.getString("LIST_PASAL", emptyJson),
            object : TypeToken<ArrayList<LpPasalResp>>() {}.type
        )
    }

    fun setIdPasal(id_pasal: Int){
        val editor = prefsLP.edit()
        editor.putInt("ID_PASAL", id_pasal)
        editor.apply()
    }

    fun getIdPasal(): Int?{
        return prefsLP.getInt("ID_PASAL", 0)
    }

    fun setListSaksiLP(listSaksi: ArrayList<LpSaksiResp>){
        val editor = prefsLP.edit()
        val json = Gson().toJson(listSaksi)
        editor.putString("LIST_SAKSI", json)
        editor.commit()
    }

    fun getListSaksi():ArrayList<LpSaksiResp>{
        val emptyJson = Gson().toJson(ArrayList<LpSaksiResp>())
        return Gson().fromJson(
            prefsLP.getString("LIST_SAKSI", emptyJson),
            object : TypeToken<ArrayList<LpSaksiResp>>() {}.type
        )
    }


    fun setPelapor(pelapor: String){
        val editor = prefsLP.edit()
        editor.putString("PELAPOR_LP", pelapor)
        editor.apply()
    }
    fun getPelapor():String?{
        return prefsLP.getString("PELAPOR_LP", null)
    }

    fun setIsiLapLP(isi_laporan: String){
        val editor = prefsLP.edit()
        editor.putString("ISI_LAPORAN_LP", isi_laporan)
        editor.apply()
    }
    fun getIsiLapLP():String?{
        return prefsLP.getString("ISI_LAPORAN_LP", null)
    }
    fun setPembukaanLapLP(pembukaan: String){
        val editor = prefsLP.edit()
        editor.putString("PEMBUAKAAN_LAPORAN", pembukaan)
        editor.apply()
    }
    fun getPembukaanLpLP():String?{
        return prefsLP.getString("PEMBUAKAAN_LAPORAN", null)
    }


    fun setMacamPelanggaranLP(macam: String){
        val editor = prefsLP.edit()
        editor.putString("MACAM_LP", macam)
        editor.apply()
    }

    fun getMacamPelanggaranLP():String?{
        return prefsLP.getString("MACAM_LP", null)
    }

    fun setKetPelaporLP(ket_pelapor: String){
        val editor = prefsLP.edit()
        editor.putString("KET_PELAPOR_LP", ket_pelapor)
        editor.apply()
    }

    fun getKetPelaporLP():String?{
        return prefsLP.getString("KET_PELAPOR_LP", null)
    }


    fun setKronologisPelapor(kronologis: String){
        val editor = prefsLP.edit()
        editor.putString("KRONOLOGIS", kronologis)
        editor.apply()
    }
    fun getKronologisPelapor():String?{
        return prefsLP.getString("KRONOLOGIS", null)
    }

    fun setRincianDisiplin(rincian: String){
        val editor = prefsLP.edit()
        editor.putString("RINCIAN", rincian)
        editor.apply()
    }

    fun getRincianDisiplin():String?{
        return prefsLP.getString("RINCIAN", null)
    }


    fun clearLP(){
        val editor = prefsLP.edit()
        editor.clear()
        editor.commit()
    }

}