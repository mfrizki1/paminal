package id.calocallo.sicape.ui.main

import android.Manifest
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import id.calocallo.sicape.R
import id.calocallo.sicape.model.FiturModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.UserResp
import id.calocallo.sicape.ui.main.profil.ProfilFragment
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar_logo.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : BaseActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener, MultiplePermissionsListener {
    private lateinit var sessionmanager: SessionManager1
    lateinit var list: ArrayList<FiturModel>
    private var isSipilLogin: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Logger.addLogAdapter(AndroidLogAdapter())

        sessionmanager = SessionManager1(this)

        setSupportActionBar(toolbar_logo)
        supportActionBar?.title = "Dashboard"
        isSipilLogin = intent.extras?.getBoolean("IS_SIPIL")

        getUser()
        initBottom()
        if (savedInstanceState == null) {
            bottom_navigation.selectedItemId = R.id.nav_home
        }

        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ).withListener(this)
            .check()
    }

    private fun initBottom() {
        bottom_navigation.setOnNavigationItemSelectedListener(this)
    }

    private fun getUser(){
        NetworkConfig().getServUser().getUser("Bearer ${sessionmanager.fetchAuthToken()}").enqueue(
            object : Callback<UserResp> {
                override fun onResponse(call: Call<UserResp>, response: Response<UserResp>) {
                    if (response.isSuccessful) {
                        sessionmanager.saveUser(response.body())
                    } else {
                        Toast.makeText(this@MainActivity, R.string.error_conn, Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<UserResp>, t: Throwable) {
                    Toast.makeText(this@MainActivity, t.toString(), Toast.LENGTH_SHORT).show()

                }
            })
    }

   /* private fun getSipil() {
        NetworkConfig().getServPers()
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

    private fun getPersonel() {
        NetworkConfig().getServPers()
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

    private fun getSuperAdmin() {
        NetworkConfig().getServPers()
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
    }*/


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

    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
        if(report?.areAllPermissionsGranted() == true){
            Toast.makeText(this, "Izin Diberikan", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPermissionRationaleShouldBeShown(
        permissions: MutableList<PermissionRequest>?,
        token: PermissionToken?
    ) {
        token?.continuePermissionRequest()
    }
}