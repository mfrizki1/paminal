package id.calocallo.sicape.ui.main.addpersonal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.MertuaLakiReq
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_mertua_laki.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddMertuaLakiActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var mertuaLakiReq =
        MertuaLakiReq()
    var stts_hidup = 0
    var stts_kerja = 0
    var agama_skrg = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_mertua_laki)
        sessionManager = SessionManager(this)

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Mertua Laki"
        initSpinner(
            spinner_pekerjaan_mertua_laki,
            spinner_stts_hidup_mertua_laki,
            sp_agama_mertua_laki
        )


        val nama_lengkap = edt_nama_lngkp_mertua_laki.text.toString()
        val alias = edt_alias_mertua_laki.text.toString()
        val tempat_lhr = edt_tmpt_ttl_mertua_laki.text.toString()
        val tgl_lhr = edt_tgl_ttl_mertua_laki.text.toString()
        val agama = sp_agama_mertua_laki.text.toString()
        val aliran_dianut = edt_aliran_dianut_mertua_laki.text.toString()
        val suku = edt_suku_mertua_laki.text.toString()
        val kwg = edt_kwg_mertua_laki.text.toString()
        val how_to_kwg = edt_how_to_kwg_mertua_laki.text.toString()
        val almt_skrg = edt_almt_skrg_mertua_laki.text.toString()
        val no_telp_rmh = edt_no_telp_mertua_laki.text.toString()
        val almt_sblm = edt_almt_rmh_sblm_mertua_laki.text.toString()
        val pkrjaan = edt_pekerjaan_mertua_laki.text.toString()
        val almt_kantor = edt_almt_kntr_mertua_laki.text.toString()
        val no_tlp_kantor = edt_no_telp_kntr_mertua_laki.text.toString()
        val pekerjaan_sblm = edt_pekerjaan_sblm_mertua_laki.text.toString()
        val pend_terakhir = edt_pend_trkhr_mertua_laki.text.toString()

        //organisasi yang diikuti
        val kddkn_org_diikuti = edt_kddkn_org_diikuti_mertua_laki.text.toString()
        val thn_org_diikut = edt_thn_org_diikuti_mertua_laki.text.toString()
        val alasan_org_diikuti = edt_alasan_org_diikuti_mertua_laki.text.toString()
        val almt_org_diikuti = edt_almt_org_diikuti_mertua_laki.text.toString()

        //organisasi yang pernah diikuti
        val kddkn_org_prnh = edt_kddkn_org_prnh_mertua_laki.text.toString()
        val thn_org_prnh = edt_thn_org_prnh_mertua_laki.text.toString()
        val alasan_org_prnh = edt_alasan_org_prnh_mertua_laki.text.toString()
        val almt_org_prnh = edt_almt_org_prnh_mertua_laki.text.toString()

        //status hidup
