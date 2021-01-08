package id.calocallo.sicape.ui.main.addpersonal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.MertuaPerempuanReq
import id.calocallo.sicape.ui.main.addpersonal.anak.AddAnakActivity
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_mertua_perempuan.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddMertuaPerempuanActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager

    private var mertuaPerempuanReq =
        MertuaPerempuanReq()
    var stts_kerja = 0
    var stts_hidup = 0
    var agama_skrg = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_mertua_perempuan)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Mertua Perempuan"
        sessionManager = SessionManager(this)

        initSpinner(
            spinner_pekerjaan_mertua_perempuan,
            spinner_stts_hidup_mertua_perempuan,
            sp_agama_mertua_perempuan
        )

        btn_next_mertua_perempuan.setOnClickListener {
            mertuaPerempuanReq.nama = edt_nama_lngkp_mertua_perempuan.text.toString()
            mertuaPerempuanReq.nama_alias = edt_alias_mertua_perempuan.text.toString()
            mertuaPerempuanReq.tempat_lahir = edt_tmpt_ttl_mertua_perempuan.text.toString()
            mertuaPerempuanReq.tanggal_lahir = edt_tgl_ttl_mertua_perempuan.text.toString()
            mertuaPerempuanReq.agama = agama_skrg
            mertuaPerempuanReq.aliran_kepercayaan_dianut = edt_aliran_dianut_mertua_perempuan.text.toString()
            mertuaPerempuanReq.ras = edt_suku_mertua_perempuan.text.toString()
            mertuaPerempuanReq.kewarganegaran = edt_kwg_mertua_perempuan.text.toString()
            mertuaPerempuanReq.cara_peroleh_kewarganegaraan= edt_how_to_kwg_mertua_perempuan.text.toString()
            mertuaPerempuanReq.alamat_rumah= edt_almt_skrg_mertua_perempuan.text.toString()
            mertuaPerempuanReq.no_telp_rumah= edt_no_telp_mertua_perempuan.text.toString()
            mertuaPerempuanReq.alamat_rumah_sebelumnya = edt_almt_rmh_sblm_mertua_perempuan.text.toString()

            mertuaPerempuanReq.status_kerja = stts_kerja
            mertuaPerempuanReq.pekerjaan_terakhir= edt_pekerjaan_mertua_perempuan.text.toString()
            mertuaPerempuanReq.alasan_pensiun = edt_alsn_berhenti_mertua_perempuan.text.toString()
            mertuaPerempuanReq.tahun_pensiun = edt_thn_berhenti_mertua_perempuan.text.toString()

            mertuaPerempuanReq.nama_kantor = edt_nama_almt_kntr_mertua_perempuan.text.toString()
            mertuaPerempuanReq.alamat_kantor = edt_almt_kntr_mertua_perempuan.text.toString()
            mertuaPerempuanReq.no_telp_kantor = edt_no_telp_kntr_mertua_perempuan.text.toString()
            mertuaPerempuanReq.pekerjaan_sebelumnya = edt_pekerjaan_sblm_mertua_perempuan.text.toString()
            mertuaPerempuanReq.pendidikan_terakhir = edt_pend_trkhr_mertua_perempuan.text.toString()

            mertuaPerempuanReq.status_kehidupan = stts_hidup
            mertuaPerempuanReq.tahun_kematian = edt_tahun_kematian_mertua_perempuan.text.toString()
            mertuaPerempuanReq.lokasi_kematian = edt_dimana_mertua_perempuan.text.toString()
            mertuaPerempuanReq.sebab_kematian = edt_penyebab_mertua_perempuan.text.toString()
            sessionManager.setMertuaPerempuan(mertuaPerempuanReq)
            Log.e("mertuaPerempuan", "${sessionManager.getMertuaPerempuan()}")
            startActivity(Intent(this, AddAnakActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun initSpinner(
        spinnerPekerjaanMertuaPerempuan: AutoCompleteTextView,
        spinnerSttsHidupMertuaPerempuan: AutoCompleteTextView,
        spAgamaMertuaPerempuan: AutoCompleteTextView
    ) {
        val item = listOf("Masih", "Tidak")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        spinnerPekerjaanMertuaPerempuan.setAdapter(adapter)
        spinnerPekerjaanMertuaPerempuan.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                txt_layout_nama_kantor_mertua_perempuan.visibility = View.VISIBLE
                txt_layout_alamat_kantor_mertua_perempuan.visibility = View.VISIBLE
                txt_layout_no_telp_kantor_mertua_perempuan.visibility = View.VISIBLE
                txt_layout_thn_berhenti_mertua_perempuan.visibility = View.GONE
                txt_layout_alsn_berhenti_mertua_perempuan.visibility = View.GONE
                stts_kerja = 1

            } else {
                txt_layout_pekerjaan_mertua_perempuan.visibility = View.GONE
                txt_layout_nama_kantor_mertua_perempuan.visibility = View.GONE
                txt_layout_alamat_kantor_mertua_perempuan.visibility = View.GONE
                txt_layout_no_telp_kantor_mertua_perempuan.visibility = View.GONE
                txt_layout_thn_berhenti_mertua_perempuan.visibility = View.VISIBLE
                txt_layout_alsn_berhenti_mertua_perempuan.visibility = View.VISIBLE
                stts_kerja = 0

            }
        }
        val itemHidup = listOf("Masih", "Tidak")
        val adapterHidup = ArrayAdapter(this, R.layout.item_spinner, itemHidup)
        spinnerSttsHidupMertuaPerempuan.setAdapter(adapterHidup)
        spinnerSttsHidupMertuaPerempuan.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                txt_layout_penyebab_mertua_perempuan.visibility = View.GONE
                txt_layout_dimana_mertua_perempuan.visibility = View.GONE
                txt_layout_bagaimana_stts_mertua_perempuan.visibility = View.GONE
                stts_hidup = 1
            } else {
                txt_layout_dimana_mertua_perempuan.visibility = View.VISIBLE
                txt_layout_bagaimana_stts_mertua_perempuan.visibility = View.VISIBLE
                stts_hidup = 0
            }
        }

        val agama = listOf("Islam", "Katolik", "Protestan", "Budha", "Hindu", "Konghuchu")
        val adapterAgama = ArrayAdapter(this, R.layout.item_spinner, agama)
        spAgamaMertuaPerempuan.setAdapter(adapterAgama)
        spAgamaMertuaPerempuan.setOnItemClickListener { parent, view, position, id ->
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