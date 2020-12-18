package id.calocallo.sicape.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel

class DataStoreManager(context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.DATA), Context.MODE_PRIVATE)


}