package id.calocallo.sicape.ui.main.addpersonal

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.KeluargaReq
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_mertua_laki.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddMertuaLakiActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var keluargaReq = KeluargaReq()
    var stts_hidup = 0
    var stts_kerja = 0
    var agama_skrg = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_mertua_laki)
        sessionManager1 = SessionManager1(this)

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Mertua Laki"
        initSpinner()

        btn_next_mertua_laki.setOnClickListener {
            keluargaReq.nama = edt_nama_lngkp_mertua_laki.text.toString()
            if (edt_nama_lngkp_mertua_laki.text?.length!! > 1) {
                keluargaReq.status_hubungan = "mertua_laki_laki"
            }
            keluargaReq.nama_alias = edt_alias_mertua_laki.text.toString()
            keluargaReq.tempat_lahir = edt_tmpt_ttl_mertua_laki.text.toString()
            keluargaReq.tanggal_lahir = edt_tgl_ttl_mertua_laki.text.toString()
            keluargaReq.agama = agama_skrg
            keluargaReq.aliran_kepercayaan_dianut = edt_aliran_dianut_mertua_laki.text.toString()
            keluargaReq.ras = edt_suku_mertua_laki.text.toString()
            keluargaReq.kewarganegaraan = edt_kwg_mertua_laki.text.toString()
            keluargaReq.cara_peroleh_kewarganegaraan = edt_how_to_kwg_mertua_laki.text.toString()
            keluargaReq.alamat_rumah = edt_almt_skrg_mertua_laki.text.toString()
            keluargaReq.no_telp_rumah = edt_no_telp_mertua_laki.text.toString()
            keluargaReq.alamat_rumah_sebelumnya = edt_almt_rmh_sblm_mertua_laki.text.toString()

            keluargaReq.status_kerja = stts_kerja
            keluargaReq.pekerjaan_terakhir = edt_pekerjaan_mertua_laki.text.toString()
            keluargaReq.alasan_pensiun = edt_alsn_berhenti_mertua_laki.text.toString()
            keluargaReq.tahun_pensiun = edt_thn_berhenti_mertua_laki.text.toString()

            keluargaReq.nama_kantor = edt_nama_almt_kntr_mertua_laki.text.toString()
            keluargaReq.alamat_kantor = edt_almt_kntr_mertua_laki.text.toString()
            keluargaReq.no_telp_kantor = edt_no_telp_kntr_mertua_laki.text.toString()
            keluargaReq.pekerjaan_sebelumnya = edt_pekerjaan_sblm_mertua_laki.text.toString()
            keluargaReq.pendidikan_terakhir = edt_pend_trkhr_mertua_laki.text.toString()

            keluargaReq.kedudukan_organisasi_saat_ini =
                edt_kddkn_org_prnh_mertua_laki.text.toString()
            keluargaReq.tahun_bergabung_organisasi_saat_ini =
                edt_thn_org_diikuti_mertua_laki.text.toString()
            keluargaReq.alasan_bergabung_organisasi_saat_ini =
                edt_alasan_org_diikuti_mertua_laki.text.toString()
            keluargaReq.alamat_organisasi_saat_ini =
                edt_almt_org_diikuti_mertua_laki.text.toString()
            keluargaReq.kedudukan_organisasi_sebelumnya =
                edt_kddkn_org_prnh_mertua_laki.text.toString()
            keluargaReq.tahun_bergabung_organisasi_sebelumnya =
                edt_thn_org_prnh_mertua_laki.text.toString()
            keluargaReq.alasan_bergabung_organisasi_sebelumnya =
                edt_alasan_org_prnh_mertua_laki.text.toString()
            keluargaReq.alamat_organisasi_sebelumnya = edt_almt_org_prnh_mertua_laki.text.toString()


            keluargaReq.status_kehidupan = stts_hidup
            keluargaReq.tahun_kematian = edt_tahun_kematian_mertua_laki.text.toString()
            keluargaReq.lokasi_kematian = edt_dimana_mertua_laki.text.toString()
            keluargaReq.sebab_kematian = edt_penyebab_mertua_laki.text.toString()

            if (keluargaReq.nama != null) {
                sessionManager1.setMertuaLaki(keluargaReq)
            }else{
                sessionManager1.clearMertuaLaki()
            }
            Log.e("mertuaLaki", "${sessionManager1.getMertuaLaki()}")
            startActivity(Intent(this, AddMertuaPerempuanActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun initSpinner() {
        val item = listOf("Masih", "Tidak")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        spinner_pekerjaan_mertua_laki.setAdapter(adapter)
        spinner_pekerjaan_mertua_laki.setOnItemClickListener { _, _, position, _ ->
            if (position == 0) {
                txt_layout_nama_kantor_mertua_laki.visibility = View.VISIBLE
                txt_layout_alamat_kantor_mertua_laki.visibility = View.VISIBLE
                txt_layout_no_telp_kantor_mertua_laki.visibility = View.VISIBLE
                txt_layout_thn_berhenti_mertua_laki.visibility = View.GONE
                txt_layout_alsn_berhenti_mertua_laki.visibility = View.GONE
                stts_kerja = 1
                edt_thn_berhenti_mertua_laki.text = null
                edt_alsn_berhenti_mertua_laki.text = null
            } else {
                txt_layout_nama_kantor_mertua_laki.visibility = View.GONE
                txt_layout_alamat_kantor_mertua_laki.visibility = View.GONE
                txt_layout_no_telp_kantor_mertua_laki.visibility = View.GONE
                txt_layout_thn_berhenti_mertua_laki.visibility = View.VISIBLE
                txt_layout_alsn_berhenti_mertua_laki.visibility = View.VISIBLE
                stts_kerja = 0
                edt_nama_almt_kntr_mertua_laki.text = null
                edt_almt_kntr_mertua_laki.text = null
                edt_no_telp_kntr_mertua_laki.text = null
            }
        }
        val itemHidup = listOf("Masih", "Tidak")
        val adapterHidup = ArrayAdapter(this, R.layout.item_spinner, itemHidup)
        spinner_stts_hidup_mertua_laki.setAdapter(adapterHidup)
        spinner_stts_hidup_mertua_laki.setOnItemClickListener { _, _, position, _ ->
            if (position == 0) {
                txt_layout_penyebab_mertua_laki.visibility = View.GONE
                txt_layout_dimana_mertua_laki.visibility = View.GONE
                txt_layout_bagaimana_stts_mertua_laki.visibility = View.GONE
                stts_hidup = 1
                edt_penyebab_mertua_laki.text = null
                edt_tahun_kematian_mertua_laki.text = null
                edt_dimana_mertua_laki.text = null
            } else {
                txt_layout_penyebab_mertua_laki.visibility = View.VISIBLE
                txt_layout_dimana_mertua_laki.visibility = View.VISIBLE
                txt_layout_bagaimana_stts_mertua_laki.visibility = View.VISIBLE
                stts_hidup = 0
            }
        }

        val agama = listOf("Islam", "Katolik", "Protestan", "Budha", "Hindu", "Konghuchu")
        val adapterAgama = ArrayAdapter(this, R.layout.item_spinner, agama)
        sp_agama_mertua_laki.setAdapter(adapterAgama)
        sp_agama_mertua_laki.setOnItemClickListener { _, _, position, _ ->
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
        test(sessionManager1.getMertuaLaki())
        initSpinner()
    }

    @SuppressLint("SetTextI18n")
    private fun test(mertua1: KeluargaReq) {
        edt_nama_lngkp_mertua_laki.setText(mertua1.nama)
        edt_alias_mertua_laki.setText(mertua1.nama_alias)
        edt_tmpt_ttl_mertua_laki.setText(mertua1.tempat_lahir)
        edt_tgl_ttl_mertua_laki.setText(mertua1.tanggal_lahir)
        sp_agama_mertua_laki.setText(mertua1.agama)
        edt_aliran_dianut_mertua_laki.setText(mertua1.aliran_kepercayaan_dianut)
        edt_suku_mertua_laki.setText(mertua1.ras)
        edt_kwg_mertua_laki.setText(mertua1.kewarganegaraan)
        edt_how_to_kwg_mertua_laki.setText(mertua1.cara_peroleh_kewarganegaraan)
        edt_almt_skrg_mertua_laki.setText(mertua1.alamat_rumah)
        edt_no_telp_mertua_laki.setText(mertua1.no_telp_rumah)
        edt_almt_rmh_sblm_mertua_laki.setText(mertua1.alamat_rumah_sebelumnya)
        edt_pekerjaan_mertua_laki.setText(mertua1.pekerjaan_terakhir)
        edt_almt_kntr_mertua_laki.setText(mertua1.alamat_kantor)
        edt_no_telp_kntr_mertua_laki.setText(mertua1.no_telp_kantor)
        edt_pekerjaan_sblm_mertua_laki.setText(mertua1.pekerjaan_sebelumnya)
        edt_pend_trkhr_mertua_laki.setText(mertua1.pendidikan_terakhir)
        edt_kddkn_org_diikuti_mertua_laki.setText(mertua1.kedudukan_organisasi_saat_ini)
        edt_thn_org_diikuti_mertua_laki.setText(mertua1.tahun_bergabung_organisasi_saat_ini)
        edt_alasan_org_diikuti_mertua_laki.setText(mertua1.alasan_bergabung_organisasi_saat_ini)
        edt_almt_org_diikuti_mertua_laki.setText(mertua1.alamat_organisasi_saat_ini)
        edt_kddkn_org_prnh_mertua_laki.setText(mertua1.kedudukan_organisasi_sebelumnya)
        edt_thn_org_prnh_mertua_laki.setText(mertua1.tahun_bergabung_organisasi_sebelumnya)
        edt_alasan_org_prnh_mertua_laki.setText(mertua1.alasan_bergabung_organisasi_sebelumnya)
        edt_almt_org_prnh_mertua_laki.setText(mertua1.alamat_organisasi_sebelumnya)
        edt_tahun_kematian_mertua_laki.setText(mertua1.tahun_kematian)
        edt_dimana_mertua_laki.setText(mertua1.lokasi_kematian)
        edt_penyebab_mertua_laki.setText(mertua1.sebab_kematian)
        edt_nama_almt_kntr_mertua_laki.setText(mertua1.nama_kantor)

        edt_kddkn_org_prnh_mertua_laki.setText(mertua1.kedudukan_organisasi_saat_ini)
        edt_thn_org_diikuti_mertua_laki.setText(mertua1.tahun_bergabung_organisasi_saat_ini)
        edt_alasan_org_diikuti_mertua_laki.setText(mertua1.alasan_bergabung_organisasi_saat_ini)
        edt_almt_org_diikuti_mertua_laki.setText(mertua1.alamat_organisasi_saat_ini)
        edt_kddkn_org_prnh_mertua_laki.setText(mertua1.kedudukan_organisasi_sebelumnya)
        edt_thn_org_prnh_mertua_laki.setText(mertua1.tahun_bergabung_organisasi_sebelumnya)
        edt_alasan_org_prnh_mertua_laki.setText(mertua1.alasan_bergabung_organisasi_sebelumnya)
        edt_almt_org_prnh_mertua_laki.setText(mertua1.alamat_rumah_sebelumnya)

        agama_skrg = mertua1.agama.toString()
        sp_agama_mertua_laki.setText(mertua1.agama) /**/

        stts_kerja = mertua1.status_kerja!!
        stts_hidup = mertua1.status_kehidupan!!
        if (mertua1.status_kerja == 1) {
            txt_layout_nama_kantor_mertua_laki.visibility = View.VISIBLE
            txt_layout_alamat_kantor_mertua_laki.visibility = View.VISIBLE
            txt_layout_no_telp_kantor_mertua_laki.visibility = View.VISIBLE
            txt_layout_alsn_berhenti_mertua_laki.visibility = View.GONE
            txt_layout_thn_berhenti_mertua_laki.visibility = View.GONE
            spinner_pekerjaan_mertua_laki.setText("Masih")
        } else {
            txt_layout_nama_kantor_mertua_laki.visibility = View.GONE
            txt_layout_alamat_kantor_mertua_laki.visibility = View.GONE
            txt_layout_no_telp_kantor_mertua_laki.visibility = View.GONE
            txt_layout_alsn_berhenti_mertua_laki.visibility = View.VISIBLE
            txt_layout_thn_berhenti_mertua_laki.visibility = View.VISIBLE
            spinner_pekerjaan_mertua_laki.setText("Tidak")
        }
        if (mertua1.status_kehidupan == 1) {
            txt_layout_penyebab_mertua_laki.visibility = View.GONE
            txt_layout_bagaimana_stts_mertua_laki.visibility = View.GONE
            txt_layout_dimana_mertua_laki.visibility = View.GONE
            spinner_stts_hidup_mertua_laki.setText("Masih")
        } else {
            txt_layout_penyebab_mertua_laki.visibility = View.VISIBLE
            txt_layout_bagaimana_stts_mertua_laki.visibility = View.VISIBLE
            txt_layout_dimana_mertua_laki.visibility = View.VISIBLE
            spinner_stts_hidup_mertua_laki.setText("Tidak")
        }
        edt_thn_berhenti_mertua_laki.setText(mertua1.tahun_pensiun)
        edt_alsn_berhenti_mertua_laki.setText(mertua1.alasan_pensiun)
    }
}