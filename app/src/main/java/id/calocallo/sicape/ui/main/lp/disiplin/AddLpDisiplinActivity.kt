package id.calocallo.sicape.ui.main.lp.disiplin

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AllPersonelModel
import id.calocallo.sicape.network.response.PersonelMinResp
import id.calocallo.sicape.ui.main.lp.pasal.PickPasalActivity
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_lp_disiplin.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddLpDisiplinActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var idPelapor: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lp_disiplin)

        sessionManager1 = SessionManager1(this)
        val jenis = intent.extras?.getString(JENIS_DISIPLIN)
        setupActionBarWithBackButton(toolbar)
        when (jenis) {
            "pidana" -> supportActionBar?.title = "Tambah Data Laporan Polisi Pidana"
            "kode_etik" -> supportActionBar?.title = "Tambah Data Laporan Polisi Kode Etik"
            "disiplin" -> supportActionBar?.title = "Tambah Data Laporan Polisi Disiplin"
        }

        //get Id Pelapor(polisi)
        btn_choose_personel_pelapor_lp_add_disiplin.setOnClickListener {
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, REQ_PELAPOR)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        //next to Pasal
        btn_next_lp_displin.setOnClickListener {
            sessionManager1.setUraianPelanggaranLP(edt_uraian_pelanggaran_disiplin_add.text.toString())
            sessionManager1.setMacamPelanggaranLP(edt_macam_pelanggaran_disiplin.text.toString())
            sessionManager1.setKetPelaporLP(edt_ket_pelapor_disiplin.text.toString())
            sessionManager1.setKronologisPelapor(edt_kronologis_pelapor_disiplin.text.toString())
            sessionManager1.setRincianDisiplin(edt_rincian_pelanggaran_disiplin.text.toString())
            val intent = Intent(this, PickPasalActivity::class.java)
            intent.putExtra(PickPasalActivity.ID_PELAPOR, idPelapor)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }


    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val personel = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
        when (resultCode) {
            Activity.RESULT_OK -> {
                when (requestCode) {
                    REQ_PELAPOR -> {
                        idPelapor = personel?.id
                        txt_nama_pelapor_disiplin_lp_add.text ="Nama : ${personel?.nama}"
                        txt_pangkat_pelapor_disiplin_lp_add.text ="Pangkat : ${personel?.pangkat.toString().toUpperCase()}"
                        txt_nrp_pelapor_disiplin_lp_add.text ="NRP : ${personel?.nrp}"
                        txt_jabatan_pelapor_disiplin_lp_add.text ="Jabatan : ${personel?.jabatan}"
                        txt_kesatuan_pelapor_disiplin_lp_add.text ="Kesatuan : ${personel?.satuan_kerja?.kesatuan}"
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