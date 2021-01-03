package id.calocallo.sicape.ui.main.lp.disiplin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.ui.main.choose.ChoosePersonelActivity
import id.calocallo.sicape.ui.main.lp.pasal.PickPasalActivity
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_lp_disiplin.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddLpDisiplinActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var idPelapor: Int? = null
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
        btn_choose_personel_pelapor_lp_add_disiplin.setOnClickListener {
            val intent = Intent(this, ChoosePersonelActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivityForResult(intent, REQ_PELAPOR)
        }

        //next to Pasal
        btn_next_lp_displin.setOnClickListener {
            sessionManager.setMacamPelanggaranLP(edt_macam_pelanggaran_disiplin.text.toString())
            sessionManager.setKetPelaporLP(edt_ket_pelapor_disiplin.text.toString())
            sessionManager.setKronologisPelapor(edt_kronologis_pelapor_disiplin.text.toString())
            sessionManager.setRincianDisiplin(edt_rincian_pelanggaran_disiplin.text.toString())
            val intent = Intent(this, PickPasalActivity::class.java)
            intent.putExtra(PickPasalActivity.ID_PELAPOR, idPelapor)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val personel = data?.getParcelableExtra<PersonelModel>("ID_PERSONEL")
        when (resultCode) {
            Activity.RESULT_OK -> {
                when (requestCode) {
                    REQ_PELAPOR -> {
                        idPelapor = personel?.id
                        txt_nama_pelapor_disiplin_lp_add.text = personel?.nama
                        txt_pangkat_pelapor_disiplin_lp_add.text = personel?.pangkat
                        txt_nrp_pelapor_disiplin_lp_add.text = personel?.nrp
                        txt_jabatan_pelapor_disiplin_lp_add.text = personel?.jabatan
                        txt_kesatuan_pelapor_disiplin_lp_add.text = personel?.satuan_kerja?.kesatuan
                    }

                }
            }
        }
    }

    companion object {
        const val JENIS_DISIPLIN = "JENIS_DISIPLIN"
        const val REQ_PELAPOR = 202
    }
}