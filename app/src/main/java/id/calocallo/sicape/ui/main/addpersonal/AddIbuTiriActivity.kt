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
import kotlinx.android.synthetic.main.activity_add_ayah_tiri.*
import kotlinx.android.synthetic.main.activity_add_ibu_kandung.*
import kotlinx.android.synthetic.main.activity_add_ibu_tiri.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddIbuTiriActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var keluargaReq = KeluargaReq()
    var stts_kerja = 0
    var stts_hidup = 0
    var agama_skrg = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ibu_tiri)
        sessionManager1 = SessionManager1(this)

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
            keluargaReq.nama = edt_nama_lngkp_ibu_tiri.text.toString()
            if (edt_nama_lngkp_ibu_tiri.text?.length!! > 1) {
                keluargaReq.status_hubungan = "ibu_tiri"
            }
            keluargaReq.nama_alias = edt_alias_ibu_tiri.text.toString()
            keluargaReq.tempat_lahir = edt_tmpt_ttl_ibu_tiri.text.toString()
            keluargaReq.tanggal_lahir = edt_tgl_ttl_ibu_tiri.text.toString()
            keluargaReq.agama = agama_skrg
            keluargaReq.aliran_kepercayaan_dianut = edt_aliran_dianut_ibu_tiri.text.toString()
            keluargaReq.ras = edt_suku_ibu_tiri.text.toString()
            keluargaReq.kewarganegaraan = edt_kwg_ibu_tiri.text.toString()
            keluargaReq.cara_peroleh_kewarganegaraan = edt_how_to_kwg_ibu_tiri.text.toString()
            keluargaReq.alamat_rumah = edt_almt_skrg_ibu_tiri.text.toString()
            keluargaReq.no_telp_rumah = edt_no_telp_ibu_tiri.text.toString()
            keluargaReq.alamat_rumah_sebelumnya = edt_almt_rmh_sblm_ibu_tiri.text.toString()

            keluargaReq.status_kerja = stts_kerja
            keluargaReq.pekerjaan_terakhir = edt_pekerjaan_ibu_tiri.text.toString()
            keluargaReq.alasan_pensiun = edt_alsn_berhenti_ibu_tiri.text.toString()
            keluargaReq.tahun_pensiun = edt_thn_berhenti_ibu_tiri.text.toString()

            keluargaReq.nama_kantor = edt_nama_almt_kntr_ibu_tiri.text.toString()
            keluargaReq.alamat_kantor = edt_almt_kntr_ibu_tiri.text.toString()
            keluargaReq.no_telp_kantor = edt_no_telp_kntr_ibu_tiri.text.toString()
            keluargaReq.pekerjaan_sebelumnya = edt_pekerjaan_sblm_ibu_tiri.text.toString()
            keluargaReq.pendidikan_terakhir = edt_pend_trkhr_ibu_tiri.text.toString()

            keluargaReq.status_kehidupan = stts_hidup
            keluargaReq.tahun_kematian = edt_tahun_kematian_ibu_tiri.text.toString()
            keluargaReq.lokasi_kematian = edt_dimana_ibu_tiri.text.toString()
            keluargaReq.sebab_kematian = edt_penyebab_ibu_tiri.text.toString()

            keluargaReq.kedudukan_organisasi_saat_ini = edt_kddkn_org_prnh_ibu_tiri.text.toString()
            keluargaReq.tahun_bergabung_organisasi_saat_ini =
                edt_thn_org_diikuti_ibu_tiri.text.toString()
            keluargaReq.alasan_bergabung_organisasi_saat_ini =
                edt_alasan_org_diikuti_ibu_tiri.text.toString()
            keluargaReq.alamat_organisasi_saat_ini = edt_almt_org_diikuti_ibu_tiri.text.toString()
            keluargaReq.kedudukan_organisasi_sebelumnya =
                edt_kddkn_org_prnh_ibu_tiri.text.toString()
            keluargaReq.tahun_bergabung_organisasi_sebelumnya =
                edt_thn_org_prnh_ibu_tiri.text.toString()
            keluargaReq.alasan_bergabung_organisasi_sebelumnya =
                edt_alasan_org_prnh_ibu_tiri.text.toString()
            keluargaReq.alamat_organisasi_sebelumnya = edt_almt_org_prnh_ibu_tiri.text.toString()

            if (keluargaReq.nama != null) {
                sessionManager1.setIbuTiri(keluargaReq)
            }else{
                sessionManager1.clearIbuTiri()
            }
            Log.e("ibutTiri", "${sessionManager1.getIbuTiri()}")
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
                edt_thn_berhenti_ibu_tiri.text = null
                edt_alsn_berhenti_ibu_tiri.text = null
            } else {
                txt_layout_nama_kantor_ibu_tiri.visibility = View.GONE
                txt_layout_alamat_kantor_ibu_tiri.visibility = View.GONE
                txt_layout_no_telp_kantor_ibu_tiri.visibility = View.GONE
                txt_layout_thn_berhenti_ibu_tiri.visibility = View.VISIBLE
                txt_layout_alsn_berhenti_ibu_tiri.visibility = View.VISIBLE
                stts_kerja = 0
                edt_nama_almt_kntr_ibu_tiri.text = null
                edt_almt_kntr_ibu_tiri.text = null
                edt_no_telp_kntr_ibu_tiri.text = null
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
                edt_penyebab_ibu_tiri.text = null
                edt_tahun_kematian_ibu_tiri.text = null
                edt_dimana_ibu_tiri.text = null
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
        test(sessionManager1.getIbuTiri())
        initSpinner(spinner_pekerjaan_ibu_tiri, spinner_stts_hidup_ibu_tiri, sp_agama_ibu_tiri)
    }

    private fun test(ibuTiri: KeluargaReq) {
        edt_nama_lngkp_ibu_tiri.setText(ibuTiri.nama)
        edt_alias_ibu_tiri.setText(ibuTiri.nama_alias)
        edt_tmpt_ttl_ibu_tiri.setText(ibuTiri.tempat_lahir)
        edt_tgl_ttl_ibu_tiri.setText(ibuTiri.tanggal_lahir)
        sp_agama_ibu_tiri.setText(ibuTiri.agama)
        edt_aliran_dianut_ibu_tiri.setText(ibuTiri.aliran_kepercayaan_dianut)
        edt_suku_ibu_tiri.setText(ibuTiri.ras)
        edt_kwg_ibu_tiri.setText(ibuTiri.kewarganegaraan)
        edt_how_to_kwg_ibu_tiri.setText(ibuTiri.cara_peroleh_kewarganegaraan)
        edt_almt_skrg_ibu_tiri.setText(ibuTiri.alamat_rumah)
        edt_no_telp_ibu_tiri.setText(ibuTiri.no_telp_rumah)
        edt_almt_rmh_sblm_ibu_tiri.setText(ibuTiri.alamat_rumah_sebelumnya)
        edt_pekerjaan_ibu_tiri.setText(ibuTiri.pekerjaan_terakhir)
        edt_almt_kntr_ibu_tiri.setText(ibuTiri.alamat_kantor)
        edt_no_telp_kntr_ibu_tiri.setText(ibuTiri.no_telp_kantor)
        edt_pekerjaan_sblm_ibu_tiri.setText(ibuTiri.pekerjaan_sebelumnya)
        edt_pend_trkhr_ibu_tiri.setText(ibuTiri.pendidikan_terakhir)
        edt_kddkn_org_diikuti_ibu_tiri.setText(ibuTiri.kedudukan_organisasi_saat_ini)
        edt_thn_org_diikuti_ibu_tiri.setText(ibuTiri.tahun_bergabung_organisasi_saat_ini)
        edt_alasan_org_diikuti_ibu_tiri.setText(ibuTiri.alasan_bergabung_organisasi_saat_ini)
        edt_almt_org_diikuti_ibu_tiri.setText(ibuTiri.alamat_organisasi_saat_ini)
        edt_kddkn_org_prnh_ibu_tiri.setText(ibuTiri.kedudukan_organisasi_sebelumnya)
        edt_thn_org_prnh_ibu_tiri.setText(ibuTiri.tahun_bergabung_organisasi_sebelumnya)
        edt_alasan_org_prnh_ibu_tiri.setText(ibuTiri.alasan_bergabung_organisasi_sebelumnya)
        edt_almt_org_prnh_ibu_tiri.setText(ibuTiri.alamat_organisasi_sebelumnya)
        edt_tahun_kematian_ibu_tiri.setText(ibuTiri.tahun_kematian)
        edt_dimana_ibu_tiri.setText(ibuTiri.lokasi_kematian)
        edt_penyebab_ibu_tiri.setText(ibuTiri.sebab_kematian)
        edt_nama_almt_kntr_ibu_tiri.setText(ibuTiri.nama_kantor)


        edt_kddkn_org_prnh_ibu_tiri.setText(ibuTiri.kedudukan_organisasi_saat_ini)
        edt_thn_org_diikuti_ibu_tiri.setText(ibuTiri.tahun_bergabung_organisasi_saat_ini)
        edt_alasan_org_diikuti_ibu_tiri.setText(ibuTiri.alasan_bergabung_organisasi_saat_ini)
        edt_almt_org_diikuti_ibu_tiri.setText(ibuTiri.alamat_organisasi_saat_ini)
        edt_kddkn_org_prnh_ibu_tiri.setText(ibuTiri.kedudukan_organisasi_sebelumnya)
        edt_thn_org_prnh_ibu_tiri.setText(ibuTiri.tahun_bergabung_organisasi_sebelumnya)
        edt_alasan_org_prnh_ibu_tiri.setText(ibuTiri.alasan_bergabung_organisasi_sebelumnya)
        edt_almt_org_prnh_ibu_tiri.setText(ibuTiri.alamat_rumah_sebelumnya)

        agama_skrg = ibuTiri.agama.toString()
        sp_agama_ibu_tiri.setText(ibuTiri.agama) /**/

        stts_kerja = ibuTiri.status_kerja!!
        stts_hidup = ibuTiri.status_kehidupan!!
        if (ibuTiri.status_kerja == 1) {
            txt_layout_nama_kantor_ibu_tiri.visibility = View.VISIBLE
            txt_layout_alamat_kantor_ibu_tiri.visibility = View.VISIBLE
            txt_layout_no_telp_kantor_ibu_tiri.visibility = View.VISIBLE
            txt_layout_alsn_berhenti_ibu_tiri.visibility = View.GONE
            txt_layout_thn_berhenti_ibu_tiri.visibility = View.GONE
            spinner_pekerjaan_ibu_tiri.setText("Masih")
        } else {
            txt_layout_nama_kantor_ibu_tiri.visibility = View.GONE
            txt_layout_alamat_kantor_ibu_tiri.visibility = View.GONE
            txt_layout_no_telp_kantor_ibu_tiri.visibility = View.GONE
            txt_layout_alsn_berhenti_ibu_tiri.visibility = View.VISIBLE
            txt_layout_thn_berhenti_ibu_tiri.visibility = View.VISIBLE
            spinner_pekerjaan_ibu_tiri.setText("Tidak")
        }
        if (ibuTiri.status_kehidupan == 1) {
            txt_layout_penyebab_ibu_tiri.visibility = View.GONE
            txt_layout_bagaimana_stts_ibu_tiri.visibility = View.GONE
            txt_layout_dimana_ibu_tiri.visibility = View.GONE
            spinner_stts_hidup_ibu_tiri.setText("Masih")
        } else {
            txt_layout_penyebab_ibu_tiri.visibility = View.VISIBLE
            txt_layout_bagaimana_stts_ibu_tiri.visibility = View.VISIBLE
            txt_layout_dimana_ibu_tiri.visibility = View.VISIBLE
            spinner_stts_hidup_ibu_tiri.setText("Tidak")
        }
        edt_thn_berhenti_ibu_tiri.setText(ibuTiri.tahun_pensiun)
        edt_alsn_berhenti_ibu_tiri.setText(ibuTiri.alasan_pensiun)


    }
}