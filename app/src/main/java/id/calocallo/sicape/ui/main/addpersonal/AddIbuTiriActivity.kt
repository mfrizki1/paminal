package id.calocallo.sicape.ui.main.addpersonal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import id.calocallo.sicape.R
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_ibu_tiri.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddIbuTiriActivity : BaseActivity() {
    var stts_kerja = ""
    var stts_hidup = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ibu_tiri)

        supportActionBar?.title = "Ibu Tiri / Angkat"
        setupActionBarWithBackButton(toolbar)
        initSpinner(spinner_pekerjaan_ibu_tiri, spinner_stts_hidup_ibu_tiri)

        val nama_lengkap = edt_nama_lngkp_ibu_tiri.text.toString()
        val alias = edt_alias_ibu_tiri.text.toString()
        val tempat_lhr = edt_tmpt_ttl_ibu_tiri.text.toString()
        val tgl_lhr = edt_tgl_ttl_ibu_tiri.text.toString()
        val agama = edt_agm_ibu_tiri.text.toString()
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
        val bagaimana_hidup = edt_bagaimana_stts_ibu_tiri.text.toString()
        val dimana_hidup = edt_dimana_ibu_tiri.text.toString()
        val penyebab_hidup = edt_penyebab_ibu_tiri.text.toString()

        btn_next_ibu_tiri.setOnClickListener {
            startActivity(Intent(this, AddMertuaLakiActivity::class.java))
        }
    }

    private fun initSpinner(
        spinnerPekerjaanIbuTiri: AutoCompleteTextView,
        spinnerSttsHidupIbuTiri: AutoCompleteTextView
    ) {
        val item = listOf("Masih", "Tidak")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        spinnerPekerjaanIbuTiri.setAdapter(adapter)
        spinnerPekerjaanIbuTiri.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                txt_layout_pekerjaan_ibu_tiri.visibility = View.VISIBLE
                txt_layout_thn_berhenti_ibu_tiri.visibility = View.GONE
                txt_layout_alsn_berhenti_ibu_tiri.visibility = View.GONE
                stts_kerja = parent.getItemAtPosition(position).toString()

            } else {
                txt_layout_pekerjaan_ibu_tiri.visibility = View.GONE
                txt_layout_thn_berhenti_ibu_tiri.visibility = View.VISIBLE
                txt_layout_alsn_berhenti_ibu_tiri.visibility = View.VISIBLE
                stts_kerja = parent.getItemAtPosition(position).toString()

            }
        }
        val itemHidup = listOf("Masih", "Tidak")
        val adapterHidup = ArrayAdapter(this, R.layout.item_spinner, itemHidup)
        spinnerSttsHidupIbuTiri.setAdapter(adapterHidup)
        spinnerSttsHidupIbuTiri.setOnItemClickListener { parent, view, position, id ->
            if (position != 0) {
                txt_layout_penyebab_ibu_tiri.visibility = View.GONE
                txt_layout_dimana_ibu_tiri.visibility = View.GONE
                txt_layout_bagaimana_stts_ibu_tiri.visibility = View.GONE
                stts_hidup = parent.getItemAtPosition(position).toString()
            } else {
                txt_layout_penyebab_ibu_tiri.visibility = View.VISIBLE
                txt_layout_dimana_ibu_tiri.visibility = View.VISIBLE
                txt_layout_bagaimana_stts_ibu_tiri.visibility = View.VISIBLE
                stts_hidup = parent.getItemAtPosition(position).toString()
            }
        }
    }
}