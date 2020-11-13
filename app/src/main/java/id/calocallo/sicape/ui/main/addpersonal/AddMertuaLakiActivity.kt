package id.calocallo.sicape.ui.main.addpersonal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import id.calocallo.sicape.R
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_ibu_tiri.*
import kotlinx.android.synthetic.main.activity_add_mertua_laki.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddMertuaLakiActivity : BaseActivity() {
    var stts_hidup = ""
    var stts_kerja = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_mertua_laki)

        supportActionBar?.title = "Mertua Laki"
        setupActionBarWithBackButton(toolbar)
        initSpinner(spinner_pekerjaan_mertua_laki, spinner_stts_hidup_mertua_laki)


        val nama_lengkap = edt_nama_lngkp_mertua_laki.text.toString()
        val alias = edt_alias_mertua_laki.text.toString()
        val tempat_lhr = edt_tmpt_ttl_mertua_laki.text.toString()
        val tgl_lhr = edt_tgl_ttl_mertua_laki.text.toString()
        val agama = edt_agm_mertua_laki.text.toString()
        val aliran_dianut = edt_aliran_dianut_mertua_laki.text.toString()
        val suku = edt_suku_mertua_laki.text.toString()
        val kwg = edt_kwg_mertua_laki.text.toString()
        val how_to_kwg = edt_how_to_kwg_mertua_laki.text.toString()
        val almt_skrg = edt_almt_skrg_mertua_laki.text.toString()
        val no_telp_rmh = edt_no_telp_mertua_laki.text.toString()
        val almt_sblm = edt_almt_rmh_sblm_mertua_laki.text.toString()
        val pkrjaan = edt_pekerjaan_mertua_laki.text.toString()
        val almt_kantor = edt_almt_kntr_mertua_laki.text.toString()
        val no_tlp_kantor = edt_no_telp_kntr_mertua_laki.text.toString()
        val pekerjaan_sblm = edt_pekerjaan_sblm_mertua_laki.text.toString()
        val pend_terakhir = edt_pend_trkhr_mertua_laki.text.toString()

        //organisasi yang diikuti
        val kddkn_org_diikuti = edt_kddkn_org_diikuti_mertua_laki.text.toString()
        val thn_org_diikut = edt_thn_org_diikuti_mertua_laki.text.toString()
        val alasan_org_diikuti = edt_alasan_org_diikuti_mertua_laki.text.toString()
        val almt_org_diikuti = edt_almt_org_diikuti_mertua_laki.text.toString()

        //organisasi yang pernah diikuti
        val kddkn_org_prnh = edt_kddkn_org_prnh_mertua_laki.text.toString()
        val thn_org_prnh = edt_thn_org_prnh_mertua_laki.text.toString()
        val alasan_org_prnh = edt_alasan_org_prnh_mertua_laki.text.toString()
        val almt_org_prnh = edt_almt_org_prnh_mertua_laki.text.toString()

        //status hidup
        val bagaimana_hidup = edt_bagaimana_stts_mertua_laki.text.toString()
        val dimana_hidup = edt_dimana_mertua_laki.text.toString()
        val penyebab_hidup = edt_penyebab_mertua_laki.text.toString()

        btn_next_mertua_laki.setOnClickListener {
            startActivity(Intent(this, AddMertuaPerempuanActivity::class.java))
        }
    }

    private fun initSpinner(
        spinnerPekerjaanMertuaLaki: AutoCompleteTextView,
        spinnerSttsHidupMertuaLaki: AutoCompleteTextView
    ) {
        val item = listOf("Masih", "Tidak")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        spinnerPekerjaanMertuaLaki.setAdapter(adapter)
        spinnerPekerjaanMertuaLaki.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                txt_layout_pekerjaan_mertua_laki.visibility = View.VISIBLE
                txt_layout_thn_berhenti_mertua_laki.visibility = View.GONE
                txt_layout_alsn_berhenti_mertua_laki.visibility = View.GONE
                stts_kerja = parent.getItemAtPosition(position).toString()

            } else {
                txt_layout_pekerjaan_mertua_laki.visibility = View.GONE
                txt_layout_thn_berhenti_mertua_laki.visibility = View.VISIBLE
                txt_layout_alsn_berhenti_mertua_laki.visibility = View.VISIBLE
                stts_kerja = parent.getItemAtPosition(position).toString()

            }
        }
        val itemHidup = listOf("Masih", "Tidak")
        val adapterHidup = ArrayAdapter(this, R.layout.item_spinner, itemHidup)
        spinnerSttsHidupMertuaLaki.setAdapter(adapterHidup)
        spinnerSttsHidupMertuaLaki.setOnItemClickListener { parent, view, position, id ->
            if (position != 0) {
                txt_layout_penyebab_mertua_laki.visibility = View.GONE
                txt_layout_dimana_mertua_laki.visibility = View.GONE
                txt_layout_bagaimana_stts_mertua_laki.visibility = View.GONE
                stts_hidup = parent.getItemAtPosition(position).toString()
            } else {
                txt_layout_penyebab_mertua_laki.visibility = View.VISIBLE
                txt_layout_dimana_mertua_laki.visibility = View.VISIBLE
                txt_layout_bagaimana_stts_mertua_laki.visibility = View.VISIBLE
                stts_hidup = parent.getItemAtPosition(position).toString()
            }
        }
    }
}