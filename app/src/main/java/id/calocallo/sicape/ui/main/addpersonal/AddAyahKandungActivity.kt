package id.calocallo.sicape.ui.main.addpersonal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import id.calocallo.sicape.R
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_ayah_kandung.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddAyahKandungActivity : BaseActivity() {
    var stts_kerja = ""
    var stts_hidup = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ayah_kandung)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Ayah Kandung"
        initSpinner(spinner_pekerjaan_ayah, spinner_stts_hidup_ayah)

        val nama_lengkap = edt_nama_lngkp_ayah.text.toString()
        val alias = edt_alias_ayah.text.toString()
        val tempat_lhr = edt_tmpt_ttl_ayah.text.toString()
        val tgl_lhr = edt_tgl_ttl_ayah.text.toString()
        val agama = edt_agm_ayah.text.toString()
        val aliran_dianut = edt_aliran_dianut_ayah.text.toString()
        val suku = edt_suku_ayah.text.toString()
        val kwg = edt_kwg_ayah.text.toString()
        val how_to_kwg = edt_how_to_kwg_ayah.text.toString()
        val almt_skrg = edt_almt_skrg_ayah.text.toString()
        val no_telp_rmh = edt_no_telp_ayah.text.toString()
        val almt_sblm = edt_almt_rmh_sblm_ayah.text.toString()
        val pkrjaan = edt_pekerjaan_ayah.text.toString()
        val almt_kantor = edt_almt_kntr_ayah.text.toString()
        val no_tlp_kantor = edt_no_telp_kntr_ayah.text.toString()
        val pekerjaan_sblm = edt_pekerjaan_sblm_ayah.text.toString()
        val pend_terakhir = edt_pend_trkhr_ayah.text.toString()
        Log.e("main", stts_kerja)

        //organisasi yang diikuti
        val kddkn_org_diikuti = edt_kddkn_org_diikuti_ayah.text.toString()
        val thn_org_diikut = edt_thn_org_diikuti_ayah.text.toString()
        val alasan_org_diikuti = edt_alasan_org_diikuti_ayah.text.toString()
        val almt_org_diikuti = edt_almt_org_diikuti_ayah.text.toString()

        //organisasi yang pernah diikuti
        val kddkn_org_prnh = edt_kddkn_org_prnh_ayah.text.toString()
        val thn_org_prnh = edt_thn_org_prnh_ayah.text.toString()
        val alasan_org_prnh = edt_alasan_org_prnh_ayah.text.toString()
        val almt_org_prnh = edt_almt_org_prnh_ayah.text.toString()

        //status hidup

        val bagaimana_hidup = edt_bagaimana_ayah.text.toString()
        val dimana_hidup = edt_dimana_ayah.text.toString()
        val penyebab_hidup = edt_penyebab_ayah.text.toString()

        btn_next_ayah.setOnClickListener {
            //init(allModel)
            //berhasil -> GO
            //gagal -> TOAST

            Log.e("ayah_kandung_activity", "$stts_kerja, $stts_hidup")
            startActivity(Intent(this, AddAyahTiriActivity::class.java))
        }
    }

    private fun initSpinner(
        spinner: AutoCompleteTextView,
        spinnerSttsHidup: AutoCompleteTextView
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
                stts_kerja = parent.getItemAtPosition(position).toString()

            } else { //tidak bekerja
//                Log.e("stts_kerja_spinner", parent.getItemAtPosition(position).toString())
                txt_layout_thn_berhenti_ayah.visibility = View.VISIBLE
                txt_layout_alsn_berhenti_ayah.visibility = View.VISIBLE
                txt_layout_pekerjaan_ayah.visibility = View.GONE
                stts_kerja = parent.getItemAtPosition(position).toString()

            }
        }
        val itemHidup = listOf("Masih", "Tidak")
        val adapterHidup = ArrayAdapter(this, R.layout.item_spinner, itemHidup)
        spinnerSttsHidup.setAdapter(adapterHidup)
        spinnerSttsHidup.setOnItemClickListener { parent, view, position, id ->
            if (position != 0) {
                txt_layout_penyebab_ayah.visibility = View.VISIBLE
                txt_layout_bagaimana_stts_ayah.visibility = View.VISIBLE
                txt_layout_dimana_ayah.visibility = View.VISIBLE
                stts_hidup = parent.getItemAtPosition(position).toString()
            } else {
                txt_layout_penyebab_ayah.visibility = View.GONE
                txt_layout_bagaimana_stts_ayah.visibility = View.GONE
                txt_layout_dimana_ayah.visibility = View.GONE
                stts_hidup = parent.getItemAtPosition(position).toString()

            }

        }
    }
}