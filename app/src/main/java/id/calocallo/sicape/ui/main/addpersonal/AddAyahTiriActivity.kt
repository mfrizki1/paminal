package id.calocallo.sicape.ui.main.addpersonal

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.KeluargaReq
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_ayah_tiri.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddAyahTiriActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var keluargaReq = KeluargaReq()
    var stts_kerja = 0
    var stts_hidup = 0
    var agama_skrg = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ayah_tiri)
        setupActionBarWithBackButton(toolbar)

        supportActionBar?.title = "Ayah Tiri / Angkat"
        initSpinner(spinner_pekerjaan_ayah_tiri, spinner_stts_hidup_ayah_tiri, sp_agama_ayah_tiri)
        sessionManager1 = SessionManager1(this)


        btn_next_ayah_tiri.setOnClickListener {
            keluargaReq.nama = edt_nama_lngkp_ayah_tiri.text.toString()
            if (edt_nama_lngkp_ayah_tiri.text?.length!! > 1) {
                keluargaReq.status_hubungan = "ayah_tiri"
            }
            keluargaReq.nama_alias = edt_alias_ayah_tiri.text.toString()
            keluargaReq.tempat_lahir = edt_tmpt_ttl_ayah_tiri.text.toString()
            keluargaReq.tanggal_lahir = edt_tgl_ttl_ayah_tiri.text.toString()
            keluargaReq.agama = agama_skrg
            keluargaReq.aliran_kepercayaan_dianut = edt_aliran_dianut_ayah_tiri.text.toString()
            keluargaReq.ras = edt_suku_ayah_tiri.text.toString()
            keluargaReq.kewarganegaraan = edt_kwg_ayah_tiri.text.toString()
            keluargaReq.cara_peroleh_kewarganegaraan = edt_how_to_kwg_ayah_tiri.text.toString()
            keluargaReq.alamat_rumah = edt_almt_skrg_ayah_tiri.text.toString()
            keluargaReq.no_telp_rumah = edt_no_telp_ayah_tiri.text.toString()
            keluargaReq.alamat_rumah_sebelumnya = edt_almt_rmh_sblm_ayah_tiri.text.toString()

            keluargaReq.status_kerja = stts_kerja
            keluargaReq.pekerjaan_terakhir = edt_pekerjaan_ayah_tiri.text.toString()
            keluargaReq.alasan_pensiun = edt_alsn_berhenti_ayah_tiri.text.toString()
            keluargaReq.tahun_pensiun = edt_thn_berhenti_ayah_tiri.text.toString()

            keluargaReq.nama_kantor = edt_nama_kntr_ayah_tiri.text.toString()
            keluargaReq.alamat_kantor = edt_almt_kntr_ayah_tiri.text.toString()
            keluargaReq.no_telp_kantor = edt_no_telp_kntr_ayah_tiri.text.toString()
            keluargaReq.pekerjaan_sebelumnya = edt_pekerjaan_sblm_ayah_tiri.text.toString()
            keluargaReq.pendidikan_terakhir = edt_pend_trkhr_ayah_tiri.text.toString()

            keluargaReq.status_kehidupan = stts_hidup
            keluargaReq.tahun_kematian = edt_tahun_kematian_ayah_tiri.text.toString()
            keluargaReq.lokasi_kematian = edt_dimana_ayah_tiri.text.toString()
            keluargaReq.sebab_kematian = edt_penyebab_ayah_tiri.text.toString()

            keluargaReq.kedudukan_organisasi_saat_ini =
                edt_kddkn_org_diikuti_ayah_tiri.text.toString()
            keluargaReq.tahun_bergabung_organisasi_saat_ini =
                edt_thn_org_diikuti_ayah_tiri.text.toString()
            keluargaReq.alasan_bergabung_organisasi_saat_ini =
                edt_alasan_org_diikuti_ayah_tiri.text.toString()
            keluargaReq.alamat_organisasi_saat_ini = edt_almt_org_diikuti_ayah_tiri.text.toString()
            keluargaReq.kedudukan_organisasi_sebelumnya =
                edt_kddkn_org_prnh_ayah_tiri.text.toString()
            keluargaReq.tahun_bergabung_organisasi_sebelumnya =
                edt_thn_org_prnh_ayah_tiri.text.toString()
            keluargaReq.alasan_bergabung_organisasi_sebelumnya =
                edt_alasan_org_prnh_ayah_tiri.text.toString()
            keluargaReq.alamat_organisasi_sebelumnya = edt_almt_org_prnh_ayah_tiri.text.toString()
            if (keluargaReq.nama != null) {
                sessionManager1.setAyahTiri(keluargaReq)
            }
            Log.e("ayahTiri2", "${sessionManager1.getAyahTiri()}")
            startActivity(Intent(this, AddIbuKandungActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }


    }

    override fun onResume() {
        super.onResume()
        test(sessionManager1.getAyahTiri())
    }

    @SuppressLint("SetTextI18n")
    private fun test(ayahTiri: KeluargaReq) {
        edt_nama_lngkp_ayah_tiri.setText(ayahTiri.nama)
        edt_alias_ayah_tiri.setText(ayahTiri.nama_alias)
        edt_tmpt_ttl_ayah_tiri.setText(ayahTiri.tempat_lahir)
        edt_tgl_ttl_ayah_tiri.setText(ayahTiri.tanggal_lahir)
        edt_aliran_dianut_ayah_tiri.setText(ayahTiri.aliran_kepercayaan_dianut)
        edt_suku_ayah_tiri.setText(ayahTiri.ras)
        edt_kwg_ayah_tiri.setText(ayahTiri.kewarganegaraan)
        edt_how_to_kwg_ayah_tiri.setText(ayahTiri.cara_peroleh_kewarganegaraan)
        edt_almt_skrg_ayah_tiri.setText(ayahTiri.alamat_rumah)
        edt_no_telp_ayah_tiri.setText(ayahTiri.no_telp_rumah)
        edt_almt_rmh_sblm_ayah_tiri.setText(ayahTiri.alamat_rumah)
        edt_pekerjaan_ayah_tiri.setText(ayahTiri.pekerjaan_terakhir)
        edt_alsn_berhenti_ayah_tiri.setText(ayahTiri.alasan_pensiun)
        edt_thn_berhenti_ayah_tiri.setText(ayahTiri.tahun_pensiun)
        edt_nama_kntr_ayah_tiri.setText(ayahTiri.nama_kantor)
        edt_almt_kntr_ayah_tiri.setText(ayahTiri.alamat_kantor)
        edt_no_telp_kntr_ayah_tiri.setText(ayahTiri.no_telp_kantor)
        edt_pekerjaan_sblm_ayah_tiri.setText(ayahTiri.pekerjaan_sebelumnya)
        edt_pend_trkhr_ayah_tiri.setText(ayahTiri.pendidikan_terakhir)
        edt_tahun_kematian_ayah_tiri.setText(ayahTiri.tahun_kematian)
        edt_dimana_ayah_tiri.setText(ayahTiri.lokasi_kematian)
        edt_penyebab_ayah_tiri.setText(ayahTiri.sebab_kematian)

        edt_kddkn_org_diikuti_ayah_tiri.setText(ayahTiri.kedudukan_organisasi_saat_ini)
        edt_thn_org_diikuti_ayah_tiri.setText(ayahTiri.tahun_bergabung_organisasi_saat_ini)
        edt_alasan_org_diikuti_ayah_tiri.setText(ayahTiri.alasan_bergabung_organisasi_saat_ini)
        edt_almt_org_diikuti_ayah_tiri.setText(ayahTiri.alamat_organisasi_saat_ini)
        edt_kddkn_org_prnh_ayah_tiri.setText(ayahTiri.kedudukan_organisasi_sebelumnya)
        edt_thn_org_prnh_ayah_tiri.setText(ayahTiri.tahun_bergabung_organisasi_sebelumnya)
        edt_alasan_org_prnh_ayah_tiri.setText(ayahTiri.alasan_bergabung_organisasi_sebelumnya)
        edt_almt_org_prnh_ayah_tiri.setText(ayahTiri.alamat_rumah_sebelumnya)

        agama_skrg = ayahTiri.agama.toString()
        sp_agama_ayah_tiri.setText(ayahTiri.agama)

        stts_hidup = ayahTiri.status_kehidupan!!
        if (ayahTiri.status_kehidupan == 1) {
            spinner_stts_hidup_ayah_tiri.setText("Masih")
            txt_layout_penyebab_ayah_tiri.visibility = View.GONE
            txt_layout_bagaimana_stts_ayah_tiri.visibility = View.GONE
            txt_layout_dimana_ayah_tiri.visibility = View.GONE
        } else {
            spinner_stts_hidup_ayah_tiri.setText("Tidak")
            txt_layout_penyebab_ayah_tiri.visibility = View.VISIBLE
            txt_layout_bagaimana_stts_ayah_tiri.visibility = View.VISIBLE
            txt_layout_dimana_ayah_tiri.visibility = View.VISIBLE
        }

        stts_kerja = ayahTiri.status_kerja!!
        if (ayahTiri.status_kerja == 1) {
            spinner_pekerjaan_ayah_tiri.setText("Masih")
            txt_layout_nama_kantor_ayah_tiri.visibility = View.VISIBLE
            txt_layout_alamat_kantor_ayah_tiri.visibility = View.VISIBLE
            txt_layout_no_telp_kantor_ayah_tiri.visibility = View.VISIBLE
            txt_layout_thn_berhenti_ayah_tiri.visibility = View.GONE
            txt_layout_alsn_berhenti_ayah_tiri.visibility = View.GONE
        } else {
            spinner_pekerjaan_ayah_tiri.setText("Tidak")
            txt_layout_nama_kantor_ayah_tiri.visibility = View.GONE
            txt_layout_alamat_kantor_ayah_tiri.visibility = View.GONE
            txt_layout_no_telp_kantor_ayah_tiri.visibility = View.GONE
            txt_layout_thn_berhenti_ayah_tiri.visibility = View.VISIBLE
            txt_layout_alsn_berhenti_ayah_tiri.visibility = View.VISIBLE
        }
        initSpinner(spinner_pekerjaan_ayah_tiri, spinner_stts_hidup_ayah_tiri, sp_agama_ayah_tiri)
    }

    private fun initSpinner(
        spinnerPekerjaanAyah: AutoCompleteTextView,
        spinnerSttsHidupAyah: AutoCompleteTextView,
        spAgamaAyahTiri: AutoCompleteTextView
    ) {
        val item = listOf("Masih", "Tidak")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        spinnerPekerjaanAyah.setAdapter(adapter)
        spinnerPekerjaanAyah.setOnItemClickListener { _, _, position, _ ->
            if (position == 0) {
                txt_layout_nama_kantor_ayah_tiri.visibility = View.VISIBLE
                txt_layout_alamat_kantor_ayah_tiri.visibility = View.VISIBLE
                txt_layout_no_telp_kantor_ayah_tiri.visibility = View.VISIBLE
                txt_layout_thn_berhenti_ayah_tiri.visibility = View.GONE
                txt_layout_alsn_berhenti_ayah_tiri.visibility = View.GONE
                edt_thn_berhenti_ayah_tiri.text = null
                edt_alsn_berhenti_ayah_tiri.text = null
                stts_kerja = 1
            } else {
                txt_layout_nama_kantor_ayah_tiri.visibility = View.GONE
                txt_layout_alamat_kantor_ayah_tiri.visibility = View.GONE
                txt_layout_no_telp_kantor_ayah_tiri.visibility = View.GONE
                txt_layout_thn_berhenti_ayah_tiri.visibility = View.VISIBLE
                txt_layout_alsn_berhenti_ayah_tiri.visibility = View.VISIBLE

                edt_nama_kntr_ayah_tiri.text = null
                edt_almt_kntr_ayah_tiri.text = null
                edt_no_telp_kntr_ayah_tiri.text = null
                stts_kerja = 0
            }
        }
        val itemHidup = listOf("Masih", "Tidak")
        val adapterHidup = ArrayAdapter(this, R.layout.item_spinner, itemHidup)
        spinnerSttsHidupAyah.setAdapter(adapterHidup)
        spinnerSttsHidupAyah.setOnItemClickListener { _, _, position, _ ->
            if (position == 0) {
                txt_layout_penyebab_ayah_tiri.visibility = View.GONE
                txt_layout_bagaimana_stts_ayah_tiri.visibility = View.GONE
                txt_layout_dimana_ayah_tiri.visibility = View.GONE
                edt_penyebab_ayah_tiri.text = null
                edt_tahun_kematian_ayah_tiri.text = null
                edt_dimana_ayah_tiri.text = null
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
        spAgamaAyahTiri.setOnItemClickListener { _, _, position, _ ->
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
}