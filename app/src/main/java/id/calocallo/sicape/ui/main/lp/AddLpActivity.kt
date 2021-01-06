package id.calocallo.sicape.ui.main.lp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.response.PelanggaranResp
import id.calocallo.sicape.ui.main.choose.ChoosePersonelActivity
import id.calocallo.sicape.ui.main.lp.disiplin.AddLpDisiplinActivity.Companion.JENIS_DISIPLIN
import id.calocallo.sicape.ui.main.lp.kke.AddLpKodeEtikActivity.Companion.JENIS_KKE
import id.calocallo.sicape.ui.main.lp.pidana.AddLpPidanaActivity.Companion.JENIS_PIDANA
import id.calocallo.sicape.ui.main.lp.disiplin.AddLpDisiplinActivity
import id.calocallo.sicape.ui.main.lp.kke.AddLpKodeEtikActivity
import id.calocallo.sicape.ui.main.lp.pidana.AddLpPidanaActivity
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_lp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddLpActivity : BaseActivity() {
    companion object {
        const val REQ_DILAPOR = 111
        const val REQ_TERLAPOR = 222
        const val REQ_PELANGGARAN = 333
        const val NO_LP = "NO_LP"
        const val KET_LP = "KET_LP"
        const val ID_TERLAPOR = "ID_TERLAPOR"
        const val ID_DILAPOR = "ID_DILAPOR"
        const val ID_PELANGGARAN = "ID_PELANGGARAN"
        const val LP = "LP"
    }

    private lateinit var sessionManager: SessionManager
    private var idPersonelTerlapor: Int? = null
    private var idPersonelDilapor: Int? = null
    private var idPelanggaran: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lp)
        val jenis = intent.extras?.getString("JENIS_LP")
        setupActionBarWithBackButton(toolbar)
//        supportActionBar?.title = "Tambah Data Laporan Polisi"
        when (jenis) {
            "pidana" -> supportActionBar?.title = "Tambah Data Laporan Polisi Pidana"
            "kode_etik" -> supportActionBar?.title = "Tambah Data Laporan Polisi Kode Etik"
            "disiplin" -> supportActionBar?.title = "Tambah Data Laporan Polisi Disiplin"
        }
        sessionManager = SessionManager(this)


        btn_choose_personel_terlapor_lp_add.setOnClickListener {
            val intent = Intent(this, ChoosePersonelActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivityForResult(intent, REQ_TERLAPOR)
        }

        /*
        btn_choose_personel_terlapor_lp_add.setOnClickListener {
            val intent = Intent(this, ChoosePersonelActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivityForResult(intent, REQ_TERLAPOR)
        }
                 */

        btn_next_lp_add.setOnClickListener {
            if (idPersonelDilapor == 0) {
                btn_next_lp_add.isClickable = false
            } else if (idPersonelTerlapor == 0) {
                btn_next_lp_add.isClickable = false

            } else {
                /*
//            bundle.putString(NO_LP, edt_no_lp_add.text.toString())
//            bundle.putString(KET_LP, edt_ket_lp_add.text.toString())
//            idPersonelDilapor?.let { it1 -> bundle.putInt(ID_DILAPOR, it1) }
//            idPersonelTerlapor?.let { it1 -> bundle.putInt(ID_TERLAPOR, it1) }
//            idPelanggaran?.let { it1 -> bundle.putInt(ID_PELANGGARAN, it1) }
//            intent.putExtras(bundle)
                 */
                sessionManager.setJenisLP(jenis.toString())
                sessionManager.setNoLP(edt_no_lp_add.text.toString())
                idPersonelTerlapor?.let { it1 -> sessionManager.setIDPersonelTerlapor(it1) }
                idPelanggaran?.let { it1 -> sessionManager.setIdPelanggaran(it1) }
                sessionManager.setKotaBuatLp(edt_kota_buat_add_lp.text.toString())
                sessionManager.setTglBuatLp(edt_tgl_buat_add.text.toString())
                sessionManager.setNamaPimpBidLp(edt_nama_pimpinan_bidang_add.text.toString())
                sessionManager.setPangkatPimpBidLp(edt_pangkat_pimpinan_bidang_add.text.toString())
                sessionManager.setNrpPimpBidLp(edt_nrp_pimpinan_bidang_add.text.toString())
                sessionManager.setJabatanPimpBidLp(edt_jabatan_pimpinan_bidang_add.text.toString())
//                txt_pelanggaran_lp_add.text.toString()

                when (jenis) {
                    "pidana" -> {
                        val intent = Intent(this, AddLpPidanaActivity::class.java)
                        intent.putExtra(JENIS_PIDANA, jenis)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }
                    "kode_etik" -> {
                        val intent = Intent(this, AddLpKodeEtikActivity::class.java)
                        intent.putExtra(JENIS_KKE, jenis)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }
                    "disiplin" -> {
                        val intent = Intent(this, AddLpDisiplinActivity::class.java)
                        intent.putExtra(JENIS_DISIPLIN, jenis)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }
                }
//                val intent = Intent(this, PickPasalLpActivity::class.java)
//                val intent = Intent(this, AddLpPidanaActivity::class.java)
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
//                val bundle = Bundle()
//                startActivity(intent)


//                sessionManager.setKetLP(edt_ket_lp_add.text.toString())
//                sessionManager.setAlatBuktiLP(edt_alat_bukti_lp_add.text.toString())
//                idPersonelDilapor?.let { it1 -> sessionManager.setIDPersonelDilapor(it1) }
//            edt_pasal_lp_add.text.toString()
//            Toast.makeText(this, "Berhasil Masuk", Toast.LENGTH_SHORT).show()
//                Log.e(
//                    "addLP", "$jenis, ${edt_no_lp_add.text.toString()}," +
//                            "${txt_pelanggaran_lp_add.text.toString()}," +
//                            "TErlapor : $idPersonelTerlapor, Dilapor: $idPersonelDilapor, $idPelanggaran"
//                )
            }

        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val personel = data?.getParcelableExtra<PersonelModel>("ID_PERSONEL")
        val pelanggaran = data?.getParcelableExtra<PelanggaranResp>("PELANGGARAN")
        when (resultCode) {
            Activity.RESULT_OK -> {
                when (requestCode) {
                    REQ_DILAPOR -> {
//                        idPersonelDilapor = personel?.id
//                        txt_jabatan_dilapor_lp_add.text = personel?.jabatan
//                        txt_kesatuan_dilapor_lp_add.text = personel?.satuan_kerja?.kesatuan
//                        txt_nama_dilapor_lp_add.text = personel?.nama
//                        txt_nrp_dilapor_lp_add.text = "NRP : ${personel?.nrp}"
//                        txt_pangkat_dilapor_lp_add.text = "Pangkat ${personel?.pangkat}"
                    }

                    REQ_TERLAPOR -> {
                        idPersonelTerlapor = personel?.id
                        txt_jabatan_terlapor_lp_add.text = personel?.jabatan
                        txt_kesatuan_terlapor_lp_add.text = personel?.satuan_kerja?.kesatuan
                        txt_nama_terlapor_lp_add.text = personel?.nama
                        txt_nrp_terlapor_lp_add.text = "NRP : ${personel?.nrp}"
                        txt_pangkat_terlapor_lp_add.text = "Pangkat ${personel?.pangkat}"
                    }
                    REQ_PELANGGARAN -> {
                        idPelanggaran = pelanggaran?.id
//                        txt_pelanggaran_lp_add.text =
//                            "Pelanggaran : ${pelanggaran?.nama_pelanggaran}"
                    }
                }
            }
        }
    }
}