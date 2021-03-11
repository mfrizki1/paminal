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
    fun clearGelar() {
        val editor = prefsGelar.edit()
        editor.remove("GELAR_1")
        editor.commit()
    }

}