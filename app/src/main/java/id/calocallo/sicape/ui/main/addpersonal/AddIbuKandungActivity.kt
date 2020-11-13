package id.calocallo.sicape.ui.main.addpersonal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import id.calocallo.sicape.R
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_ibu_kandung.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddIbuKandungActivity : BaseActivity() {
    var stts_kerja = ""
    var stts_hidup = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ibu_kandung)

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Ibu Kandung"
        initSpinner(spinner_pekerjaan_ibu, spinner_stts_hidup_ibu)


        val nama_lengkap = edt_nama_lngkp_ibu.text.toString()
        val alias = edt_alias_ibu.text.toString()
        val tempat_lhr = edt_tmpt_ttl_ibu.text.toString()
        val tgl_lhr = edt_tgl_ttl_ibu.text.toString()
        val agama = edt_agm_ibu.text.toString()
        val aliran_dianut = edt_aliran_dianut_ibu.text.toString()
        val suku = edt_suku_ibu.text.toString()
        val kwg = edt_kwg_ibu.text.toString()
        val how_to_kwg = edt_how_to_kwg_ibu.text.toString()
        val almt_skrg = edt_almt_skrg_ibu.text.toString()
        val no_telp_rmh = edt_no_telp_ibu.text.toString()
        val almt_sblm = edt_almt_rmh_sblm_ibu.text.toString()
        val pkrjaan = edt_pekerjaan_ibu.text.toString()
        val almt_kantor = edt_almt_kntr_ibu.text.toString()
        val no_tlp_kantor = edt_no_telp_kntr_ibu.text.toString()
        val pekerjaan_sblm = edt_pekerjaan_sblm_ibu.text.toString()
        val pend_terakhir = edt_pend_trkhr_ibu.text.toString()

        //organisasi yang diikuti
        val kddkn_org_diikuti = edt_kddkn_org_diikuti_ibu.text.toString()
        val thn_org_diikut = edt_thn_org_diikuti_ibu.text.toString()
        val alasan_org_diikuti = edt_alasan_org_diikuti_ibu.text.toString()
        val almt_org_diikuti = edt_almt_org_diikuti_ibu.text.toString()

        //organisasi yang pernah diikuti
        val kddkn_org_prnh = edt_kddkn_org_prnh_ibu.text.toString()
        val thn_org_prnh = edt_thn_org_prnh_ibu.text.toString()
        val alasan_org_prnh = edt_alasan_org_prnh_ibu.text.toString()
        val almt_org_prnh = edt_almt_org_prnh_ibu.text.toString()

        //status hidup

        val bagaimana_hidup = edt_bagaimana_stts_ibu.text.toString()
        val dimana_hidup = edt_dimana_ibu.text.toString()
        val penyebab_hidup = edt_penyebab_ibu.text.toString()

        btn_next_ibu.setOnClickListener {
            startActivity(Intent(this, AddIbuTiriActivity::class.java))
        }

    }

    private fun initSpinner(
        spinnerPekerjaanIbu: AutoCompleteTextView,
        spinnerSttsHidupIbu: AutoCompleteTextView
    ) {
        val item = listOf("Masih", "Tidak")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        spinnerPekerjaanIbu.setAdapter(adapter)
        spinnerPekerjaanIbu.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                txt_layout_pekerjaan_ibu.visibility = View.VISIBLE
                txt_layout_alsn_berhenti_ibu.visibility = View.GONE
                txt_layout_thn_berhenti_ibu.visibility = View.GONE
                stts_kerja = parent.getItemAtPosition(position).toString()
            } else {
                txt_layout_pekerjaan_ibu.visibility = View.GONE
                txt_layout_alsn_berhenti_ibu.visibility = View.VISIBLE
                txt_layout_thn_berhenti_ibu.visibility = View.VISIBLE
                stts_kerja = parent.getItemAtPosition(position).toString()
            }
        }
        val itemHidup = listOf("Masih", "Tidak")
        val adapterHidup = ArrayAdapter(this, R.layout.item_spinner, itemHidup)
        spinnerSttsHidupIbu.setAdapter(adapterHidup)
        spinnerSttsHidupIbu.setOnItemClickListener { parent, view, position, id ->
            if (position != 0) {
                txt_layout_bagaimana_stts_ibu.visibility = View.VISIBLE
                txt_layout_dimana_ibu.visibility = View.VISIBLE
                txt_layout_penyebab_ibu.visibility = View.VISIBLE
                stts_hidup = parent.getItemAtPosition(position).toString()
            } else {
                txt_layout_bagaimana_stts_ibu.visibility = View.GONE
                txt_layout_dimana_ibu.visibility = View.GONE
                txt_layout_penyebab_ibu.visibility = View.GONE
                stts_hidup = parent.getItemAtPosition(position).toString()
            }
//            stts_hidup = parent.getItemAtPosition(position).toString()
        }
    }
}