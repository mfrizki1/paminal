package id.calocallo.sicape.ui.main.addpersonal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.KeluargaReq
import id.calocallo.sicape.ui.main.addpersonal.anak.AddAnakActivity
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_mertua_laki.*
import kotlinx.android.synthetic.main.activity_add_mertua_perempuan.*
import kotlinx.android.synthetic.main.activity_add_mertua_perempuan.*
import kotlinx.android.synthetic.main.activity_add_mertua_perempuan.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddMertuaPerempuanActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1

    private var keluargaReq =
        KeluargaReq()
    var stts_kerja = 0
    var stts_hidup = 0
    var agama_skrg = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_mertua_perempuan)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Mertua Perempuan"
        sessionManager1 = SessionManager1(this)

        initSpinner()

        btn_next_mertua_perempuan.setOnClickListener {
            keluargaReq.nama = edt_nama_lngkp_mertua_perempuan.text.toString()
            if (edt_nama_lngkp_mertua_perempuan.text?.length!! > 1) {
                keluargaReq.status_hubungan = "mertua_perempuan"
            }
            keluargaReq.nama_alias = edt_alias_mertua_perempuan.text.toString()
            keluargaReq.tempat_lahir = edt_tmpt_ttl_mertua_perempuan.text.toString()
            keluargaReq.tanggal_lahir = edt_tgl_ttl_mertua_perempuan.text.toString()
            keluargaReq.agama = agama_skrg
            keluargaReq.aliran_kepercayaan_dianut =
                edt_aliran_dianut_mertua_perempuan.text.toString()
            keluargaReq.ras = edt_suku_mertua_perempuan.text.toString()
            keluargaReq.kewarganegaraan = edt_kwg_mertua_perempuan.text.toString()
            keluargaReq.cara_peroleh_kewarganegaraan =
                edt_how_to_kwg_mertua_perempuan.text.toString()
            keluargaReq.alamat_rumah = edt_almt_skrg_mertua_perempuan.text.toString()
            keluargaReq.no_telp_rumah = edt_no_telp_mertua_perempuan.text.toString()
            keluargaReq.alamat_rumah_sebelumnya = edt_almt_rmh_sblm_mertua_perempuan.text.toString()

            keluargaReq.status_kerja = stts_kerja
            keluargaReq.pekerjaan_terakhir = edt_pekerjaan_mertua_perempuan.text.toString()
            keluargaReq.alasan_pensiun = edt_alsn_berhenti_mertua_perempuan.text.toString()
            keluargaReq.tahun_pensiun = edt_thn_berhenti_mertua_perempuan.text.toString()

            keluargaReq.nama_kantor = edt_nama_almt_kntr_mertua_perempuan.text.toString()
            keluargaReq.alamat_kantor = edt_almt_kntr_mertua_perempuan.text.toString()
            keluargaReq.no_telp_kantor = edt_no_telp_kntr_mertua_perempuan.text.toString()
            keluargaReq.pekerjaan_sebelumnya = edt_pekerjaan_sblm_mertua_perempuan.text.toString()
            keluargaReq.pendidikan_terakhir = edt_pend_trkhr_mertua_perempuan.text.toString()

            keluargaReq.kedudukan_organisasi_saat_ini =
                edt_kddkn_org_prnh_mertua_perempuan.text.toString()
            keluargaReq.tahun_bergabung_organisasi_saat_ini =
                edt_thn_org_diikuti_mertua_perempuan.text.toString()
            keluargaReq.alasan_bergabung_organisasi_saat_ini =
                edt_alasan_org_diikuti_mertua_perempuan.text.toString()
            keluargaReq.alamat_organisasi_saat_ini =
                edt_almt_org_diikuti_mertua_perempuan.text.toString()
            keluargaReq.kedudukan_organisasi_sebelumnya =
                edt_kddkn_org_prnh_mertua_perempuan.text.toString()
            keluargaReq.tahun_bergabung_organisasi_sebelumnya =
                edt_thn_org_prnh_mertua_perempuan.text.toString()
            keluargaReq.alasan_bergabung_organisasi_sebelumnya =
                edt_alasan_org_prnh_mertua_perempuan.text.toString()
            keluargaReq.alamat_organisasi_sebelumnya =
                edt_almt_org_prnh_mertua_perempuan.text.toString()


            keluargaReq.status_kehidupan = stts_hidup
            keluargaReq.tahun_kematian = edt_tahun_kematian_mertua_perempuan.text.toString()
            keluargaReq.lokasi_kematian = edt_dimana_mertua_perempuan.text.toString()
            keluargaReq.sebab_kematian = edt_penyebab_mertua_perempuan.text.toString()

            if (keluargaReq.nama != null) {
                sessionManager1.setMertuaPerempuan(keluargaReq)
            }else{
                sessionManager1.clearMertuaPerempuan()
            }
            Log.e("mertuaPerempuan", "${sessionManager1.getMertuaPerempuan()}")
            startActivity(Intent(this, AddAnakActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun initSpinner() {
        val item = listOf("Masih", "Tidak")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        spinner_pekerjaan_mertua_perempuan.setAdapter(adapter)
        spinner_pekerjaan_mertua_perempuan.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                txt_layout_nama_kantor_mertua_perempuan.visibility = View.VISIBLE
                txt_layout_alamat_kantor_mertua_perempuan.visibility = View.VISIBLE
                txt_layout_no_telp_kantor_mertua_perempuan.visibility = View.VISIBLE
                txt_layout_thn_berhenti_mertua_perempuan.visibility = View.GONE
                txt_layout_alsn_berhenti_mertua_perempuan.visibility = View.GONE
                stts_kerja = 1
                edt_thn_berhenti_mertua_perempuan.text = null
                edt_alsn_berhenti_mertua_perempuan.text = null
            } else {
                txt_layout_pekerjaan_mertua_perempuan.visibility = View.GONE
                txt_layout_nama_kantor_mertua_perempuan.visibility = View.GONE
                txt_layout_alamat_kantor_mertua_perempuan.visibility = View.GONE
                txt_layout_no_telp_kantor_mertua_perempuan.visibility = View.GONE
                txt_layout_thn_berhenti_mertua_perempuan.visibility = View.VISIBLE
                txt_layout_alsn_berhenti_mertua_perempuan.visibility = View.VISIBLE
                stts_kerja = 0
                edt_nama_almt_kntr_mertua_perempuan.text = null
                edt_almt_kntr_mertua_perempuan.text = null
                edt_no_telp_kntr_mertua_perempuan.text = null
            }
        }
        val itemHidup = listOf("Masih", "Tidak")
        val adapterHidup = ArrayAdapter(this, R.layout.item_spinner, itemHidup)
        spinner_stts_hidup_mertua_perempuan.setAdapter(adapterHidup)
        spinner_stts_hidup_mertua_perempuan.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                txt_layout_penyebab_mertua_perempuan.visibility = View.GONE
                txt_layout_dimana_mertua_perempuan.visibility = View.GONE
                txt_layout_bagaimana_stts_mertua_perempuan.visibility = View.GONE
                stts_hidup = 1
                edt_penyebab_mertua_perempuan.text = null
                edt_tahun_kematian_mertua_perempuan.text = null
                edt_dimana_mertua_perempuan.text = null
            } else {
                txt_layout_dimana_mertua_perempuan.visibility = View.VISIBLE
                txt_layout_bagaimana_stts_mertua_perempuan.visibility = View.VISIBLE
                stts_hidup = 0
            }
        }

        val agama = listOf("Islam", "Katolik", "Protestan", "Budha", "Hindu", "Konghuchu")
        val adapterAgama = ArrayAdapter(this, R.layout.item_spinner, agama)
        sp_agama_mertua_perempuan.setAdapter(adapterAgama)
        sp_agama_mertua_perempuan.setOnItemClickListener { parent, view, position, id ->
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

    override fun onResume() {
        super.onResume()
        test(sessionManager1.getMertuaPerempuan())
        initSpinner()
    }

    private fun test(mertua2: KeluargaReq) {
        edt_nama_lngkp_mertua_perempuan.setText(mertua2.nama)
        edt_alias_mertua_perempuan.setText(mertua2.nama_alias)
        edt_tmpt_ttl_mertua_perempuan.setText(mertua2.tempat_lahir)
        edt_tgl_ttl_mertua_perempuan.setText(mertua2.tanggal_lahir)
        sp_agama_mertua_perempuan.setText(mertua2.agama)
        edt_aliran_dianut_mertua_perempuan.setText(mertua2.aliran_kepercayaan_dianut)
        edt_suku_mertua_perempuan.setText(mertua2.ras)
        edt_kwg_mertua_perempuan.setText(mertua2.kewarganegaraan)
        edt_how_to_kwg_mertua_perempuan.setText(mertua2.cara_peroleh_kewarganegaraan)
        edt_almt_skrg_mertua_perempuan.setText(mertua2.alamat_rumah)
        edt_no_telp_mertua_perempuan.setText(mertua2.no_telp_rumah)
        edt_almt_rmh_sblm_mertua_perempuan.setText(mertua2.alamat_rumah_sebelumnya)
        edt_pekerjaan_mertua_perempuan.setText(mertua2.pekerjaan_terakhir)
        edt_nama_almt_kntr_mertua_perempuan.setText(mertua2.nama_kantor)
        edt_almt_kntr_mertua_perempuan.setText(mertua2.alamat_kantor)
        edt_no_telp_kntr_mertua_perempuan.setText(mertua2.no_telp_kantor)
        edt_pekerjaan_sblm_mertua_perempuan.setText(mertua2.pekerjaan_sebelumnya)
        edt_pend_trkhr_mertua_perempuan.setText(mertua2.pendidikan_terakhir)
        edt_kddkn_org_diikuti_mertua_perempuan.setText(mertua2.kedudukan_organisasi_saat_ini)
        edt_thn_org_diikuti_mertua_perempuan.setText(mertua2.tahun_bergabung_organisasi_saat_ini)
        edt_alasan_org_diikuti_mertua_perempuan.setText(mertua2.alasan_bergabung_organisasi_saat_ini)
        edt_almt_org_diikuti_mertua_perempuan.setText(mertua2.alamat_organisasi_saat_ini)
        edt_kddkn_org_prnh_mertua_perempuan.setText(mertua2.kedudukan_organisasi_sebelumnya)
        edt_thn_org_prnh_mertua_perempuan.setText(mertua2.tahun_bergabung_organisasi_sebelumnya)
        edt_alasan_org_prnh_mertua_perempuan.setText(mertua2.alasan_bergabung_organisasi_sebelumnya)
        edt_almt_org_prnh_mertua_perempuan.setText(mertua2.alamat_organisasi_sebelumnya)
        edt_tahun_kematian_mertua_perempuan.setText(mertua2.tahun_kematian)
        edt_dimana_mertua_perempuan.setText(mertua2.lokasi_kematian)
        edt_penyebab_mertua_perempuan.setText(mertua2.sebab_kematian)

        edt_kddkn_org_prnh_mertua_perempuan.setText(mertua2.kedudukan_organisasi_saat_ini)
        edt_thn_org_diikuti_mertua_perempuan.setText(mertua2.tahun_bergabung_organisasi_saat_ini)
        edt_alasan_org_diikuti_mertua_perempuan.setText(mertua2.alasan_bergabung_organisasi_saat_ini)
        edt_almt_org_diikuti_mertua_perempuan.setText(mertua2.alamat_organisasi_saat_ini)
        edt_kddkn_org_prnh_mertua_perempuan.setText(mertua2.kedudukan_organisasi_sebelumnya)
        edt_thn_org_prnh_mertua_perempuan.setText(mertua2.tahun_bergabung_organisasi_sebelumnya)
        edt_alasan_org_prnh_mertua_perempuan.setText(mertua2.alasan_bergabung_organisasi_sebelumnya)
        edt_almt_org_prnh_mertua_perempuan.setText(mertua2.alamat_rumah_sebelumnya)

        agama_skrg = mertua2.agama.toString()
        sp_agama_mertua_perempuan.setText(mertua2.agama) /**/

        stts_kerja = mertua2.status_kerja!!
        stts_hidup = mertua2.status_kehidupan!!
        if (mertua2.status_kerja == 1) {
            txt_layout_nama_kantor_mertua_perempuan.visibility = View.VISIBLE
            txt_layout_alamat_kantor_mertua_perempuan.visibility = View.VISIBLE
            txt_layout_no_telp_kantor_mertua_perempuan.visibility = View.VISIBLE
            txt_layout_alsn_berhenti_mertua_perempuan.visibility = View.GONE
            txt_layout_thn_berhenti_mertua_perempuan.visibility = View.GONE
            spinner_pekerjaan_mertua_perempuan.setText("Masih")
        } else {
            txt_layout_nama_kantor_mertua_perempuan.visibility = View.GONE
            txt_layout_alamat_kantor_mertua_perempuan.visibility = View.GONE
            txt_layout_no_telp_kantor_mertua_perempuan.visibility = View.GONE
            txt_layout_alsn_berhenti_mertua_perempuan.visibility = View.VISIBLE
            txt_layout_thn_berhenti_mertua_perempuan.visibility = View.VISIBLE
            spinner_pekerjaan_mertua_perempuan.setText("Tidak")
        }
        if (mertua2.status_kehidupan == 1) {
            txt_layout_penyebab_mertua_perempuan.visibility = View.GONE
            txt_layout_bagaimana_stts_mertua_perempuan.visibility = View.GONE
            txt_layout_dimana_mertua_perempuan.visibility = View.GONE
            spinner_stts_hidup_mertua_perempuan.setText("Masih")
        } else {
            txt_layout_penyebab_mertua_perempuan.visibility = View.VISIBLE
            txt_layout_bagaimana_stts_mertua_perempuan.visibility = View.VISIBLE
            txt_layout_dimana_mertua_perempuan.visibility = View.VISIBLE
            spinner_stts_hidup_mertua_perempuan.setText("Tidak")
        }
        edt_thn_berhenti_mertua_perempuan.setText(mertua2.tahun_pensiun)
        edt_alsn_berhenti_mertua_perempuan.setText(mertua2.alasan_pensiun)
    }

}