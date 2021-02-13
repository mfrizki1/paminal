package id.calocallo.sicape.ui.main.lp.kke

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AllPersonelModel
import id.calocallo.sicape.ui.main.lp.pasal.PickPasalActivity
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_lp_kode_etik.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddLpKodeEtikActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var idPelapor: Int?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lp_kode_etik)

        sessionManager1 = SessionManager1(this)
        val jenis = intent.extras?.getString(JENIS_KKE)
        setupActionBarWithBackButton(toolbar)
        when (jenis) {
            "pidana" -> supportActionBar?.title = "Tambah Data Laporan Polisi Pidana"
            "kode_etik" -> supportActionBar?.title = "Tambah Data Laporan Polisi Kode Etik"
            "disiplin" -> supportActionBar?.title = "Tambah Data Laporan Polisi Disiplin"
        }
        //button pick personel pelapor
        btn_choose_personel_pelapor_lp_add_kke.setOnClickListener{
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, REQ_PELAPOR)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        //button next to pasal
        btn_next_lp_kke.setOnClickListener {
            sessionManager1.setIsiLapLP(edt_isi_laporan_kke.text.toString())
            sessionManager1.setAlatBuktiLP(edt_alat_bukti_kke.text.toString())
            sessionManager1.setUraianPelanggaranLP(edt_uraian_pelanggaran_kke.text.toString())
//            val intent = Intent(this, PickPasalLpActivity::class.java)
            val intent = Intent(this, PickPasalActivity::class.java)
            intent.putExtra(PickPasalActivity.ID_PELAPOR, idPelapor)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val pelapor = data?.getParcelableExtra<AllPersonelModel>("ID_PERSONEL")
        if(resultCode == Activity.RESULT_OK && requestCode == REQ_PELAPOR){
            idPelapor = pelapor?.id
            txt_nama_pelapor_kke_lp_add.text ="Nama : ${pelapor?.nama}"
            txt_pangkat_pelapor_kke_lp_add.text ="Pangkat : ${pelapor?.pangkat.toString().toUpperCase()}"
            txt_nrp_pelapor_kke_lp_add.text ="NRP : ${pelapor?.nrp}"
            txt_jabatan_pelapor_kke_lp_add.text ="Jabatan : ${pelapor?.jabatan}"
            txt_kesatuan_pelapor_kke_lp_add.text ="Kesatuan : ${pelapor?.satuan_kerja?.kesatuan}"
        }
    }

    companion object{
        const val JENIS_KKE = "JENIS_KKE"
        const val REQ_PELAPOR = 202
    }
}