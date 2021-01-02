package id.calocallo.sicape.ui.main.lp.kke

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.ui.main.choose.ChoosePersonelActivity
import id.calocallo.sicape.ui.main.lp.pasal.PickPasalLpActivity
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_lp_kode_etik.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddLpKodeEtikActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lp_kode_etik)

        sessionManager = SessionManager(this)
        val jenis = intent.extras?.getString(JENIS_KKE)
        setupActionBarWithBackButton(toolbar)
        when (jenis) {
            "pidana" -> supportActionBar?.title = "Tambah Data Laporan Polisi Pidana"
            "kode_etik" -> supportActionBar?.title = "Tambah Data Laporan Polisi Kode Etik"
            "disiplin" -> supportActionBar?.title = "Tambah Data Laporan Polisi Disiplin"
        }
        //button pick personel pelapor
        btn_choose_personel_pelapor_lp_add_kke.setOnClickListener{
            val intent = Intent(this, ChoosePersonelActivity::class.java)
            startActivityForResult(intent,
                REQ_PELAPOR
            )
        }

        //button next to pasal
        btn_next_lp_kke.setOnClickListener {
            sessionManager.setIsiLapLP(edt_isi_laporan_kke.text.toString())
            sessionManager.setAlatBuktiLP(edt_alat_bukti_kke.text.toString())
            val intent = Intent(this, PickPasalLpActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val pelapor = data?.getParcelableExtra<PersonelModel>("ID_PERSONEL")
        if(resultCode == Activity.RESULT_OK && requestCode == REQ_PELAPOR){
            pelapor?.id?.let { sessionManager.setIDPersonelPelapor(it) }
            txt_nama_pelapor_kke_lp_add.text = pelapor?.nama
            txt_pangkat_pelapor_kke_lp_add.text = pelapor?.pangkat
            txt_nrp_pelapor_kke_lp_add.text = pelapor?.nrp
            txt_jabatan_pelapor_kke_lp_add.text = pelapor?.jabatan
            txt_kesatuan_pelapor_kke_lp_add.text = pelapor?.satuan_kerja?.kesatuan
        }
    }

    companion object{
        const val JENIS_KKE = "JENIS_KKE"
        const val REQ_PELAPOR = 202
    }
}