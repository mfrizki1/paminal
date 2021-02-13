package id.calocallo.sicape.ui.main

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import id.calocallo.sicape.R
import id.calocallo.sicape.model.FiturModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.HakAksesPersonel1
import id.calocallo.sicape.network.response.HakAksesSipil
import id.calocallo.sicape.network.response.SipilProfilResp
import id.calocallo.sicape.ui.main.profil.ProfilFragment
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar_logo.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var sessionmanager: SessionManager1
    lateinit var list: ArrayList<FiturModel>
    private var isSipilLogin: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Logger.addLogAdapter(AndroidLogAdapter())

        sessionmanager = SessionManager1(this)

        /*
        list = ArrayList<FiturModel>()
        list.add(FiturModel("Personel", R.drawable.ic_catpers))
        list.add(FiturModel("Laporan Polisi", R.drawable.ic_paminal))
        list.add(FiturModel("Laporan Hasil Penyelidikan", R.drawable.))
        list.add(FiturModel("SKHD", R.drawable.ic_paminal))
        list.add(FiturModel("CATPERS", R.drawable.ic_paminal))
        list.add(FiturModel("SKHP", R.drawable.ic_paminal))
        list.add(FiturModel("WANJAK", R.drawable.ic_paminal))
        list.add(FiturModel("Laporan", R.drawable.ic_paminal))
        list.add(FiturModel("Analisa", R.drawable.ic_paminal))
         */

        setSupportActionBar(toolbar_logo)
        supportActionBar?.title = "Dashboard"
        isSipilLogin = intent.extras?.getBoolean("IS_SIPIL")
        if (isSipilLogin == true) {
            getSipil()
        } else if(isSipilLogin == false) {
            getPersonel()
        }else{
            getSuperAdmin()
        }
        initBottom()
        if (savedInstanceState == null) {
            bottom_navigation.selectedItemId = R.id.nav_home
        }

    }

    private fun getSuperAdmin() {
        NetworkConfig().getService()
            .getUserSuper(tokenBearer = "Bearer ${sessionmanager.fetchAuthToken()}")
            .enqueue(object : Callback<HakAksesPersonel1> {
                override fun onFailure(call: Call<HakAksesPersonel1>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Gagal Koneksi", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(
                    call: Call<HakAksesPersonel1>,
                    response: Response<HakAksesPersonel1>
                ) {
                    if (response.isSuccessful) {
                        val user = response.body()
                        Logger.d("$user")
//                        saveUser(user)
                        sessionmanager.saveUserPersonel(user)
                        sessionmanager.clearUserSipil()
                        Log.e("personel", "${sessionmanager.fetchUserPersonel()}")
                        Log.e("sipil", "${sessionmanager.fetchUserSipil()}")
                    } else {
                        Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
    }

    private fun getSipil() {
        NetworkConfig().getService()
            .getUserSipil(tokenBearer = "Bearer ${sessionmanager.fetchAuthToken()}")
            .enqueue(object : Callback<HakAksesSipil> {
                override fun onFailure(call: Call<HakAksesSipil>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Gagal Koneksi", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(
                    call: Call<HakAksesSipil>,
                    response: Response<HakAksesSipil>
                ) {
                    if (response.isSuccessful) {
                        val user = response.body()
//                        saveUser(user)
                        sessionmanager.saveUserSipil(user)
                        sessionmanager.clearUserPersonel()
                        Log.e("sipil", "${sessionmanager.fetchUserSipil()}")
                        Log.e("personel", "${sessionmanager.fetchUserPersonel()}")
                    } else {
                        Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
    }

    private fun initBottom() {
        bottom_navigation.setOnNavigationItemSelectedListener(this)
    }

    private fun getPersonel() {
        NetworkConfig().getService()
            .getUserPersonel(tokenBearer = "Bearer ${sessionmanager.fetchAuthToken()}")
            .enqueue(object : Callback<HakAksesPersonel1> {
                override fun onFailure(call: Call<HakAksesPersonel1>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Gagal Koneksi", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(
                    call: Call<HakAksesPersonel1>,
                    response: Response<HakAksesPersonel1>
                ) {
                    if (response.isSuccessful) {
                        val user = response.body()
//                        saveUser(user)
                        sessionmanager.saveUserPersonel(user)
                        sessionmanager.clearUserSipil()
                        Log.e("personel", "${sessionmanager.fetchUserPersonel()}")
                        Log.e("sipil", "${sessionmanager.fetchUserSipil()}")
                    } else {
                        Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null

        when (item.itemId) {
            R.id.nav_home -> {
                supportActionBar?.title = "Dashboard"
                fragment = DashboardFragment()
            }
            R.id.nav_profile -> {
                supportActionBar?.title = "Profile"
                fragment = ProfilFragment()
            }
        }
        return loadFragment(fragment)
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fl_home, fragment)
                .commit()
            return true
        }
        return false
    }
}