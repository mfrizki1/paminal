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
import kotlinx.android.synthetic.main.activity_add_ibu_kandung.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddIbuKandungActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var keluargaReq = KeluargaReq()
    var stts_kerja = 0
    var stts_hidup = 0
    var agama_skrg = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ibu_kandung)
        sessionManager1 = SessionManager1(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Ibu Kandung"
        initSpinner(spinner_pekerjaan_ibu, spinner_stts_hidup_ibu, sp_agama_ibu)

        btn_next_ibu.setOnClickListener {
            keluargaReq.nama = edt_nama_lngkp_ibu.text.toString()
            if (edt_nama_lngkp_ibu.text?.length!! > 1) {
                keluargaReq.status_hubungan = "ibu_kandung"
            }
            keluargaReq.nama_alias = edt_alias_ibu.text.toString()
            keluargaReq.tempat_lahir = edt_tmpt_ttl_ibu.text.toString()
            keluargaReq.tanggal_lahir = edt_tgl_ttl_ibu.text.toString()
            keluargaReq.agama = agama_skrg
            keluargaReq.aliran_kepercayaan_dianut = edt_aliran_dianut_ibu.text.toString()
            keluargaReq.ras = edt_suku_ibu.text.toString()
            keluargaReq.kewarganegaraan = edt_kwg_ibu.text.toString()
            keluargaReq.cara_peroleh_kewarganegaraan = edt_how_to_kwg_ibu.text.toString()
            keluargaReq.alamat_rumah = edt_almt_skrg_ibu.text.toString()
            keluargaReq.no_telp_rumah = edt_no_telp_ibu.text.toString()
            keluargaReq.alamat_rumah_sebelumnya = edt_almt_rmh_sblm_ibu.text.toString()

            keluargaReq.status_kerja = stts_kerja
            keluargaReq.pekerjaan_terakhir = edt_pekerjaan_ibu.text.toString()
            keluargaReq.alasan_pensiun = edt_alsn_berhenti_ibu.text.toString()
            keluargaReq.tahun_pensiun = edt_thn_berhenti_ibu.text.toString()

            keluargaReq.nama_kantor = edt_nama_almt_kntr_ibu.text.toString()
            keluargaReq.alamat_kantor = edt_almt_kntr_ibu.text.toString()
            keluargaReq.no_telp_kantor = edt_no_telp_kntr_ibu.text.toString()
            keluargaReq.pekerjaan_sebelumnya = edt_pekerjaan_sblm_ibu.text.toString()
            keluargaReq.pendidikan_terakhir = edt_pend_trkhr_ibu.text.toString()

            keluargaReq.status_kehidupan = stts_hidup
            keluargaReq.tahun_kematian = edt_tahun_kematian_ibu.text.toString()
            keluargaReq.lokasi_kematian = edt_dimana_ibu.text.toString()
            keluargaReq.sebab_kematian = edt_penyebab_ibu.text.toString()

            keluargaReq.kedudukan_organisasi_saat_ini = edt_kddkn_org_diikuti_ibu.text.toString()
            keluargaReq.tahun_bergabung_organisasi_saat_ini =
                edt_thn_org_diikuti_ibu.text.toString()
            keluargaReq.alasan_bergabung_organisasi_saat_ini =
                edt_alasan_org_diikuti_ibu.text.toString()
            keluargaReq.alamat_organisasi_saat_ini = edt_almt_org_diikuti_ibu.text.toString()
            keluargaReq.kedudukan_organisasi_sebelumnya = edt_kddkn_org_prnh_ibu.text.toString()
            keluargaReq.tahun_bergabung_organisasi_sebelumnya = edt_thn_org_prnh_ibu.text.toString()
            keluargaReq.alasan_bergabung_organisasi_sebelumnya =
                edt_alasan_org_prnh_ibu.text.toString()
            keluargaReq.alamat_organisasi_sebelumnya = edt_almt_org_prnh_ibu.text.toString()

            if (keluargaReq.nama != null) {
                sessionManager1.setIbu(keluargaReq)
            }else{
                sessionManager1.clearIbuKandung()
            }
            Log.e("ibu", "${sessionManager1.getIbu()}")
            startActivity(Intent(this, AddIbuTiriActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

    }

    private fun initSpinner(
        spinnerPekerjaanIbu: AutoCompleteTextView,
        spinnerSttsHidupIbu: AutoCompleteTextView,
        spAgamaIbu: AutoCompleteTextView
    ) {
        val item = listOf("Masih", "Tidak")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        spinnerPekerjaanIbu.setAdapter(adapter)
        spinnerPekerjaanIbu.setOnItemClickListener { _, _, position, _ ->
            if (position == 0) {
                txt_layout_nama_kantor_ibu.visibility = View.VISIBLE
                txt_layout_alamat_kantor_ibu.visibility = View.VISIBLE
                txt_layout_no_telp_kantor_ibu.visibility = View.VISIBLE
                txt_layout_alsn_berhenti_ibu.visibility = View.GONE
                txt_layout_thn_berhenti_ibu.visibility = View.GONE
                edt_thn_berhenti_ibu.text = null
                edt_alsn_berhenti_ibu.text = null
                stts_kerja = 1
            } else {
                txt_layout_nama_kantor_ibu.visibility = View.GONE
                txt_layout_alamat_kantor_ibu.visibility = View.GONE
                txt_layout_no_telp_kantor_ibu.visibility = View.GONE
                txt_layout_alsn_berhenti_ibu.visibility = View.VISIBLE
                txt_layout_thn_berhenti_ibu.visibility = View.VISIBLE
                edt_nama_almt_kntr_ibu.text = null
                edt_almt_kntr_ibu.text = null
                edt_no_telp_kntr_ibu.text = null
                stts_kerja = 0
            }
        }
        val itemHidup = listOf("Masih", "Tidak")
        val adapterHidup = ArrayAdapter(this, R.layout.item_spinner, itemHidup)
        spinnerSttsHidupIbu.setAdapter(adapterHidup)
        spinnerSttsHidupIbu.setOnItemClickListener { _, _, position, _ ->
            if (position == 0) {
                txt_layout_bagaimana_stts_ibu.visibility = View.GONE
                txt_layout_dimana_ibu.visibility = View.GONE
                txt_layout_penyebab_ibu.visibility = View.GONE
                stts_hidup = 1
                edt_penyebab_ibu.text = null
                edt_tahun_kematian_ibu.text = null
                edt_dimana_ibu.text = null
            } else {
                txt_layout_bagaimana_stts_ibu.visibility = View.VISIBLE
                txt_layout_dimana_ibu.visibility = View.VISIBLE
                txt_layout_penyebab_ibu.visibility = View.VISIBLE
                stts_hidup = 0


            }
//            stts_hidup = parent.getItemAtPosition(position).toString()
        }

        val agama = listOf("Islam", "Katolik", "Protestan", "Budha", "Hindu", "Konghuchu")
        val adapterAgama = ArrayAdapter(this, R.layout.item_spinner, agama)
        spAgamaIbu.setAdapter(adapterAgama)
        spAgamaIbu.setOnItemClickListener { _, _, position, _ ->
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
        test(sessionManager1.getIbu())
        initSpinner(spinner_pekerjaan_ibu, spinner_stts_hidup_ibu, sp_agama_ibu)
    }

    @SuppressLint("SetTextI18n")
    private fun test(ibu: KeluargaReq) {
        edt_nama_lngkp_ibu.setText(ibu.nama)
        edt_alias_ibu.setText(ibu.nama_alias)
        edt_tmpt_ttl_ibu.setText(ibu.tempat_lahir)
        edt_tgl_ttl_ibu.setText(ibu.tanggal_lahir)
//        sp_agama_ibu.setText(ibu.agama)
        edt_aliran_dianut_ibu.setText(ibu.aliran_kepercayaan_dianut)
        edt_suku_ibu.setText(ibu.ras)
        edt_kwg_ibu.setText(ibu.kewarganegaraan)
        edt_how_to_kwg_ibu.setText(ibu.cara_peroleh_kewarganegaraan)
        edt_almt_skrg_ibu.setText(ibu.alamat_rumah)
        edt_no_telp_ibu.setText(ibu.no_telp_rumah)
        edt_almt_rmh_sblm_ibu.setText(ibu.alamat_rumah_sebelumnya)
        edt_pekerjaan_ibu.setText(ibu.pekerjaan_terakhir)
        edt_almt_kntr_ibu.setText(ibu.alamat_kantor)
        edt_no_telp_kntr_ibu.setText(ibu.no_telp_kantor)
        edt_pekerjaan_sblm_ibu.setText(ibu.pekerjaan_sebelumnya)
        edt_pend_trkhr_ibu.setText(ibu.pendidikan_terakhir)
        edt_kddkn_org_diikuti_ibu.setText(ibu.kedudukan_organisasi_saat_ini)
        edt_thn_org_diikuti_ibu.setText(ibu.tahun_bergabung_organisasi_saat_ini)
        edt_alasan_org_diikuti_ibu.setText(ibu.alasan_bergabung_organisasi_saat_ini)
        edt_almt_org_diikuti_ibu.setText(ibu.alamat_organisasi_saat_ini)
        edt_kddkn_org_prnh_ibu.setText(ibu.kedudukan_organisasi_sebelumnya)
        edt_thn_org_prnh_ibu.setText(ibu.tahun_bergabung_organisasi_sebelumnya)
        edt_alasan_org_prnh_ibu.setText(ibu.alasan_bergabung_organisasi_sebelumnya)
        edt_almt_org_prnh_ibu.setText(ibu.alamat_organisasi_sebelumnya)
        edt_tahun_kematian_ibu.setText(ibu.tahun_kematian)
        edt_dimana_ibu.setText(ibu.lokasi_kematian)
        edt_penyebab_ibu.setText(ibu.sebab_kematian)
        edt_nama_almt_kntr_ibu.setText(ibu.nama_kantor)


        agama_skrg = ibu.agama.toString()
        sp_agama_ibu.setText(ibu.agama) /**/
        stts_kerja = ibu.status_kerja!!
        stts_hidup = ibu.status_kehidupan!!
        if (ibu.status_kerja == 1) {
            txt_layout_nama_kantor_ibu.visibility = View.VISIBLE
            txt_layout_alamat_kantor_ibu.visibility = View.VISIBLE
            txt_layout_no_telp_kantor_ibu.visibility = View.VISIBLE
            txt_layout_alsn_berhenti_ibu.visibility = View.GONE
            txt_layout_thn_berhenti_ibu.visibility = View.GONE
            spinner_pekerjaan_ibu.setText("Masih")
        } else {
            txt_layout_nama_kantor_ibu.visibility = View.GONE
            txt_layout_alamat_kantor_ibu.visibility = View.GONE
            txt_layout_no_telp_kantor_ibu.visibility = View.GONE
            txt_layout_alsn_berhenti_ibu.visibility = View.VISIBLE
            txt_layout_thn_berhenti_ibu.visibility = View.VISIBLE
            spinner_pekerjaan_ibu.setText("Tidak")
        }
        if (ibu.status_kehidupan == 1) {
            txt_layout_penyebab_ibu.visibility = View.GONE
            txt_layout_bagaimana_stts_ibu.visibility = View.GONE
            txt_layout_dimana_ibu.visibility = View.GONE
            spinner_stts_hidup_ibu.setText("Masih")
        } else {
            txt_layout_penyebab_ibu.visibility = View.VISIBLE
            txt_layout_bagaimana_stts_ibu.visibility = View.VISIBLE
            txt_layout_dimana_ibu.visibility = View.VISIBLE
            spinner_stts_hidup_ibu.setText("Tidak")
        }
        edt_thn_berhenti_ibu.setText(ibu.tahun_pensiun)
        edt_alsn_berhenti_ibu.setText(ibu.alasan_pensiun)

    }
}