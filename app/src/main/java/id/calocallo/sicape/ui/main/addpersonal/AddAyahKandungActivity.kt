package id.calocallo.sicape.ui.main.addpersonal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AyahReq
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_ayah_kandung.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddAyahKandungActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager

    private var ayahReq = AyahReq()
    var stts_kerja = 0
    var stts_hidup = 0
    var agama_skrg = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ayah_kandung)
        setupActionBarWithBackButton(toolbar)
        sessionManager = SessionManager(this)
        supportActionBar?.title = "Ayah Kandung"
        initSpinner(spinner_pekerjaan_ayah, spinner_stts_hidup_ayah, sp_agama_ayah)

        btn_next_ayah.setOnClickListener {
            ayahReq.nama = edt_nama_lngkp_ayah.text.toString()
            ayahReq.nama_alias = edt_alias_ayah.text.toString()
            ayahReq.tempat_lahir = edt_tmpt_ttl_ayah.text.toString()
            ayahReq.tanggal_lahir = edt_tgl_ttl_ayah.text.toString()
            ayahReq.agama = agama_skrg
            ayahReq.aliran_kepercayaan_dianut = edt_aliran_dianut_ayah.text.toString()
            ayahReq.ras = edt_suku_ayah.text.toString()
            ayahReq.kewarganegaran = edt_kwg_ayah.text.toString()
            ayahReq.cara_peroleh_kewarganegaraan= edt_how_to_kwg_ayah.text.toString()
            ayahReq.alamat_rumah= edt_almt_skrg_ayah.text.toString()
            ayahReq.no_telp_rumah= edt_no_telp_ayah.text.toString()
            ayahReq.alamat_rumah_sebelumnya = edt_almt_rmh_sblm_ayah.text.toString()

            ayahReq.status_kerja = stts_kerja
            ayahReq.pekerjaan_terakhir= edt_pekerjaan_ayah.text.toString()
            ayahReq.alasan_pensiun = edt_alsn_berhenti_ayah.text.toString()
            ayahReq.tahun_pensiun = edt_thn_berhenti_ayah.text.toString()

            ayahReq.nama_kantor = edt_nama_almt_kntr_ayah.text.toString()
            ayahReq.alamat_kantor = edt_almt_kntr_ayah.text.toString()
            ayahReq.no_telp_kantor = edt_no_telp_kntr_ayah.text.toString()
            ayahReq.pekerjaan_sebelumnya = edt_pekerjaan_sblm_ayah.text.toString()
            ayahReq.pendidikan_terakhir = edt_pend_trkhr_ayah.text.toString()
            ayahReq.pernikahan_brp = edt_pernikahan_brp_ayah.text.toString()

            ayahReq.status_kehidupan = stts_hidup
            ayahReq.tahun_kematian = edt_tahun_kematian_ayah.text.toString()
            ayahReq.lokasi_kematian = edt_dimana_ayah.text.toString()
            ayahReq.sebab_kematian = edt_penyebab_ayah.text.toString()
            sessionManager.setAyahKandung(ayahReq)
            Log.e("ayahKandung", "${sessionManager.getAyahKandung()}")
            //init(allModel)
            //berhasil -> GO
            //gagal -> TOAST

//            Log.e("ayah_kandung_activity", "$stts_kerja, $stts_hidup")
            startActivity(Intent(this, AddAyahTiriActivity::class.java))
        }
    }

    private fun initSpinner(
        spinner: AutoCompleteTextView,
        spinnerSttsHidup: AutoCompleteTextView,
        spAgamaAyah: AutoCompleteTextView
    ) {
        val item = listOf("Masih", "Tidak")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        spinner.setAdapter(adapter)
        spinner.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) { // masih
//                Log.e("stts_kerja_spinner", parent.getItemAtPosition(position).toString())
                txt_layout_pekerjaan_ayah.visibility = View.VISIBLE
                txt_layout_thn_berhenti_ayah.visibility = View.GONE
                txt_layout_alsn_berhenti_ayah.visibility = View.GONE
//                stts_kerja = parent.getItemAtPosition(position).toString()
                stts_kerja = 1

            } else { //tidak bekerja
//                Log.e("stts_kerja_spinner", parent.getItemAtPosition(position).toString())
                txt_layout_thn_berhenti_ayah.visibility = View.VISIBLE
                txt_layout_alsn_berhenti_ayah.visibility = View.VISIBLE
                txt_layout_pekerjaan_ayah.visibility = View.GONE
//                stts_kerja = parent.getItemAtPosition(position).toString()
                stts_kerja = 0

            }
        }
        val itemHidup = listOf("Masih", "Tidak")
        val adapterHidup = ArrayAdapter(this, R.layout.item_spinner, itemHidup)
        spinnerSttsHidup.setAdapter(adapterHidup)
        spinnerSttsHidup.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                txt_layout_penyebab_ayah.visibility = View.GONE
                txt_layout_bagaimana_stts_ayah.visibility = View.GONE
                txt_layout_dimana_ayah.visibility = View.GONE
//                stts_hidup = parent.getItemAtPosition(position).toString()
                stts_hidup = 1
            } else {
                txt_layout_penyebab_ayah.visibility = View.VISIBLE
                txt_layout_bagaimana_stts_ayah.visibility = View.VISIBLE
                txt_layout_dimana_ayah.visibility = View.VISIBLE
                stts_hidup = 0

            }

        }

        val agama = listOf("Islam", "Katolik", "Protestan", "Budha", "Hindu", "Konghuchu")
        val adapterAgama = ArrayAdapter(this, R.layout.item_spinner, agama)
        spAgamaAyah.setAdapter(adapterAgama)
        spAgamaAyah.setOnItemClickListener { parent, view, position, id ->
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