package id.calocallo.sicape.ui.main.addpersonal.pasangan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.PasanganReq
import id.calocallo.sicape.ui.main.addpersonal.AddAyahKandungActivity
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_pasangan.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddPasanganActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var pasanganReq = PasanganReq()
    private var agama_skrg: String? = null
    private var agama_sblm: String? = null
    var stts_pasangan = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pasangan)
        sessionManager = SessionManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Pasangan"

        val jk = sessionManager.getPersonel().jenis_kelamin
        if (jk == "laki_laki") {
            stts_pasangan = "istri"
            txt_pasangan.text = "Istri"
        } else if (jk == "perempuan") {
            stts_pasangan = "suami"
            txt_pasangan.text = "Suami"
        } else {
            stts_pasangan = "istri"
            txt_pasangan.text = "Istri"
        }

        SP()

        //tod
        btn_next_pasangan.setOnClickListener {
            val nama_lngkp = edt_nama_lngkp_pasangan.text.toString()
            val alias = edt_alias_pasangan.text.toString()
            val tmpt_lhr = edt_tmpt_ttl_pasangan.text.toString()
            val tgl_lhr = edt_tgl_ttl_pasangan.text.toString()
            val suku = edt_suku_pasangan.text.toString()
            val kwg = edt_kwg_pasangan.text.toString()
            val how_to_kwg = edt_how_to_kwg_pasangan.text.toString()
//        val agama_skrg = sp_agama_skrg_pasangan.text.toString()
//        val agama_sblm = sp_agama_sblm_pasangan.text.toString()
            val aliran_dianut = edt_aliran_dianut_pasangan.text.toString()
            val aliran_diikuti = edt_aliran_diikuti_pasangan.text.toString()
            val almt_sblm_kwn = edt_almt_sblm_kwin_pasangan.text.toString()
            val almt_rmh_skrg = edt_almt_skrg_pasangan.text.toString()
            val no_telp_rmh = edt_no_telp_pasangan.text.toString()
            val pend_terakhir = edt_pend_trkhr_pasangan.text.toString()
            val pernikahan_kbrpa = edt_kwin_berapa_pasangan.text.toString()
            val pekerjaan = edt_pekerjaan_pasangan.text.toString()
            val almt_kntr = edt_almt_pekerjaan_pasangan.text.toString()
            val no_telp_kntr = edt_no_telp_pekerjaan_pasangan.text.toString()
            val pekerjaan_sblm = edt_pekerjaan_sblm_pasangan.text.toString()

            //organisasi yang diikuti
            val kedudukan_org_diikuti = edt_kddkn_org_diikuti_pasangan.text.toString()
            val thn_org_diikuti = edt_thn_org_diikuti_pasangan.text.toString()
            val alasan_org_diikuti = edt_alasan_org_diikuti_pasangan.text.toString()
            val almt_org_diikuti = edt_almt_org_diikuti_pasangan.text.toString()

            //organisasi lain yang pernah diikuti
            val kedudukan_org_prnh_diikuti = edt_kddkn_org_prnh_pasangan.text.toString()
            val thn_org_prnh_diikuti = edt_thn_org_prnh_pasangan.text.toString()
            val alasan_org_prnh_diikuti = edt_alasan_org_prnh_pasangan.text.toString()
            val almt_org_prnh_diikuti = edt_almt_org_prnh_pasangan.text.toString()

            pasanganReq.status_pasangan = stts_pasangan
            pasanganReq.nama = nama_lngkp
            pasanganReq.nama_alias = alias
            pasanganReq.tempat_lahir = tmpt_lhr
            pasanganReq.tanggal_lahir = tgl_lhr
            pasanganReq.ras = suku
            pasanganReq.kewarganegaraan = kwg
            pasanganReq.cara_peroleh_kewarganegaraan = how_to_kwg
            pasanganReq.agama_sekarang = agama_skrg
            pasanganReq.agama_sebelumnya = agama_sblm
            pasanganReq.aliran_kepercayaan_dianut = aliran_dianut
            pasanganReq.aliran_kepercayaan_diikuti = aliran_diikuti
            pasanganReq.alamat_terakhir_sebelum_kawin = almt_sblm_kwn
            pasanganReq.alamat_rumah = almt_rmh_skrg
            pasanganReq.no_telp_rumah = no_telp_rmh
            pasanganReq.pendidikan_terakhir = pend_terakhir
            pasanganReq.perkawinan_keberapa = pernikahan_kbrpa
            pasanganReq.pekerjaan = pekerjaan
            pasanganReq.alamat_kantor = almt_kntr
            pasanganReq.no_telp_kantor = no_telp_kntr
            pasanganReq.pekerjaan_sebelumnya = pekerjaan_sblm


            pasanganReq.kedudukan_organisasi_saat_ini = kedudukan_org_diikuti
            pasanganReq.tahun_bergabung_organisasi_saat_ini = thn_org_diikuti
            pasanganReq.alasan_bergabung_organisasi_saat_ini = alasan_org_diikuti
            pasanganReq.alamat_organisasi_saat_ini = almt_org_diikuti

            pasanganReq.kedudukan_organisasi_sebelumnya = kedudukan_org_prnh_diikuti
            pasanganReq.tahun_bergabung_organisasi_sebelumnya = thn_org_prnh_diikuti
            pasanganReq.alasan_bergabung_organisasi_sebelumnya = alasan_org_prnh_diikuti
            pasanganReq.alamat_organisasi_sebelumnya = almt_org_prnh_diikuti


            sessionManager.setPasangan(pasanganReq)
            Log.e("pasangan", "${sessionManager.getPasangan()}")
            //iniAPI(allModel)
            //berhasil -> GO
            //GAGAL -> TOAST
            startActivity(Intent(this, AddAyahKandungActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }
    }

    private fun SP() {
        val agama = listOf("Islam", "Katolik", "Protestan", "Budha", "Hindu", "Konghuchu")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, agama)
        sp_agama_skrg_pasangan.setAdapter(adapter)
        sp_agama_skrg_pasangan.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                agama_skrg = "islam"
            } else if (position == 1) {
                agama_skrg = "katolik"
            } else if (position == 2) {
                agama_skrg = "protestan"
            } else if (position == 3) {
                agama_skrg = "budha"
            } else if (position == 4) {
                agama_skrg = "hindu"
            } else {
                agama_skrg = "konghuchu"
            }
        }

        sp_agama_sblm_pasangan.setAdapter(adapter)
        sp_agama_sblm_pasangan.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                agama_sblm = "islam"
            } else if (position == 1) {
                agama_sblm = "katolik"
            } else if (position == 2) {
                agama_sblm = "protestan"
            } else if (position == 3) {
                agama_sblm = "budha"
            } else if (position == 4) {
                agama_sblm = "hindu"
            } else {
                agama_sblm = "konghuchu"
            }
        }
    }
}