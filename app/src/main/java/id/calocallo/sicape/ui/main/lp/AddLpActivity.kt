package id.calocallo.sicape.ui.main.lp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.PelanggaranResp
import id.calocallo.sicape.network.response.PersonelMinResp
import id.calocallo.sicape.ui.main.lp.disiplin.AddLpDisiplinActivity.Companion.JENIS_DISIPLIN
import id.calocallo.sicape.ui.main.lp.kke.AddLpKodeEtikActivity.Companion.JENIS_KKE
import id.calocallo.sicape.ui.main.lp.pidana.AddLpPidanaActivity.Companion.JENIS_PIDANA
import id.calocallo.sicape.ui.main.lp.disiplin.AddLpDisiplinActivity
import id.calocallo.sicape.ui.main.lp.kke.AddLpKodeEtikActivity
import id.calocallo.sicape.ui.main.lp.pidana.AddLpPidanaActivity
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_lp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddLpActivity : BaseActivity() {
    companion object {
        const val REQ_DILAPOR = 111
        const val REQ_TERLAPOR = 222
        const val REQ_PELANGGARAN = 333
        const val LP = "LP"
    }

    private lateinit var sessionManager1: SessionManager1
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
            "pidana" -> {
                viewPidana()
                supportActionBar?.title = "Tambah Data Laporan Polisi Pidana"
            }
            "kode_etik" -> {
                txt_layout_no_lp_add.gone()
                supportActionBar?.title = "Tambah Data Laporan Polisi Kode Etik"
            }
            "disiplin" -> supportActionBar?.title = "Tambah Data Laporan Polisi Disiplin"
        }
        sessionManager1 = SessionManager1(this)


        btn_choose_personel_terlapor_lp_add.setOnClickListener {
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, REQ_TERLAPOR)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
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
                sessionManager1.setJenisLP(jenis.toString())
                sessionManager1.setNoLP(edt_no_lp_add.text.toString())
                idPersonelTerlapor?.let { it1 -> sessionManager1.setIDPersonelTerlapor(it1) }
                idPelanggaran?.let { it1 -> sessionManager1.setIdPelanggaran(it1) }
                sessionManager1.setKotaBuatLp(edt_kota_buat_add_lp.text.toString())
                sessionManager1.setTglBuatLp(edt_tgl_buat_add.text.toString())
                sessionManager1.setWaktuBuatLaporan(edt_pukul_laporan_lp.text.toString())
                sessionManager1.setNamaPimpBidLp(edt_nama_pimpinan_bidang_add.text.toString())
                sessionManager1.setPangkatPimpBidLp(edt_pangkat_pimpinan_bidang_add.text.toString())
                sessionManager1.setNrpPimpBidLp(edt_nrp_pimpinan_bidang_add.text.toString())
                sessionManager1.setJabatanPimpBidLp(edt_jabatan_pimpinan_bidang_add.text.toString())
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
            }

        }

        val adapterSatker =
            ArrayAdapter(this, R.layout.item_spinner, resources.getStringArray(R.array.satker))
        spinner_kesatuan_lp_add.setAdapter(adapterSatker)
        spinner_kesatuan_lp_add.setOnItemClickListener { parent, view, position, id ->
            sessionManager1.setKesatuanPimpBidLp(parent.getItemAtPosition(position) as String)
        }
    }

    private fun viewPidana() {
        txt_layout_nama_pimpinan_bidang_add.gone()
        txt_layout_pangkat_pimpinan_bidang_add.gone()
        txt_layout_nrp_pimpinan_bidang_add.gone()
        txt_layout_jabatan_pimpinan_bidang_add.gone()
        txt_layout_spinner_kesatuan_lp_add.gone()
        txt_layout_no_lp_add.gone()
        txt_title_pimpinan_bidang_add.gone()
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val personel = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
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
                        txt_jabatan_terlapor_lp_add.text = "Jabatan : ${personel?.jabatan}"
                        txt_kesatuan_terlapor_lp_add.text =
                            "Kesatuan : ${personel?.satuan_kerja?.kesatuan}"
                        txt_nama_terlapor_lp_add.text = "Nama : ${personel?.nama}"
                        txt_nrp_terlapor_lp_add.text = "NRP : ${personel?.nrp}"
                        txt_pangkat_terlapor_lp_add.text =
                            "Pangkat : ${personel?.pangkat.toString().toUpperCase()}"
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