package id.calocallo.sicape.ui.main.addpersonal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AyahTiriReq
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_ayah_tiri.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddAyahTiriActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var ayahTiriReq = AyahTiriReq()
    var stts_kerja = 0
    var stts_hidup = 0
    var agama_skrg = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ayah_tiri)
        setupActionBarWithBackButton(toolbar)

        supportActionBar?.title = "Ayah Tiri / Angkat"
        initSpinner(spinner_pekerjaan_ayah_tiri, spinner_stts_hidup_ayah_tiri, sp_agama_ayah_tiri)

        val nama_lengkap = edt_nama_lngkp_ayah_tiri.text.toString()
        val alias = edt_alias_ayah_tiri.text.toString()
        val tempat_lhr = edt_tmpt_ttl_ayah_tiri.text.toString()
        val tgl_lhr = edt_tgl_ttl_ayah_tiri.text.toString()
        val agama = sp_agama_ayah_tiri.text.toString()
        val aliran_dianut = edt_aliran_dianut_ayah_tiri.text.toString()
        val suku = edt_suku_ayah_tiri.text.toString()
        val kwg = edt_kwg_ayah_tiri.text.toString()
        val how_to_kwg = edt_how_to_kwg_ayah_tiri.text.toString()
        val almt_skrg = edt_almt_skrg_ayah_tiri.text.toString()
        val no_telp_rmh = edt_no_telp_ayah_tiri.text.toString()
        val almt_sblm = edt_almt_rmh_sblm_ayah_tiri.text.toString()
        val pkrjaan = edt_pekerjaan_ayah_tiri.text.toString()
        val almt_kantor = edt_almt_kntr_ayah_tiri.text.toString()
        val no_tlp_kantor = edt_no_telp_kntr_ayah_tiri.text.toString()
        val pekerjaan_sblm = edt_pekerjaan_sblm_ayah_tiri.text.toString()
        val pend_terakhir = edt_pend_trkhr_ayah_tiri.text.toString()


        //organisasi yang diikuti
        val kddkn_org_diikuti = edt_kddkn_org_diikuti_ayah_tiri.text.toString()
        val thn_org_diikut = edt_thn_org_diikuti_ayah_tiri.text.toString()
        val alasan_org_diikuti = edt_alasan_org_diikuti_ayah_tiri.text.toString()
        val almt_org_diikuti = edt_almt_org_diikuti_ayah_tiri.text.toString()

        //organisasi yang pernah diikuti
        val kddkn_org_prnh = edt_kddkn_org_prnh_ayah_tiri.text.toString()
        val thn_org_prnh = edt_thn_org_prnh_ayah_tiri.text.toString()
        val alasan_org_prnh = edt_alasan_org_prnh_ayah_tiri.text.toString()
        val almt_org_prnh = edt_almt_org_prnh_ayah_tiri.text.toString()

        //status hidup
        val bagaimana_hidup = edt_tahun_kematian_ayah_tiri.text.toString()
        val dimana_hidup = edt_dimana_ayah_tiri.text.toString()
        val penyebab_hidup = edt_penyebab_ayah_tiri.text.toString()
        sessionManager = SessionManager(this)


        btn_next_ayah_tiri.setOnClickListener {
            ayahTiriReq.nama = edt_nama_lngkp_ayah_tiri.text.toString()
            ayahTiriReq.nama_alias = edt_alias_ayah_tiri.text.toString()
            ayahTiriReq.tempat_lahir = edt_tmpt_ttl_ayah_tiri.text.toString()
            ayahTiriReq.tanggal_lahir = edt_tgl_ttl_ayah_tiri.text.toString()
            ayahTiriReq.agama = agama_skrg
            ayahTiriReq.aliran_kepercayaan_dianut = edt_aliran_dianut_ayah_tiri.text.toString()
            ayahTiriReq.ras = edt_suku_ayah_tiri.text.toString()
            ayahTiriReq.kewarganegaran = edt_kwg_ayah_tiri.text.toString()
            ayahTiriReq.cara_peroleh_kewarganegaraan = edt_how_to_kwg_ayah_tiri.text.toString()
            ayahTiriReq.alamat_rumah = edt_almt_skrg_ayah_tiri.text.toString()
            ayahTiriReq.no_telp_rumah = edt_no_telp_ayah_tiri.text.toString()
            ayahTiriReq.alamat_rumah_sebelumnya = edt_almt_rmh_sblm_ayah_tiri.text.toString()

            ayahTiriReq.status_kerja = stts_kerja
            ayahTiriReq.pekerjaan_terakhir = edt_pekerjaan_ayah_tiri.text.toString()
            ayahTiriReq.alasan_pensiun = edt_alsn_berhenti_ayah_tiri.text.toString()
            ayahTiriReq.tahun_pensiun = edt_thn_berhenti_ayah_tiri.text.toString()

            ayahTiriReq.nama_kantor = edt_nama_kntr_ayah_tiri.text.toString()
            ayahTiriReq.alamat_kantor = edt_almt_kntr_ayah_tiri.text.toString()
            ayahTiriReq.no_telp_kantor = edt_no_telp_kntr_ayah_tiri.text.toString()
            ayahTiriReq.pekerjaan_sebelumnya = edt_pekerjaan_sblm_ayah_tiri.text.toString()
            ayahTiriReq.pendidikan_terakhir = edt_pend_trkhr_ayah_tiri.text.toString()
            ayahTiriReq.pernikahan_brp = edt_pernikahan_brp_ayah_tiri.text.toString()

            ayahTiriReq.tahun_kematian = edt_tahun_kematian_ayah_tiri.text.toString()
            ayahTiriReq.lokasi_kematian = edt_dimana_ayah_tiri.text.toString()
            ayahTiriReq.sebab_kematian = edt_penyebab_ayah_tiri.text.toString()

            sessionManager.setAyahTiri(ayahTiriReq)
            Log.e("ayahTiri","${sessionManager.getAyahTiri()}")
            startActivity(Intent(this, AddIbuKandungActivity::class.java))
        }


    }

    private fun initSpinner(
        spinnerPekerjaanAyah: AutoCompleteTextView,
        spinnerSttsHidupAyah: AutoCompleteTextView,
        spAgamaAyahTiri: AutoCompleteTextView
    ) {
        val item = listOf("Masih", "Tidak")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        spinnerPekerjaanAyah.setAdapter(adapter)
        spinnerPekerjaanAyah.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                txt_layout_pekerjaan_ayah_tiri.visibility = View.VISIBLE
                txt_layout_thn_berhenti_ayah_tiri.visibility = View.GONE
                txt_layout_alsn_berhenti_ayah_tiri.visibility = View.GONE
                stts_kerja = 1
            } else {
                txt_layout_thn_berhenti_ayah_tiri.visibility = View.VISIBLE
                txt_layout_alsn_berhenti_ayah_tiri.visibility = View.VISIBLE
                txt_layout_pekerjaan_ayah_tiri.visibility = View.GONE
                stts_kerja = 0
            }
        }
        val itemHidup = listOf("Masih", "Tidak")
        val adapterHidup = ArrayAdapter(this, R.layout.item_spinner, itemHidup)
        spinnerSttsHidupAyah.setAdapter(adapterHidup)
        spinnerSttsHidupAyah.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                txt_layout_penyebab_ayah_tiri.visibility = View.GONE
                txt_layout_bagaimana_stts_ayah_tiri.visibility = View.GONE
                txt_layout_dimana_ayah_tiri.visibility = View.GONE
                stts_hidup = 1
            } else {
                txt_layout_penyebab_ayah_tiri.visibility = View.VISIBLE
                txt_layout_bagaimana_stts_ayah_tiri.visibility = View.VISIBLE
                txt_layout_dimana_ayah_tiri.visibility = View.VISIBLE
                stts_hidup = 0

            }
        }

        val agama = listOf("Islam", "Katolik", "Protestan", "Budha", "Hindu", "Konghuchu")
        val adapterAgama = ArrayAdapter(this, R.layout.item_spinner, agama)
        spAgamaAyahTiri.setAdapter(adapterAgama)
        spAgamaAyahTiri.setOnItemClickListener { parent, view, position, id ->
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