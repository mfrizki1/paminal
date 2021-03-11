package id.calocallo.sicape.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.calocallo.sicape.R
import id.calocallo.sicape.model.Gelar1Model
import id.calocallo.sicape.model.PaparanGelarModel
import id.calocallo.sicape.model.TanggPesertaModel
import id.calocallo.sicape.network.request.LhgReq
import id.calocallo.sicape.network.request.RefPenyelidikanReq

class GelarDataManager(context: Context) {
    private var prefsGelar: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.GELAR), Context.MODE_PRIVATE)

    fun setGelar1(gelar1: LhgReq){
        val editor = prefsGelar.edit()
        val gson = Gson()
        val json = gson.toJson(gelar1)
        editor.putString("GELAR_1", json)
        editor.commit()

    }
    fun getGelar1():LhgReq{
        val emptyUser = Gson().toJson(LhgReq())
        return Gson().fromJson(
            prefsGelar.getString("GELAR_1", emptyUser),
            object : TypeToken<LhgReq>() {}.type
        )
    }
    fun setPaparanGelar(gelar1: PaparanGelarModel){
        val editor = prefsGelar.edit()
        val gson = Gson()
        val json = gson.toJson(gelar1)
        editor.putString("PAPARAN_GELAR", json)
        editor.commit()

    }
    fun getPaparanGelar():PaparanGelarModel{
        val emptyUser = Gson().toJson(PaparanGelarModel())
        return Gson().fromJson(
            prefsGelar.getString("PAPARAN_GELAR", emptyUser),
            object : TypeToken<PaparanGelarModel>() {}.type
        )
    }

    fun setTanggPesertaGelar(gelar1: ArrayList<TanggPesertaModel>){
        val editor = prefsGelar.edit()
        val gson = Gson()
        val json = gson.toJson(gelar1)
        editor.putString("TANGGAPAN_PESERTA", json)
        editor.commit()

    }
    fun getTanggPesertaGelar():ArrayList<TanggPesertaModel>{
        val emptyUser = Gson().toJson(ArrayList<TanggPesertaModel>())
        return Gson().fromJson(
            prefsGelar.getString("TANGGAPAN_PESERTA", emptyUser),
            object : TypeToken<ArrayList<TanggPesertaModel>>() {}.type
        )
    }
}