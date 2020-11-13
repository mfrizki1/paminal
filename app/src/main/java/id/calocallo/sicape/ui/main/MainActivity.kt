package id.calocallo.sicape.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.model.FiturModel
import id.calocallo.sicape.ui.main.catpers.CatpersActivity
import id.calocallo.sicape.ui.main.personel.PersonelActivity
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar_logo.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class MainActivity : BaseActivity(), FiturAdapter.FiturListener {
    lateinit var list: ArrayList<FiturModel>
    val listFitur = listOf(
        FiturModel("Personel", R.drawable.ic_personel),
        FiturModel("Laporan Polisi", R.drawable.bg_button_oval_primary),
        FiturModel("Laporan Hasil Penyelidikan", R.drawable.bg_button_oval_primary),
        FiturModel("SKHD", R.drawable.bg_button_oval_primary),
        FiturModel("CATPERS", R.drawable.bg_button_oval_primary),
        FiturModel("SKHP", R.drawable.bg_button_oval_primary),
        FiturModel("REHAB", R.drawable.bg_button_oval_primary),
        FiturModel("WANJAK", R.drawable.bg_button_oval_primary),
        FiturModel("Laporan Bulanan", R.drawable.bg_button_oval_primary),
        FiturModel("Analisa", R.drawable.bg_button_oval_primary)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        list = ArrayList<FiturModel>()
        list.add(FiturModel("Personel", R.drawable.ic_catpers))
        list.add(FiturModel("Laporan Polisi", R.drawable.ic_paminal))
        list.add(FiturModel("Laporan Hasil Penyelidikan", R.drawable.ic_paminal))
        list.add(FiturModel("SKHD", R.drawable.ic_paminal))
        list.add(FiturModel("CATPERS", R.drawable.ic_paminal))
        list.add(FiturModel("SKHP", R.drawable.ic_paminal))
        list.add(FiturModel("WANJAK", R.drawable.ic_paminal))
        list.add(FiturModel("Laporan", R.drawable.ic_paminal))
        list.add(FiturModel("Analisa", R.drawable.ic_paminal))
         */

        setSupportActionBar(toolbar_logo)
        supportActionBar?.title = "Dashboard"

        rv_fitur.setHasFixedSize(true)
        rv_fitur.layoutManager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
        val adapter = FiturAdapter(this, ArrayList(listFitur), this)
        rv_fitur.adapter = adapter
    }


    override fun onClick(position: Int) {
        if (listFitur[position].nameFitur == "Personel") {
            startActivity(Intent(this, PersonelActivity::class.java))
//            startActivity(Intent(this, CatpersActivity::class.java))
        } else {
            Toast.makeText(this, listFitur[position].nameFitur, Toast.LENGTH_SHORT).show()
        }
    }
}