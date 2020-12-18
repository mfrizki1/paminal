package id.calocallo.sicape.ui.main.lp

import android.os.Bundle
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LpDisiplinModel
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_lp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditLpActivity: BaseActivity() {
    companion object{
        const val EDIT_LP = "EDIT_LP"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lp)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title ="Edit Data Laporan Polisi"

        val editLp = intent.extras?.getParcelable<LpDisiplinModel>(EDIT_LP)
        edt_no_lp.setText(editLp?.no_lp)
        edt_hukuman_lp.setText(editLp?.hukuman)
        edt_ket_lp.setText(editLp?.keterangan)
        edt_nama_lp.setText(editLp?.nama_personel)
        edt_pangkat_lp.setText(editLp?.pangkat_personel)
        edt_nrp_lp.setText(editLp?.nrp_personel)
        edt_kesatuan_lp.setText(editLp?.kesatuan)
        edt_pasal_lp.setText(editLp?.pasal)
        sp_jenis_lp.setText(editLp?.jenis_pelanggaran)

        btn_save_lp.setOnClickListener {
            edt_no_lp.text.toString()
            edt_hukuman_lp.text.toString()
            edt_ket_lp.text.toString()
            edt_nama_lp.text.toString()
            edt_pangkat_lp.text.toString()
            edt_nrp_lp.text.toString()
            edt_kesatuan_lp.text.toString()
            edt_pasal_lp.text.toString()
        }
    }
}