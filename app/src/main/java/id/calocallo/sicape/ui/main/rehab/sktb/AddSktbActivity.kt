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
import id.calocallo.sicape.ui.main.choose.skhd.ChooseSkhdActivity
import id.calocallo.sicape.ui.main.rehab.rpph.AddRpphActivity
import id.calocallo.sicape.ui.main.rehab.rps.AddRpsActivity
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_sktb.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddSktbActivity : BaseActivity() {
    private var idSkhd: Int? = null
    private var idPutkke: Int? = null
    private var sktbReq = SktbReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_sktb)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data SKTB"

        btn_save_sktb_add.attachTextChangeAnimator()
        bindProgressButton(btn_save_sktb_add)
        btn_save_sktb_add.setOnClickListener {
            addSktb()
        }



        btn_pick_skhd_sktb_add.setOnClickListener {
            val intent = Intent(this, ChooseSkhdActivity::class.java)
            startActivityForResult(intent, AddRpsActivity.REQ_ID_SKHD)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }


        btn_pick_put_kke_sktb_add.setOnClickListener {
            val intent = Intent(this, ChooseSkhdActivity::class.java)
            intent.putExtra(ChooseSkhdActivity.PUT_KKE, ChooseSkhdActivity.PUT_KKE)
            startActivityForResult(intent, AddRpphActivity.REQ_PUT_KKE_ON_RPPH)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }


        rg_skhd_putkke_sktb_add.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            if (radio.isChecked && radio.text == "SKHD") {
                ll_skhd_sktb_add.visible()
                ll_put_kke_sktb_add.gone()

            } else {
                ll_put_kke_sktb_add.visible()
                ll_skhd_sktb_add.gone()
            }
        }
    }

    private fun addSktb() {
        val animated = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animated.setBounds(0, 0, size, size)

        sktbReq.no_sktb = edt_no_sktb_add.text.toString()
        sktbReq.menimbang_p2 = edt_menimbang_p2_sktb_add.text.toString()
        sktbReq.mengingat_p5 = edt_mengingat_p5_sktb_add.text.toString()
        sktbReq.kota_penetapan = edt_kota_penetapan_sktb_add.text.toString()
        sktbReq.tanggal_penetapan = edt_tanggal_penetapan_sktb_add.text.toString()
        sktbReq.nama_yang_menetapkan = edt_nama_pimpinan_sktb_add.text.toString()
        sktbReq.pangkat_yang_menetapkan =
            edt_pangkat_pimpinan_sktb_add.text.toString().toUpperCase()
        sktbReq.nrp_yang_menetapkan = edt_nrp_pimpinan_sktb_add.text.toString()
        sktbReq.jabatan_yang_menetapkan = edt_jabatan_pimpinan_sktb_add.text.toString()
        sktbReq.tembusan = edt_tembusan_sktb_add.text.toString()
        sktbReq.id_skhd = idSkhd
        sktbReq.id_putkke = idPutkke

        btn_save_sktb_add.showProgress {
            progressColor = Color.WHITE
        }
        btn_save_sktb_add.showDrawable(animated) {
            textMarginRes = R.dimen.space_10dp
            buttonTextRes = R.string.data_saved
        }
        Handler(Looper.getMainLooper()).postDelayed({
            btn_save_sktb_add.hideDrawable(R.string.save)
        }, 2000)
        Log.e("Add SKTB", "$sktbReq")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AddRpsActivity.REQ_ID_SKHD) {
            if (resultCode == AddRpsActivity.RES_ID_SKHD) {
                val getSkhd = data?.getParcelableExtra<SkhdOnRpsModel>(AddRpsActivity.GET_SKHD)
                Log.e("skhd", "$getSkhd")
                if (getSkhd == null) {
                    txt_skhd_sktb_add.text = "No SKHD"
                } else {
                    txt_skhd_sktb_add.text = getSkhd.no_skhd
                    idSkhd = getSkhd.id
                }
            }
        }

        if (requestCode == AddRpphActivity.REQ_PUT_KKE_ON_RPPH) {
            if (resultCode == AddRpphActivity.RES_PUT_KKE_ON_RPPH) {
                val putKkeModel =
                    data?.getParcelableExtra<PutKkeOnRpphModel>(AddRpphActivity.GET_RPPH)
                Log.e("putKKe", "$putKkeModel")
                if (putKkeModel == null) {
                    txt_put_kke_sktb_add.text = "No PUTKKE"
                } else {
                    idPutkke = putKkeModel.id
                    txt_put_kke_sktb_add.text = putKkeModel.no_putkke
                }
            }
        }
    }

    companion object {
        const val REQ_SKHD_ON_SKTB = 1
        const val REQ_PUTKKE_ON_SKTB = 2
        const val RES_SKHD_ON_SKTB = 3
        const val RES_PUTKKE_ON_SKTB = 4
    }
}