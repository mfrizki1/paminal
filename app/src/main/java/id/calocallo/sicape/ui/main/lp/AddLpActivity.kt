package id.calocallo.sicape.ui.main.lp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.response.PelanggaranResp
import id.calocallo.sicape.ui.main.choose.ChoosePersonelActivity
import id.calocallo.sicape.ui.main.lp.pasal.PickPasalLpActivity
import id.calocallo.sicape.ui.main.pelanggaran.PickPelanggaranActivity
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
        supportActionBar?.title = "Tambah Data Laporan Polisi"
        when (jenis) {
            "pidana" -> txt_jenis_lp.text = "Laporan Polisi Pidana"
            "kode_etik" -> txt_jenis_lp.text = "Laporan Polisi Kode Etik"
            "disiplin" -> txt_jenis_lp.text = "Laporan Polisi Disiplin"
        }
        sessionManager = SessionManager(this)

        btn_choose_pelanggaran_lp_add.setOnClickListener {
            val intent = Intent(this, PickPelanggaranActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivityForResult(intent, REQ_PELANGGARAN)

        }

        btn_choose_personel_dilapor_lp_add.setOnClickListener {
            val intent = Intent(this, ChoosePersonelActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivityForResult(intent, REQ_DILAPOR)
        }

        btn_choose_personel_terlapor_lp_add.setOnClickListener {
            val intent = Intent(this, ChoosePersonelActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivityForResult(intent, REQ_TERLAPOR)
        }

        btn_next_lp_add.setOnClickListener {
            if (idPersonelDilapor == 0) {
                btn_next_lp_add.isClickable = false
            } else if (idPersonelTerlapor == 0) {
                btn_next_lp_add.isClickable = false

            } else {
                val intent = Intent(this, PickPasalLpActivity::class.java)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                val bundle = Bundle()

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
                sessionManager.setKetLP(edt_ket_lp_add.text.toString())
                sessionManager.setAlatBuktiLP(edt_alat_bukti_lp_add.text.toString())
                idPersonelDilapor?.let { it1 -> sessionManager.setIDPersonelDilapor(it1) }
                idPersonelTerlapor?.let { it1 -> sessionManager.setIDPersonelTerlapor(it1) }
                idPelanggaran?.let { it1 -> sessionManager.setIdPelanggaran(it1) }
                startActivity(intent)
//            edt_pasal_lp_add.text.toString()
//            Toast.makeText(this, "Berhasil Masuk", Toast.LENGTH_SHORT).show()
                txt_pelanggaran_lp_add.text.toString()
                Log.e(
                    "addLP", "$jenis, ${edt_no_lp_add.text.toString()}," +
                            "${txt_pelanggaran_lp_add.text.toString()}, ${edt_ket_lp_add.text.toString()}," +
                            "TErlapor : $idPersonelTerlapor, Dilapor: $idPersonelDilapor, $idPelanggaran"
                )
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
                        idPersonelDilapor = personel?.id
                        txt_jabatan_dilapor_lp_add.text = personel?.jabatan
                        txt_kesatuan_dilapor_lp_add.text = personel?.satuan_kerja?.kesatuan
                        txt_nama_dilapor_lp_add.text = personel?.nama
                        txt_nrp_dilapor_lp_add.text = "NRP : ${personel?.nrp}"
                        txt_pangkat_dilapor_lp_add.text = "Pangkat ${personel?.pangkat}"
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
                        txt_pelanggaran_lp_add.text =
                            "Pelanggaran : ${pelanggaran?.nama_pelanggaran}"
                    }
                }
            }
        }
    }
}