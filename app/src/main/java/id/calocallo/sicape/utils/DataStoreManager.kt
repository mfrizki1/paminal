package id.calocallo.sicape.utils

import android.content.Context
import android.content.SharedPreferences
import id.calocallo.sicape.R

class DataStoreManager(context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.DATA), Context.MODE_PRIVATE)


}