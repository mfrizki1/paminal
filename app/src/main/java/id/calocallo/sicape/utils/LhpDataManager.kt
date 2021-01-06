package id.calocallo.sicape.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.KetTerlaporReq
import id.calocallo.sicape.network.request.PersonelPenyelidikReq
import id.calocallo.sicape.network.request.RefPenyelidikanReq
import id.calocallo.sicape.network.request.SaksiLhpReq

class LhpDataManager(context: Context) {
    private var prefsLHP : SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.LHP), Context.MODE_PRIVATE)

    fun setNoLHP(no_lhp: String){
        val editor = prefsLHP.edit()
        editor.putString("NO_LHP", no_lhp)
        editor.apply()
    }

    fun getNoLHP():String?{
        return prefsLHP.getString("NO_LHP",null)
    }

    fun setTentangLHP(tentang: String){
        val editor = prefsLHP.edit()
        editor.putString("TENTANG", tentang)
        editor.apply()
    }

    fun getTentangLHP():String?{
        return prefsLHP.getString("TENTANG",null)
    }

    fun setSPLHP(no_sp: String){
        val editor = prefsLHP.edit()
        editor.putString("NO_SP", no_sp)
        editor.apply()
    }

    fun getSPLHP():String?{
        return prefsLHP.getString("NO_SP",null)
    }

    fun setWaktuLHP(waktu_penugasan: String){
        val editor = prefsLHP.edit()
        editor.putString("WAKTU", waktu_penugasan)
        editor.apply()
    }

    fun getWaktuLHP():String?{
        return prefsLHP.getString("WAKTU",null)
    }

    fun setDaerahLHP(daerah_penyelidikan: String){
        val editor = prefsLHP.edit()
        editor.putString("DAERAH", daerah_penyelidikan)
        editor.apply()
    }

    fun getDaerahLHP():String?{
        return prefsLHP.getString("DAERAH",null)
    }

    fun setPokokPermasalahanLHP(pokok_permasalahan: String){
        val editor = prefsLHP.edit()
        editor.putString("POKOK_PERMASALAHAN", pokok_permasalahan)
        editor.apply()
    }

    fun getPokokPermasalahanLHP():String?{
        return prefsLHP.getString("POKOK_PERMASALAHAN",null)
    }

    fun setKetAhliLHP(ket_ahli: String){
        val editor = prefsLHP.edit()
        editor.putString("KET_AHLI", ket_ahli)
        editor.apply()
    }

    fun getKetAhliLHP():String?{
        return prefsLHP.getString("KET_AHLI",null)
    }

    fun setKesimpulanLHP(kesimpulan: String){
        val editor = prefsLHP.edit()
        editor.putString("Kesimpulan", kesimpulan)
        editor.apply()
    }

    fun getKesimpulanLHP():String?{
        return prefsLHP.getString("Kesimpulan",null)
    }

    fun setRekomendasiLHP(Rekomendasi: String){
        val editor = prefsLHP.edit()
        editor.putString("Rekomendasi", Rekomendasi)
        editor.apply()
    }

    fun getRekomendasiLHP():String?{
        return prefsLHP.getString("Rekomendasi",null)
    }

    fun setTugasPokokLHP(tugas_pokok: String){
        val editor = prefsLHP.edit()
        editor.putString("TUGAS_POKOK", tugas_pokok)
        editor.apply()
    }

    fun getTugasPokokLHP():String?{
        return prefsLHP.getString("TUGAS_POKOK",null)
    }

    fun setIsTerbukti(isTerbukti: Int){
        val editor = prefsLHP.edit()
        editor.putInt("ISTERBUKTI", isTerbukti)
        editor.apply()
    }
    fun getIsTerbukti():Int?{
        return prefsLHP.getInt("ISTERBUKTI",0)
    }
    fun setListRefLp(listRefLp: ArrayList<RefPenyelidikanReq>){
        val editor = prefsLHP.edit()
        val gson = Gson()
        val json = gson.toJson(listRefLp)
        editor.putString("REF_LP", json)
        editor.commit()

    }
    fun getListRefLp():ArrayList<RefPenyelidikanReq>{
        val emptyUser = Gson().toJson(ArrayList<RefPenyelidikanReq>())
        return Gson().fromJson(
            prefsLHP.getString("REF_LP", emptyUser),
            object : TypeToken<ArrayList<RefPenyelidikanReq>>() {}.type
        )
    }

    fun setListLidikLHP(listLidik: ArrayList<PersonelPenyelidikReq>){
        val editor = prefsLHP.edit()
        val gson = Gson()
        val json = gson.toJson(listLidik)
        editor.putString("LIDIK", json)
        editor.commit()
    }

    fun getListLidikLHP():ArrayList<PersonelPenyelidikReq>{
        val emptyUser = Gson().toJson(ArrayList<PersonelPenyelidikReq>())
        return Gson().fromJson(
            prefsLHP.getString("LIDIK", emptyUser),
            object : TypeToken<ArrayList<PersonelPenyelidikReq>>() {}.type
        )
    }

    fun setListSaksiLHP(listSaksi: ArrayList<SaksiLhpReq>){
        val editor = prefsLHP.edit()
        val gson = Gson()
        val json = gson.toJson(listSaksi)
        editor.putString("SAKSI", json)
        editor.commit()
    }

    fun getListSaksiLHP():ArrayList<SaksiLhpReq>{
        val emptyUser = Gson().toJson(ArrayList<SaksiLhpReq>())
        return Gson().fromJson(
            prefsLHP.getString("SAKSI", emptyUser),
            object : TypeToken<ArrayList<SaksiLhpReq>>() {}.type
        )
    }

    fun setListTerlaporLHP(listTerlapor: ArrayList<KetTerlaporReq>){
        val editor = prefsLHP.edit()
        val gson = Gson()
        val json = gson.toJson(listTerlapor)
        editor.putString("TERLAPOR", json)
        editor.commit()
    }

    fun getListTerlaporLHP():ArrayList<KetTerlaporReq>{
        val emptyUser = Gson().toJson(ArrayList<KetTerlaporReq>())
        return Gson().fromJson(
            prefsLHP.getString("TERLAPOR", emptyUser),
            object : TypeToken<ArrayList<KetTerlaporReq>>() {}.type
        )
    }

    fun clearLHP(){
        val editor = prefsLHP.edit()
        editor.clear()
        editor.commit()
    }
}