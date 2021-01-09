package id.calocallo.sicape.ui.main.rehab.sktb

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PutKkeOnRpphModel
import id.calocallo.sicape.model.SkhdOnRpsModel
import id.calocallo.sicape.network.request.SktbReq
import id.calocallo.sicape.network.response.SktbResp
import id.calocallo.sicape.ui.main.choose.skhd.ChooseSkhdActivity
import id.calocallo.sicape.ui.main.rehab.rpph.EditRpphActivity
import id.calocallo.sicape.ui.main.rehab.rps.AddRpsActivity
import id.calocallo.sicape.ui.main.rehab.rps.EditRpsActivity
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_sktb.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditSktbActivity : BaseActivity() {
    private var idSkhd: Int? = null
    private var idSkhdResult: Int? = null
    private var idPutkke: Int? = null
    private var idPutkkeResult: Int? = null
    private var sktbReq = SktbReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_sktb)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data SKTB"
        val dataSktb = intent.extras?.getParcelable<SktbResp>(EDIT_SKTB)
        getViewSktb(dataSktb)

        btn_save_sktb_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_sktb_edit)
        btn_save_sktb_edit.setOnClickListener {
            updateSktb(dataSktb)
        }

        rg_skhd_putkke_sktb_edit.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            if (radio.isChecked && radio.text == "SKHD") {
                ll_skhd_sktb_edit.visible()
                ll_put_kke_sktb_edit.gone()

            } else if (radio.isChecked && radio.text == "PUT KKE") {
                ll_skhd_sktb_edit.gone()
                ll_put_kke_sktb_edit.visible()
            }
        }
        btn_pick_put_kke_sktb_edit.setOnClickListener {
            val intent = Intent(this, ChooseSkhdActivity::class.java)
            intent.putExtra(ChooseSkhdActivity.PUT_KKE, ChooseSkhdActivity.PUT_KKE)
            startActivityForResult(intent, EditRpphActivity.REQ_PUT_KKE_ON_RPPH_EDIT)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_pick_skhd_sktb_edit.setOnClickListener {
            val intent = Intent(this, ChooseSkhdActivity::class.java)
            startActivityForResult(intent, EditRpsActivity.REQ_ID_SKHD_EDIT_RPS)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun updateSktb(dataSktb: SktbResp?) {
        val animated = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animated.setBounds(0, 0, size, size)

        sktbReq.no_sktb = edt_no_sktb_edit.text.toString()
        sktbReq.menimbang_p2 = edt_menimbang_p2_sktb_edit.text.toString()
        sktbReq.mengingat_p5 = edt_mengingat_p5_sktb_edit.text.toString()
        sktbReq.kota_penetapan = edt_kota_penetapan_sktb_edit.text.toString()
        sktbReq.tanggal_penetapan = edt_tanggal_penetapan_sktb_edit.text.toString()
        sktbReq.nama_yang_menetapkan = edt_nama_pimpinan_sktb_edit.text.toString()
        sktbReq.pangkat_yang_menetapkan =
            edt_pangkat_pimpinan_sktb_edit.text.toString().toUpperCase()
        sktbReq.nrp_yang_menetapkan = edt_nrp_pimpinan_sktb_edit.text.toString()
        sktbReq.jabatan_yang_menetapkan = edt_jabatan_pimpinan_sktb_edit.text.toString()
        sktbReq.tembusan = edt_tembusan_sktb_edit.text.toString()
//        if (idPutkke == dataSktb?.putkke?.id) {
        sktbReq.id_putkke = idPutkke
//            sktbReq.id_skhd = null
//        } else {
        sktbReq.id_skhd = idSkhd
//            sktbReq.id_putkke = null
//        }

        btn_save_sktb_edit.showProgress {
            progressColor = Color.WHITE
        }
        btn_save_sktb_edit.showDrawable(animated) {
            textMarginRes = R.dimen.space_10dp
            buttonTextRes = R.string.data_saved
        }
        Handler(Looper.getMainLooper()).postDelayed({
            btn_save_sktb_edit.hideDrawable(R.string.save)
        }, 2000)
        Log.e("Edit SKTB", "$sktbReq")
    }

    private fun getViewSktb(dataSktb: SktbResp?) {
        if (dataSktb?.putkke?.id != null) {
            rb_put_kke_sktb_edit.isChecked = true
            ll_skhd_sktb_edit.gone()
            ll_put_kke_sktb_edit.visible()
        }
        if (dataSktb?.skhd?.id != null) {
            rb_skhd_sktb_edit.isChecked = true
            ll_skhd_sktb_edit.visible()
            ll_put_kke_sktb_edit.gone()
        }

        edt_no_sktb_edit.setText(dataSktb?.no_sktb)
        edt_menimbang_p2_sktb_edit.setText(dataSktb?.menimbang_p2)
        edt_mengingat_p5_sktb_edit.setText(dataSktb?.mengingat_p5)
        edt_kota_penetapan_sktb_edit.setText(dataSktb?.kota_penetapan)
        edt_tanggal_penetapan_sktb_edit.setText(dataSktb?.tanggal_penetapan)
        edt_nama_pimpinan_sktb_edit.setText(dataSktb?.nama_yang_menetapkan)
        edt_pangkat_pimpinan_sktb_edit.setText(
            dataSktb?.pangkat_yang_menetapkan.toString().toUpperCase()
        )
        edt_nrp_pimpinan_sktb_edit.setText(dataSktb?.nrp_yang_menetapkan)
        edt_jabatan_pimpinan_sktb_edit.setText(dataSktb?.jabatan_yang_menetapkan)
        edt_tembusan_sktb_edit.setText(dataSktb?.tembusan)
        txt_skhd_sktb_edit.text = dataSktb?.skhd?.no_skhd
        txt_put_kke_sktb_edit.text = dataSktb?.putkke?.no_putkke
        idSkhd = dataSktb?.skhd?.id
        idPutkke = dataSktb?.putkke?.id

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EditRpsActivity.REQ_ID_SKHD_EDIT_RPS) {
            if (resultCode == AddRpsActivity.RES_ID_SKHD) {
                val getSkhd = data?.getParcelableExtra<SkhdOnRpsModel>(AddRpsActivity.GET_SKHD)
                if (getSkhd?.id == null) {
                    txt_skhd_sktb_edit.text = "No SKHD"
                } else {
                    txt_skhd_sktb_edit.text = getSkhd.no_skhd
                    idSkhd = getSkhd.id
                    idPutkke = null
                }
            }
        }
        if (requestCode == EditRpphActivity.REQ_PUT_KKE_ON_RPPH_EDIT) {
            if (resultCode == EditRpphActivity.RES_PUT_KKE_ON_RPPH_EDIT) {
                val putKkeModel =
                    data?.getParcelableExtra<PutKkeOnRpphModel>(EditRpphActivity.GET_RPPH_EDIT)
                if (putKkeModel?.id == null) {
                    txt_put_kke_sktb_edit.text = "No PUT KKE"
                } else {
                    idPutkke = putKkeModel.id
                    txt_put_kke_sktb_edit.text = putKkeModel.no_putkke
                    idSkhd = null
                }
            }
        }
    }

    companion object {
        const val EDIT_SKTB = "EDIT_SKTB"
    }
}