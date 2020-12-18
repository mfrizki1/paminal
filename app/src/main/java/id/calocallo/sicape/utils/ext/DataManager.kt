package id.calocallo.sicape.utils.ext

import android.content.Context
import id.calocallo.sicape.R
import android.content.SharedPreferences

class DataManager(context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.DATA), Context.MODE_PRIVATE)

    companion object{
        const val PERSONEL = "personel"
        const val UMUM = "umum"
        const val DINAS = "dinas"
        const val LAIN = "lain"
        const val PEKERJAAN = "pekerjaan"
        const val LUAR_DINAS = "LUAR_DINAS"
        const val ALAMAT = "ALAMAT"



    }
}