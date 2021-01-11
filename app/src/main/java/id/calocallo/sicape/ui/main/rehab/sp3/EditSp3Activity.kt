package id.calocallo.sicape.ui.main.rehab.sp3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import id.calocallo.sicape.R
import id.calocallo.sicape.model.SktbOnSp3
import id.calocallo.sicape.model.SkttOnSp3
import id.calocallo.sicape.network.request.Sp3Req
import id.calocallo.sicape.network.response.Sp3Resp
import id.calocallo.sicape.ui.main.choose.ChooseSktbSkttActivity
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_sp3.*
import kotlinx.android.synthetic.main.activity_edit_sp3.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditSp3Activity : BaseActivity() {
    companion object {
        const val EDT_SP3 = "EDT_SP3"
        const val REQ_SKTB_ON_SP3_EDIT = 1
        const val REQ_SKTT_ON_SP3_EDIT = 2
    }
    private var idSktb: Int? = null
    private var idSktt: Int? = null
    private var sp3Req = Sp3Req()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_sp3)

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data SP3"
        val getDetailSp3 = intent.extras?.getParcelable<Sp3Resp>(EDT_SP3)
        getViewSp3Edit(getDetailSp3)
        rg_sktb_sktt_sp3_edit.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            if (radio.isChecked && radio.text == "SKTB") {
                ll_sktb_sp3_edit.visible()
                ll_sktt_sp3_edit.gone()
            } else {
                ll_sktb_sp3_edit.gone()
                ll_sktt_sp3_edit.visible()
            }
        }

        btn_pick_sktb_sp3_edit.setOnClickListener {
            val intent = Intent(this, ChooseSktbSkttActivity::class.java)
            startActivityForResult(intent, REQ_SKTB_ON_SP3_EDIT)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }


        btn_pick_sktt_sp3_edit.setOnClickListener {
            val intent = Intent(this, ChooseSktbSkttActivity::class.java)
            intent.putExtra(AddSp3Activity.SKTT_ON_SP3, AddSp3Activity.SKTT_ON_SP3)
            startActivityForResult(intent, REQ_SKTT_ON_SP3_EDIT)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_save_sp3_edit.setOnClickListener {
            updateSp3(getDetailSp3)
        }
    }

    private fun updateSp3(detailSp3: Sp3Resp?) {
        sp3Req.no_sp4 = edt_no_sp3_edit.text.toString()
        sp3Req.mengingat_p4 = edt_mengingat_p4_sp3_edit.text.toString()
        sp3Req.mengingat_p5 = edt_mengingat_p5_sp3_edit.text.toString()
        sp3Req.menetapkan_p1 = edt_menetapkan_p1_sp3_edit.text.toString()
        sp3Req.kota_keluar = edt_kota_dikeluarkan_sp3_edit.text.toString()
        sp3Req.tanggal_keluar = edt_tanggal_dikeluarkan_sp3_edit.text.toString()
        sp3Req.nama_akreditor = edt_nama_akreditor_sp3_edit.text.toString()
        sp3Req.pangkat_akreditor = edt_pangkat_akreditor_sp3_edit.text.toString().toUpperCase()
        sp3Req.nrp_akreditor = edt_nrp_akreditor_sp3_edit.text.toString()
        if (idSktt != null) {
            sp3Req.id_sktt = idSktt
            sp3Req.id_sktb = null
        } else {
            sp3Req.id_sktb = idSktb
            sp3Req.id_sktt = null
        }
        Log.e("edti SP3", "$sp3Req")
    }

    private fun getViewSp3Edit(detailSp3: Sp3Resp?) {

        edt_no_sp3_edit.setText(detailSp3?.no_sp4)
        edt_mengingat_p4_sp3_edit.setText(detailSp3?.mengingat_p4)
        edt_mengingat_p5_sp3_edit.setText(detailSp3?.mengingat_p5)
        edt_menetapkan_p1_sp3_edit.setText(detailSp3?.menetapkan_p1)
        edt_kota_dikeluarkan_sp3_edit.setText(detailSp3?.kota_keluar)
        edt_tanggal_dikeluarkan_sp3_edit.setText(detailSp3?.tanggal_keluar)
        edt_nama_akreditor_sp3_edit.setText(detailSp3?.nama_akreditor)
        edt_pangkat_akreditor_sp3_edit.setText(
            detailSp3?.pangkat_akreditor.toString().toUpperCase()
        )
        edt_nrp_akreditor_sp3_edit.setText(detailSp3?.nrp_akreditor)
        txt_sktb_sp3_edit.text = detailSp3?.sktb?.no_sktb
        txt_sktt_sp3_edit.text = detailSp3?.sktt?.no_sktt
        if (detailSp3?.sktb?.id != null) {
            rb_sktb_sp3_edit.isChecked = true
            ll_sktb_sp3_edit.visible()
            ll_sktt_sp3_edit.gone()
            txt_sktb_sp3_edit.text = detailSp3?.sktb?.no_sktb

        } else {
            rb_sktt_sp3_edit.isChecked = true
            ll_sktb_sp3_edit.gone()
            ll_sktt_sp3_edit.visible()
            txt_sktt_sp3_edit.text = detailSp3?.sktt?.no_sktt
        }
        idSktb = detailSp3?.sktb?.id
        idSktt = detailSp3?.sktt?.id


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AddSp3Activity.REQ_SKTB_ON_SP3) {
            if (resultCode == Activity.RESULT_OK) {
                val sktb = data?.getParcelableExtra<SktbOnSp3>(ChooseSktbSkttActivity.GET_SKTB)
//                Log.e("skhd", "$sktb")
                if (sktb == null) {
                    txt_sktb_sp3_edit.text = "No SKTB"
                } else {
                    txt_sktb_sp3_edit.text = sktb.no_sktb
                    idSktb = sktb.id
                    idSktt = null
                    txt_sktt_sp3_edit.text = "No SKTT"
                }
            }
        }

        if (requestCode == AddSp3Activity.REQ_SKTT_ON_SP3) {
            if (resultCode == Activity.RESULT_OK) {
                val sktt =
                    data?.getParcelableExtra<SkttOnSp3>(ChooseSktbSkttActivity.GET_SKTT)
                Log.e("putKKe", "$sktt")
                if (sktt == null) {
                    txt_sktt_sp3_edit.text = "No SKTT"
                } else {
                    idSktt = sktt.id
                    txt_sktt_sp3_edit.text = sktt.no_sktt
                    idSktb = null
                    txt_sktb_sp3_edit.text = "No SKTB"
                }
            }
        }
    }


}
