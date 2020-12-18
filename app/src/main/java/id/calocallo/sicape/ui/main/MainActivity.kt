package id.calocallo.sicape.ui.main

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.FiturModel
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.ui.main.profil.ProfilFragment
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar_logo.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var sessionmanager: SessionManager
    lateinit var list: ArrayList<FiturModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sessionmanager = SessionManager(this)

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

        getPersonel()
        initBottom()
        if (savedInstanceState == null) {
            bottom_navigation.selectedItemId = R.id.nav_home
        }

    }

    private fun initBottom() {
        bottom_navigation.setOnNavigationItemSelectedListener(this)
    }

    private fun getPersonel() {
        NetworkConfig().getService()
            .getUser(tokenBearer = "Bearer ${sessionmanager.fetchAuthToken()}")
            .enqueue(object : Callback<PersonelModel> {
                override fun onFailure(call: Call<PersonelModel>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Gagal Koneksi", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(
                    call: Call<PersonelModel>,
                    response: Response<PersonelModel>
                ) {
                    if (response.isSuccessful) {
                        val user = response.body()
//                        saveUser(user)
                        sessionmanager.saveUser(user)

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