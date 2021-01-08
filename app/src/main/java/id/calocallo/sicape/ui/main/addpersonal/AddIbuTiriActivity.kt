package id.calocallo.sicape.ui.main.addpersonal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.IbuTiriReq
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_ibu_tiri.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddIbuTiriActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var ibuTiriReq = IbuTiriReq()
    var stts_kerja = 0
    var stts_hidup = 0
    var agama_skrg = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ibu_tiri)
        sessionManager = SessionManager(this)

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Ibu Tiri / Angkat"

        initSpinner(spinner_pekerjaan_ibu_tiri, spinner_stts_hidup_ibu_tiri, sp_agama_ibu_tiri)

        val nama_lengkap = edt_nama_lngkp_ibu_tiri.text.toString()
        val alias = edt_alias_ibu_tiri.text.toString()
        val tempat_lhr = edt_tmpt_ttl_ibu_tiri.text.toString()
        val tgl_lhr = edt_tgl_ttl_ibu_tiri.text.toString()
        val agama = sp_agama_ibu_tiri.text.toString()
        val aliran_dianut = edt_aliran_dianut_ibu_tiri.text.toString()
        val suku = edt_suku_ibu_tiri.text.toString()
        val kwg = edt_kwg_ibu_tiri.text.toString()
        val how_to_kwg = edt_how_to_kwg_ibu_tiri.text.toString()
        val almt_skrg = edt_almt_skrg_ibu_tiri.text.toString()
        val no_telp_rmh = edt_no_telp_ibu_tiri.text.toString()
        val almt_sblm = edt_almt_rmh_sblm_ibu_tiri.text.toString()
        val pkrjaan = edt_pekerjaan_ibu_tiri.text.toString()
        val almt_kantor = edt_almt_kntr_ibu_tiri.text.toString()
        val no_tlp_kantor = edt_no_telp_kntr_ibu_tiri.text.toString()
        val pekerjaan_sblm = edt_pekerjaan_sblm_ibu_tiri.text.toString()
        val pend_terakhir = edt_pend_trkhr_ibu_tiri.text.toString()

        //organisasi yang diikuti
        val kddkn_org_diikuti = edt_kddkn_org_diikuti_ibu_tiri.text.toString()
        val thn_org_diikut = edt_thn_org_diikuti_ibu_tiri.text.toString()
        val alasan_org_diikuti = edt_alasan_org_diikuti_ibu_tiri.text.toString()
        val almt_org_diikuti = edt_almt_org_diikuti_ibu_tiri.text.toString()

        //organisasi yang pernah diikuti
        val kddkn_org_prnh = edt_kddkn_org_prnh_ibu_tiri.text.toString()
        val thn_org_prnh = edt_thn_org_prnh_ibu_tiri.text.toString()
        val alasan_org_prnh = edt_alasan_org_prnh_ibu_tiri.text.toString()
        val almt_org_prnh = edt_almt_org_prnh_ibu_tiri.text.toString()

        //status hidup
        val bagaimana_hidup = edt_tahun_kematian_ibu_tiri.text.toString()
        val dimana_hidup = edt_dimana_ibu_tiri.text.toString()
        val penyebab_hidup = edt_penyebab_ibu_tiri.text.toString()

        btn_next_ibu_tiri.setOnClickListener {
            ibuTiriReq.nama = edt_nama_lngkp_ibu_tiri.text.toString()
            ibuTiriReq.nama_alias = edt_alias_ibu_tiri.text.toString()
            ibuTiriReq.tempat_lahir = edt_tmpt_ttl_ibu_tiri.text.toString()
            ibuTiriReq.tanggal_lahir = edt_tgl_ttl_ibu_tiri.text.toString()
            ibuTiriReq.agama = agama_skrg
            ibuTiriReq.aliran_kepercayaan_dianut = edt_aliran_dianut_ibu_tiri.text.toString()
            ibuTiriReq.ras = edt_suku_ibu_tiri.text.toString()
            ibuTiriReq.kewarganegaran = edt_kwg_ibu_tiri.text.toString()
            ibuTiriReq.cara_peroleh_kewarganegaraan = edt_how_to_kwg_ibu_tiri.text.toString()
            ibuTiriReq.alamat_rumah = edt_almt_skrg_ibu_tiri.text.toString()
            ibuTiriReq.no_telp_rumah = edt_no_telp_ibu_tiri.text.toString()
            ibuTiriReq.alamat_rumah_sebelumnya = edt_almt_rmh_sblm_ibu_tiri.text.toString()

            ibuTiriReq.status_kerja = stts_kerja
            ibuTiriReq.pekerjaan_terakhir = edt_pekerjaan_ibu_tiri.text.toString()
            ibuTiriReq.alasan_pensiun = edt_alsn_berhenti_ibu_tiri.text.toString()
            ibuTiriReq.tahun_pensiun = edt_thn_berhenti_ibu_tiri.text.toString()

            ibuTiriReq.nama_kantor = edt_nama_almt_kntr_ibu_tiri.text.toString()
            ibuTiriReq.alamat_kantor = edt_almt_kntr_ibu_tiri.text.toString()
            ibuTiriReq.no_telp_kantor = edt_no_telp_kntr_ibu_tiri.text.toString()
            ibuTiriReq.pekerjaan_sebelumnya = edt_pekerjaan_sblm_ibu_tiri.text.toString()
            ibuTiriReq.pendidikan_terakhir = edt_pend_trkhr_ibu_tiri.text.toString()

            ibuTiriReq.status_kehidupan = stts_hidup
            ibuTiriReq.tahun_kematian = edt_tahun_kematian_ibu_tiri.text.toString()
            ibuTiriReq.lokasi_kematian = edt_dimana_ibu_tiri.text.toString()
            ibuTiriReq.sebab_kematian = edt_penyebab_ibu_tiri.text.toString()

            sessionManager.setIbuTiri(ibuTiriReq)
            Log.e("ibutTiri", "${sessionManager.getIbuTiri()}")
            startActivity(Intent(this, AddMertuaLakiActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun initSpinner(
        spinnerPekerjaanIbuTiri: AutoCompleteTextView,
        spinnerSttsHidupIbuTiri: AutoCompleteTextView,
        spAgamaIbuTiri: AutoCompleteTextView
    ) {
        val item = listOf("Masih", "Tidak")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        spinnerPekerjaanIbuTiri.setAdapter(adapter)
        spinnerPekerjaanIbuTiri.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                txt_layout_nama_kantor_ibu_tiri.visibility = View.VISIBLE
                txt_layout_alamat_kantor_ibu_tiri.visibility = View.VISIBLE
                txt_layout_no_telp_kantor_ibu_tiri.visibility = View.VISIBLE
                txt_layout_thn_berhenti_ibu_tiri.visibility = View.GONE
                txt_layout_alsn_berhenti_ibu_tiri.visibility = View.GONE
                stts_kerja = 1

            } else {
                txt_layout_nama_kantor_ibu_tiri.visibility = View.GONE
                txt_layout_alamat_kantor_ibu_tiri.visibility = View.GONE
                txt_layout_no_telp_kantor_ibu_tiri.visibility = View.GONE
                txt_layout_thn_berhenti_ibu_tiri.visibility = View.VISIBLE
                txt_layout_alsn_berhenti_ibu_tiri.visibility = View.VISIBLE
                stts_kerja = 0

            }
        }
        val itemHidup = listOf("Masih", "Tidak")
        val adapterHidup = ArrayAdapter(this, R.layout.item_spinner, itemHidup)
        spinnerSttsHidupIbuTiri.setAdapter(adapterHidup)
        spinnerSttsHidupIbuTiri.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                txt_layout_penyebab_ibu_tiri.visibility = View.GONE
                txt_layout_dimana_ibu_tiri.visibility = View.GONE
                txt_layout_bagaimana_stts_ibu_tiri.visibility = View.GONE
                stts_hidup = 1
            } else {
                txt_layout_penyebab_ibu_tiri.visibility = View.VISIBLE
                txt_layout_dimana_ibu_tiri.visibility = View.VISIBLE
                txt_layout_bagaimana_stts_ibu_tiri.visibility = View.VISIBLE
                stts_hidup = 0

            }
        }
        val agama = listOf("Islam", "Katolik", "Protestan", "Budha", "Hindu", "Konghuchu")
        val adapterAgama = ArrayAdapter(this, R.layout.item_spinner, agama)
        spAgamaIbuTiri.setAdapter(adapterAgama)
        spAgamaIbuTiri.setOnItemClickListener { parent, view, position, id ->
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
    }
}