//        val bagaimana_hidup = edt_bagaimana_stts_mertua_laki.text.toString()
        val dimana_hidup = edt_dimana_mertua_laki.text.toString()
        val penyebab_hidup = edt_penyebab_mertua_laki.text.toString()

        btn_next_mertua_laki.setOnClickListener {
            mertuaLakiReq.nama = edt_nama_lngkp_mertua_laki.text.toString()
            mertuaLakiReq.nama_alias = edt_alias_mertua_laki.text.toString()
            mertuaLakiReq.tempat_lahir = edt_tmpt_ttl_mertua_laki.text.toString()
            mertuaLakiReq.tanggal_lahir = edt_tgl_ttl_mertua_laki.text.toString()
            mertuaLakiReq.agama = agama_skrg
            mertuaLakiReq.aliran_kepercayaan_dianut = edt_aliran_dianut_mertua_laki.text.toString()
            mertuaLakiReq.ras = edt_suku_mertua_laki.text.toString()
            mertuaLakiReq.kewarganegaran = edt_kwg_mertua_laki.text.toString()
            mertuaLakiReq.cara_peroleh_kewarganegaraan= edt_how_to_kwg_mertua_laki.text.toString()
            mertuaLakiReq.alamat_rumah= edt_almt_skrg_mertua_laki.text.toString()
            mertuaLakiReq.no_telp_rumah= edt_no_telp_mertua_laki.text.toString()
            mertuaLakiReq.alamat_rumah_sebelumnya = edt_almt_rmh_sblm_mertua_laki.text.toString()

            mertuaLakiReq.status_kerja = stts_kerja
            mertuaLakiReq.pekerjaan_terakhir= edt_pekerjaan_mertua_laki.text.toString()
            mertuaLakiReq.alasan_pensiun = edt_alsn_berhenti_mertua_laki.text.toString()
            mertuaLakiReq.tahun_pensiun = edt_thn_berhenti_mertua_laki.text.toString()

            mertuaLakiReq.nama_kantor = edt_nama_almt_kntr_mertua_laki.text.toString()
            mertuaLakiReq.alamat_kantor = edt_almt_kntr_mertua_laki.text.toString()
            mertuaLakiReq.no_telp_kantor = edt_no_telp_kntr_mertua_laki.text.toString()
            mertuaLakiReq.pekerjaan_sebelumnya = edt_pekerjaan_sblm_mertua_laki.text.toString()
            mertuaLakiReq.pendidikan_terakhir = edt_pend_trkhr_mertua_laki.text.toString()
            mertuaLakiReq.pernikahan_brp = edt_pernikahan_brp_mertua_laki.text.toString()

            mertuaLakiReq.status_kehidupan = stts_hidup
            mertuaLakiReq.tahun_kematian = edt_tahun_kematian_mertua_laki.text.toString()
            mertuaLakiReq.lokasi_kematian = edt_dimana_mertua_laki.text.toString()
            mertuaLakiReq.sebab_kematian = edt_penyebab_mertua_laki.text.toString()

            sessionManager.setMertuaLaki(mertuaLakiReq)
            Log.e("mertuaLaki", "${sessionManager.getMertuaLaki()}")
            startActivity(Intent(this, AddMertuaPerempuanActivity::class.java))
        }
    }

    private fun initSpinner(
        spinnerPekerjaanMertuaLaki: AutoCompleteTextView,
        spinnerSttsHidupMertuaLaki: AutoCompleteTextView,
        spAgamaMertuaLaki: AutoCompleteTextView
    ) {
        val item = listOf("Masih", "Tidak")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        spinnerPekerjaanMertuaLaki.setAdapter(adapter)
        spinnerPekerjaanMertuaLaki.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                txt_layout_nama_kantor_mertua_laki.visibility = View.VISIBLE
                txt_layout_alamat_kantor_mertua_laki.visibility = View.VISIBLE
                txt_layout_no_telp_kantor_mertua_laki.visibility = View.VISIBLE
                txt_layout_thn_berhenti_mertua_laki.visibility = View.GONE
                txt_layout_alsn_berhenti_mertua_laki.visibility = View.GONE
                stts_kerja = 1

            } else {
                txt_layout_nama_kantor_mertua_laki.visibility = View.GONE
                txt_layout_alamat_kantor_mertua_laki.visibility = View.GONE
                txt_layout_no_telp_kantor_mertua_laki.visibility = View.GONE
                txt_layout_thn_berhenti_mertua_laki.visibility = View.VISIBLE
                txt_layout_alsn_berhenti_mertua_laki.visibility = View.VISIBLE
                stts_kerja = 0

            }
        }
        val itemHidup = listOf("Masih", "Tidak")
        val adapterHidup = ArrayAdapter(this, R.layout.item_spinner, itemHidup)
        spinnerSttsHidupMertuaLaki.setAdapter(adapterHidup)
        spinnerSttsHidupMertuaLaki.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                txt_layout_penyebab_mertua_laki.visibility = View.GONE
                txt_layout_dimana_mertua_laki.visibility = View.GONE
                txt_layout_bagaimana_stts_mertua_laki.visibility = View.GONE
                stts_hidup = 1
            } else {
                txt_layout_penyebab_mertua_laki.visibility = View.VISIBLE
                txt_layout_dimana_mertua_laki.visibility = View.VISIBLE
                txt_layout_bagaimana_stts_mertua_laki.visibility = View.VISIBLE
                stts_hidup = 0
            }
        }

        val agama = listOf("Islam", "Katolik", "Protestan", "Budha", "Hindu", "Konghuchu")
        val adapterAgama = ArrayAdapter(this, R.layout.item_spinner, agama)
        spAgamaMertuaLaki.setAdapter(adapterAgama)
        spAgamaMertuaLaki.setOnItemClickListener { parent, view, position, id ->
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