package id.calocallo.sicape.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel

class SessionManager(context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
        const val HAK_AKSES = "hak_akses"
        const val USER = "user"
        const val isUserLogin = "userLogin"
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

    fun deleteUser() {
        val editor = prefs.edit()
        editor.remove(USER)
        editor.commit()
    }

    fun getUserLogin():Boolean{
        return prefs.getBoolean(isUserLogin, false)
    }

    fun setUserLogin(isLogin:Boolean){
        val editor = prefs.edit()
        editor.putBoolean(isUserLogin, isLogin)
        editor.commit()
    }
    fun clearUser(){
        val editor = prefs.edit()
        editor.remove(isUserLogin)
        editor.commit()
    }
}