package id.calocallo.sicape.ui.main.lp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.calocallo.sicape.R
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.utils.ext.toast
import kotlinx.android.synthetic.main.activity_kat_add_lp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class KatAddLpActivity : BaseActivity() {
    companion object{
        const val WIHT_LHP = "WIHT_LHP"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kat_add_lp)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Pilih Kategori Tambah Laporan Polisi"
        val jenis = intent.getStringExtra("JENIS_LP")

        btn_lp_add_with_lhp.setOnClickListener {
            val intent = Intent(this, AddLpActivity::class.java)
            intent.putExtra("JENIS_LP", jenis)
            intent.putExtra(WIHT_LHP,true)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_lp_add_not_lhp.setOnClickListener {
            val intent = Intent(this, AddLpActivity::class.java)
            intent.putExtra("JENIS_LP", jenis)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }
}