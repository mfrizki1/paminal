package id.calocallo.sicape.ui.main.lp.disiplin

import android.content.Intent
import android.os.Bundle
import id.calocallo.sicape.R
import id.calocallo.sicape.ui.main.lp.pasal.PickPasalLpActivity
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_lp_disiplin.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddLpDisiplinActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lp_disiplin)

        sessionManager = SessionManager(this)
        val jenis = intent.extras?.getString(JENIS_DISIPLIN)
        setupActionBarWithBackButton(toolbar)
        when (jenis) {
            "pidana" -> supportActionBar?.title = "Tambah Data Laporan Polisi Pidana"
            "kode_etik" -> supportActionBar?.title = "Tambah Data Laporan Polisi Kode Etik"
            "disiplin" -> supportActionBar?.title = "Tambah Data Laporan Polisi Disiplin"
        }

        //get Id Pelapor(polisi)

        //next to Pasal
        btn_choose_personel_pelapor_lp_add_disiplin.setOnClickListener {
            sessionManager.setMacamPelanggaranLP(edt_macam_pelanggaran_disiplin.text.toString())
            sessionManager.setKetPelaporLP(edt_ket_pelapor_disiplin.text.toString())
            sessionManager.setKronologisPelapor(edt_kronologis_pelapor_disiplin.text.toString())
            sessionManager.setRincianDisiplin(edt_rincian_pelanggaran_disiplin.text.toString())
            val intent = Intent(this, PickPasalLpActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent)
        }


    }

    companion object {
        const val JENIS_DISIPLIN = "JENIS_DISIPLIN"
    }
}