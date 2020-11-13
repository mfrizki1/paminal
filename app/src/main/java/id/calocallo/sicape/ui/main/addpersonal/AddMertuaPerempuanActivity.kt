package id.calocallo.sicape.ui.main.addpersonal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import id.calocallo.sicape.R
import id.calocallo.sicape.ui.main.addpersonal.anak.AddAnakActivity
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_mertua_perempuan.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddMertuaPerempuanActivity : BaseActivity() {
    var stts_kerja = ""
    var stts_hidup = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_mertua_perempuan)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Mertua Perempuan"

        initSpinner(spinner_pekerjaan_mertua_perempuan, spinner_stts_hidup_mertua_perempuan)

        btn_next_mertua_perempuan.setOnClickListener {
            startActivity(Intent(this, AddAnakActivity::class.java))
        }
    }

    private fun initSpinner(
        spinnerPekerjaanMertuaPerempuan: AutoCompleteTextView,
        spinnerSttsHidupMertuaPerempuan: AutoCompleteTextView
    ) {
        val item = listOf("Masih", "Tidak")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        spinnerPekerjaanMertuaPerempuan.setAdapter(adapter)
        spinnerPekerjaanMertuaPerempuan.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                txt_layout_pekerjaan_mertua_perempuan.visibility = View.VISIBLE
                txt_layout_thn_berhenti_mertua_perempuan.visibility = View.GONE
                txt_layout_alsn_berhenti_mertua_perempuan.visibility = View.GONE
                stts_kerja = parent.getItemAtPosition(position).toString()

            } else {
                txt_layout_pekerjaan_mertua_perempuan.visibility = View.GONE
                txt_layout_thn_berhenti_mertua_perempuan.visibility = View.VISIBLE
                txt_layout_alsn_berhenti_mertua_perempuan.visibility = View.VISIBLE
                stts_kerja = parent.getItemAtPosition(position).toString()

            }
        }
        val itemHidup = listOf("Masih", "Tidak")
        val adapterHidup = ArrayAdapter(this, R.layout.item_spinner, itemHidup)
        spinnerSttsHidupMertuaPerempuan.setAdapter(adapterHidup)
        spinnerSttsHidupMertuaPerempuan.setOnItemClickListener { parent, view, position, id ->
            if (position != 0) {
                txt_layout_penyebab_mertua_perempuan.visibility = View.GONE
                txt_layout_dimana_mertua_perempuan.visibility = View.GONE
                txt_layout_bagaimana_stts_mertua_perempuan.visibility = View.GONE
                stts_hidup = parent.getItemAtPosition(position).toString()
            } else {
                txt_layout_penyebab_mertua_perempuan.visibility = View.VISIBLE
                txt_layout_dimana_mertua_perempuan.visibility = View.VISIBLE
                txt_layout_bagaimana_stts_mertua_perempuan.visibility = View.VISIBLE
                stts_hidup = parent.getItemAtPosition(position).toString()
            }
        }
    }
}