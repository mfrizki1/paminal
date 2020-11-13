package id.calocallo.sicape.ui.main.addpersonal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import id.calocallo.sicape.R
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_ayah_kandung.*
import kotlinx.android.synthetic.main.activity_add_ayah_tiri.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddAyahTiriActivity : BaseActivity() {
    var stts_kerja = ""
    var stts_hidup = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ayah_tiri)
        setupActionBarWithBackButton(toolbar)

        supportActionBar?.title = "Ayah Tiri / Angkat"
        initSpinner(spinner_pekerjaan_ayah_tiri, spinner_stts_hidup_ayah_tiri)

        val nama_lengkap = edt_nama_lngkp_ayah_tiri.text.toString()
        val alias = edt_alias_ayah_tiri.text.toString()
        val tempat_lhr = edt_tmpt_ttl_ayah_tiri.text.toString()
        val tgl_lhr = edt_tgl_ttl_ayah_tiri.text.toString()
        val agama = edt_agm_ayah_tiri.text.toString()
        val aliran_dianut = edt_aliran_dianut_ayah_tiri.text.toString()
        val suku = edt_suku_ayah_tiri.text.toString()
        val kwg = edt_kwg_ayah_tiri.text.toString()
        val how_to_kwg = edt_how_to_kwg_ayah_tiri.text.toString()
        val almt_skrg = edt_almt_skrg_ayah_tiri.text.toString()
        val no_telp_rmh = edt_no_telp_ayah_tiri.text.toString()
        val almt_sblm = edt_almt_rmh_sblm_ayah_tiri.text.toString()
        val pkrjaan = edt_pekerjaan_ayah_tiri.text.toString()
        val almt_kantor = edt_almt_kntr_ayah_tiri.text.toString()
        val no_tlp_kantor = edt_no_telp_kntr_ayah_tiri.text.toString()
        val pekerjaan_sblm = edt_pekerjaan_sblm_ayah_tiri.text.toString()
        val pend_terakhir = edt_pend_trkhr_ayah_tiri.text.toString()


        //organisasi yang diikuti
        val kddkn_org_diikuti = edt_kddkn_org_diikuti_ayah_tiri.text.toString()
        val thn_org_diikut = edt_thn_org_diikuti_ayah_tiri.text.toString()
        val alasan_org_diikuti = edt_alasan_org_diikuti_ayah_tiri.text.toString()
        val almt_org_diikuti = edt_almt_org_diikuti_ayah_tiri.text.toString()

        //organisasi yang pernah diikuti
        val kddkn_org_prnh = edt_kddkn_org_prnh_ayah_tiri.text.toString()
        val thn_org_prnh = edt_thn_org_prnh_ayah_tiri.text.toString()
        val alasan_org_prnh = edt_alasan_org_prnh_ayah_tiri.text.toString()
        val almt_org_prnh = edt_almt_org_prnh_ayah_tiri.text.toString()

        //status hidup
        val bagaimana_hidup = edt_bagaimana_stts_ayah_tiri.text.toString()
        val dimana_hidup = edt_dimana_ayah_tiri.text.toString()
        val penyebab_hidup = edt_penyebab_ayah_tiri.text.toString()

        btn_next_ayah_tiri.setOnClickListener {
            startActivity(Intent(this, AddIbuKandungActivity::class.java))
        }


    }

    private fun initSpinner(
        spinnerPekerjaanAyah: AutoCompleteTextView,
        spinnerSttsHidupAyah: AutoCompleteTextView
    ) {
        val item = listOf("Masih", "Tidak")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        spinnerPekerjaanAyah.setAdapter(adapter)
        spinnerPekerjaanAyah.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                txt_layout_pekerjaan_ayah_tiri.visibility = View.VISIBLE
                txt_layout_thn_berhenti_ayah_tiri.visibility = View.GONE
                txt_layout_alsn_berhenti_ayah_tiri.visibility = View.GONE
                stts_kerja = parent.getItemAtPosition(position).toString()
            } else {
                txt_layout_thn_berhenti_ayah_tiri.visibility = View.VISIBLE
                txt_layout_alsn_berhenti_ayah_tiri.visibility = View.VISIBLE
                txt_layout_pekerjaan_ayah_tiri.visibility = View.GONE
                stts_kerja = parent.getItemAtPosition(position).toString()
            }
        }
        val itemHidup = listOf("Masih", "Tidak")
        val adapterHidup = ArrayAdapter(this, R.layout.item_spinner, itemHidup)
        spinnerSttsHidupAyah.setAdapter(adapterHidup)
        spinnerSttsHidupAyah.setOnItemClickListener { parent, view, position, id ->
            if (position != 0) {
                txt_layout_penyebab_ayah_tiri.visibility = View.VISIBLE
                txt_layout_bagaimana_stts_ayah_tiri.visibility = View.VISIBLE
                txt_layout_dimana_ayah_tiri.visibility = View.VISIBLE
                stts_hidup = parent.getItemAtPosition(position).toString()
            } else {
                txt_layout_penyebab_ayah_tiri.visibility = View.GONE
                txt_layout_bagaimana_stts_ayah_tiri.visibility = View.GONE
                txt_layout_dimana_ayah_tiri.visibility = View.GONE
                stts_hidup = parent.getItemAtPosition(position).toString()
            }
        }
    }
}