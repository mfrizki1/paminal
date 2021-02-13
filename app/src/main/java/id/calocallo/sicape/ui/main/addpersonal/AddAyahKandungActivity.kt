package id.calocallo.sicape.ui.main.addpersonal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.KeluargaReq
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_ayah_kandung.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddAyahKandungActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1

    private var keluargaReq = KeluargaReq()
    var stts_kerja = 0
    var stts_hidup = 0
    var agama_skrg = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ayah_kandung)
        setupActionBarWithBackButton(toolbar)
        sessionManager1 = SessionManager1(this)
        supportActionBar?.title = "Ayah Kandung"
        initSpinner(spinner_pekerjaan_ayah, spinner_stts_hidup_ayah, sp_agama_ayah)
        btn_next_ayah.setOnClickListener {
            keluargaReq.status_hubungan = "ayah_kandung"
            keluargaReq.agama = agama_skrg
            keluargaReq.status_kehidupan = stts_hidup
            keluargaReq.status_kerja = stts_kerja
            keluargaReq.nama = edt_nama_lngkp_ayah.text.toString()
            keluargaReq.nama_alias = edt_alias_ayah.text.toString()
            keluargaReq.tempat_lahir = edt_tmpt_ttl_ayah.text.toString()
            keluargaReq.tanggal_lahir = edt_tgl_ttl_ayah.text.toString()
            keluargaReq.aliran_kepercayaan_dianut = edt_aliran_dianut_ayah.text.toString()
            keluargaReq.ras = edt_suku_ayah.text.toString()
            keluargaReq.kewarganegaraan = edt_kwg_ayah.text.toString()
            keluargaReq.cara_peroleh_kewarganegaraan = edt_how_to_kwg_ayah.text.toString()
            keluargaReq.alamat_rumah = edt_almt_skrg_ayah.text.toString()
            keluargaReq.no_telp_rumah = edt_no_telp_ayah.text.toString()
            keluargaReq.alamat_rumah_sebelumnya = edt_almt_rmh_sblm_ayah.text.toString()

            keluargaReq.pekerjaan_terakhir = edt_pekerjaan_ayah.text.toString()
            keluargaReq.alasan_pensiun = edt_alsn_berhenti_ayah.text.toString()
            keluargaReq.tahun_pensiun = edt_thn_berhenti_ayah.text.toString()

            keluargaReq.nama_kantor = edt_nama_kntr_ayah.text.toString()
            keluargaReq.alamat_kantor = edt_almt_kntr_ayah.text.toString()
            keluargaReq.no_telp_kantor = edt_no_telp_kntr_ayah.text.toString()
            keluargaReq.pekerjaan_sebelumnya = edt_pekerjaan_sblm_ayah.text.toString()
            keluargaReq.pendidikan_terakhir = edt_pend_trkhr_ayah.text.toString()

            keluargaReq.tahun_kematian = edt_tahun_kematian_ayah.text.toString()
            keluargaReq.lokasi_kematian = edt_dimana_ayah.text.toString()
            keluargaReq.sebab_kematian = edt_penyebab_ayah.text.toString()


            keluargaReq.kedudukan_organisasi_saat_ini = edt_kddkn_org_diikuti_ayah.text.toString()
            keluargaReq.tahun_bergabung_organisasi_saat_ini =
                edt_thn_org_diikuti_ayah.text.toString()
            keluargaReq.alasan_bergabung_organisasi_saat_ini =
                edt_alasan_org_diikuti_ayah.text.toString()
            keluargaReq.alamat_organisasi_saat_ini = edt_almt_org_diikuti_ayah.text.toString()
            keluargaReq.kedudukan_organisasi_sebelumnya = edt_kddkn_org_prnh_ayah.text.toString()
            keluargaReq.tahun_bergabung_organisasi_sebelumnya =
                edt_thn_org_prnh_ayah.text.toString()
            keluargaReq.alasan_bergabung_organisasi_sebelumnya =
                edt_alasan_org_prnh_ayah.text.toString()
            keluargaReq.alamat_organisasi_sebelumnya = edt_almt_org_prnh_ayah.text.toString()

            Log.e("ayahKandung", "${sessionManager1.getAyahKandung()}")
            if(keluargaReq.nama != null) {
                sessionManager1.setAyahKandung(keluargaReq)
            }else{
                sessionManager1.clearAyahKandung()
            }
//            Log.e("ayah_kandung_activity", "$stts_kerja, $stts_hidup")
            startActivity(Intent(this, AddAyahTiriActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun test(ayahKandung: KeluargaReq) {
        edt_nama_lngkp_ayah.setText(ayahKandung.nama)
        edt_alias_ayah.setText(ayahKandung.nama_alias)
        edt_tmpt_ttl_ayah.setText(ayahKandung.tempat_lahir)
        edt_tgl_ttl_ayah.setText(ayahKandung.tanggal_lahir)
        edt_aliran_dianut_ayah.setText(ayahKandung.aliran_kepercayaan_dianut)
        edt_suku_ayah.setText(ayahKandung.ras)
        edt_kwg_ayah.setText(ayahKandung.kewarganegaraan)
        edt_how_to_kwg_ayah.setText(ayahKandung.cara_peroleh_kewarganegaraan)
        edt_almt_skrg_ayah.setText(ayahKandung.alamat_rumah)
        edt_no_telp_ayah.setText(ayahKandung.no_telp_rumah)
        edt_almt_rmh_sblm_ayah.setText(ayahKandung.alamat_rumah)
        edt_pekerjaan_ayah.setText(ayahKandung.pekerjaan_terakhir)
        edt_alsn_berhenti_ayah.setText(ayahKandung.alasan_pensiun)
        edt_thn_berhenti_ayah.setText(ayahKandung.tahun_pensiun)
        edt_nama_kntr_ayah.setText(ayahKandung.nama_kantor)
        edt_almt_kntr_ayah.setText(ayahKandung.alamat_kantor)
        edt_no_telp_kntr_ayah.setText(ayahKandung.no_telp_kantor)
        edt_pekerjaan_sblm_ayah.setText(ayahKandung.pekerjaan_sebelumnya)
        edt_pend_trkhr_ayah.setText(ayahKandung.pendidikan_terakhir)
        edt_tahun_kematian_ayah.setText(ayahKandung.tahun_kematian)
        edt_dimana_ayah.setText(ayahKandung.lokasi_kematian)
        edt_penyebab_ayah.setText(ayahKandung.sebab_kematian)

        edt_kddkn_org_diikuti_ayah.setText(ayahKandung.kedudukan_organisasi_saat_ini)
        edt_thn_org_diikuti_ayah.setText(ayahKandung.tahun_bergabung_organisasi_saat_ini)
        edt_alasan_org_diikuti_ayah.setText(ayahKandung.alasan_bergabung_organisasi_saat_ini)
        edt_almt_org_diikuti_ayah.setText(ayahKandung.alamat_organisasi_saat_ini)
        edt_kddkn_org_prnh_ayah.setText(ayahKandung.kedudukan_organisasi_sebelumnya)
        edt_thn_org_prnh_ayah.setText(ayahKandung.tahun_bergabung_organisasi_sebelumnya)
        edt_alasan_org_prnh_ayah.setText(ayahKandung.alasan_bergabung_organisasi_sebelumnya)
        edt_almt_org_prnh_ayah.setText(ayahKandung.alamat_rumah_sebelumnya)
        agama_skrg = ayahKandung.agama.toString()
        sp_agama_ayah.setText(ayahKandung.agama)

        stts_hidup = ayahKandung.status_kehidupan!!
        if (ayahKandung.status_kehidupan == 1) {
            txt_layout_penyebab_ayah.visibility = View.GONE
            txt_layout_bagaimana_stts_ayah.visibility = View.GONE
            txt_layout_dimana_ayah.visibility = View.GONE

            spinner_stts_hidup_ayah.setText("Masih")
        } else {
            txt_layout_penyebab_ayah.visibility = View.VISIBLE
            txt_layout_bagaimana_stts_ayah.visibility = View.VISIBLE
            txt_layout_dimana_ayah.visibility = View.VISIBLE
            spinner_stts_hidup_ayah.setText("Tidak")

        }

        stts_kerja = ayahKandung.status_kerja!!
        if (ayahKandung.status_kerja == 1) {
            txt_layout_nama_kantor_ayah.visibility = View.VISIBLE
            txt_layout_alamat_kantor_ayah.visibility = View.VISIBLE
            txt_layout_no_telp_kantor_ayah.visibility = View.VISIBLE
            txt_layout_thn_berhenti_ayah.visibility = View.GONE
            txt_layout_alsn_berhenti_ayah.visibility = View.GONE
            spinner_pekerjaan_ayah.setText("Masih")

        } else {
            txt_layout_nama_kantor_ayah.visibility = View.GONE
            txt_layout_alamat_kantor_ayah.visibility = View.GONE
            txt_layout_no_telp_kantor_ayah.visibility = View.GONE
            txt_layout_thn_berhenti_ayah.visibility = View.VISIBLE
            txt_layout_alsn_berhenti_ayah.visibility = View.VISIBLE
            spinner_pekerjaan_ayah.setText("Tidak")

        }
        initSpinner(spinner_pekerjaan_ayah, spinner_stts_hidup_ayah, sp_agama_ayah)
    }

    private fun initSpinner(
        spinner: AutoCompleteTextView, //pekerjaan
        spinnerSttsHidup: AutoCompleteTextView, //hidup
        spAgamaAyah: AutoCompleteTextView //agama
    ) {
        val item = listOf("Masih", "Tidak")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        spinner.setAdapter(adapter)
        spinner.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) { // masih
//                Log.e("stts_kerja_spinner", parent.getItemAtPosition(position).toString())

                txt_layout_nama_kantor_ayah.visibility = View.VISIBLE
                txt_layout_alamat_kantor_ayah.visibility = View.VISIBLE
                txt_layout_no_telp_kantor_ayah.visibility = View.VISIBLE
                txt_layout_thn_berhenti_ayah.visibility = View.GONE
                txt_layout_alsn_berhenti_ayah.visibility = View.GONE
                edt_thn_berhenti_ayah.text = null
                edt_alsn_berhenti_ayah.text = null
//                stts_kerja = parent.getItemAtPosition(position).toString()
                stts_kerja = 1

            } else { //tidak bekerja
//                Log.e("stts_kerja_spinner", parent.getItemAtPosition(position).toString())
                txt_layout_nama_kantor_ayah.visibility = View.GONE
                txt_layout_alamat_kantor_ayah.visibility = View.GONE
                txt_layout_no_telp_kantor_ayah.visibility = View.GONE
                txt_layout_thn_berhenti_ayah.visibility = View.VISIBLE
                txt_layout_alsn_berhenti_ayah.visibility = View.VISIBLE

                edt_nama_kntr_ayah.text = null
                edt_almt_kntr_ayah.text = null
                edt_no_telp_kntr_ayah.text = null
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
                edt_penyebab_ayah.text = null
                edt_tahun_kematian_ayah.text = null
                edt_dimana_ayah.text = null
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
                agama_skrg = "buddha"
            } else if (position == 4) {
                agama_skrg = "hindu"
            } else {
                agama_skrg = "konghuchu"
            }
        }
    }

    override fun onResume() {
        super.onResume()
        initSpinner(spinner_pekerjaan_ayah, spinner_stts_hidup_ayah, sp_agama_ayah)
        val ayah = sessionManager1.getAyahKandung()
        if (ayah.status_hubungan != null) {
            test(ayah)
        }

    }


}