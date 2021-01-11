package id.calocallo.sicape.ui.main.rehab.sp3

import android.app.Activity
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
import id.calocallo.sicape.model.SktbOnSp3
import id.calocallo.sicape.model.SkttOnSp3
import id.calocallo.sicape.network.request.Sp3Req
import id.calocallo.sicape.ui.main.choose.ChooseSktbSkttActivity
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_sp3.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddSp3Activity : BaseActivity() {
    companion object {
        const val SKHD_ON_SP3 = "SKHD_ON_SP3"
        const val SKTT_ON_SP3 = "SKTT_ON_SP3"
        const val REQ_SKTB_ON_SP3 = 1
        const val REQ_SKTT_ON_SP3 = 2
    }

    private var idSktb: Int? = null
    private var idSktt: Int? = null
    private var sp3Req = Sp3Req()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_sp3)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data SP3"
        ll_sktb_sp3_add
        btn_pick_sktb_sp3_add.setOnClickListener {
            val intent = Intent(this, ChooseSktbSkttActivity::class.java)
            startActivityForResult(intent, REQ_SKTB_ON_SP3)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }


        btn_pick_sktt_sp3_add.setOnClickListener {
            val intent = Intent(this, ChooseSktbSkttActivity::class.java)
            intent.putExtra(SKTT_ON_SP3, SKTT_ON_SP3)
            startActivityForResult(intent, REQ_SKTT_ON_SP3)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        rg_sktb_sktt_sp3_add.setOnCheckedChangeListener { group, checkedId ->
            val radio = findViewById<RadioButton>(checkedId)
            if (radio.isChecked && radio.text == "SKTB") {
                ll_sktb_sp3_add.visible()
                ll_sktt_sp3_add.gone()
            } else {
                ll_sktb_sp3_add.gone()
                ll_sktt_sp3_add.visible()
            }
        }
        btn_save_sp3_add.attachTextChangeAnimator()
        bindProgressButton(btn_save_sp3_add)
        btn_save_sp3_add.setOnClickListener {
            sp3Req.no_sp4 = edt_no_sp3_add.text.toString()
            sp3Req.mengingat_p4 = edt_mengingat_p4_sp3_add.text.toString()
            sp3Req.mengingat_p5 = edt_mengingat_p5_sp3_add.text.toString()
            sp3Req.menetapkan_p1 = edt_menetapkan_p1_sp3_add.text.toString()
            sp3Req.kota_keluar = edt_kota_dikeluarkan_sp3_add.text.toString()
            sp3Req.tanggal_keluar = edt_tanggal_dikeluarkan_sp3_add.text.toString()
            sp3Req.nama_akreditor = edt_nama_akreditor_sp3_add.text.toString()
            sp3Req.pangkat_akreditor = edt_pangkat_akreditor_sp3_add.text.toString().toUpperCase()
            sp3Req.nrp_akreditor = edt_nrp_akreditor_sp3_add.text.toString()
            if (idSktt != null) {
                sp3Req.id_sktt = idSktt
                sp3Req.id_sktb = null
            } else {
                sp3Req.id_sktb = idSktb
                sp3Req.id_sktt = null
            }
            Log.e("add Sp3", "$sp3Req")


            val animated = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
            val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
            animated.setBounds(0, 0, size, size)
            btn_save_sp3_add.showProgress {
                progressColor = Color.WHITE
            }
            btn_save_sp3_add.showDrawable(animated) {
                textMarginRes = R.dimen.space_10dp
                buttonTextRes = R.string.data_updated
            }
            Handler(Looper.getMainLooper()).postDelayed({
                btn_save_sp3_add.hideDrawable(R.string.save)
            }, 2000)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_SKTB_ON_SP3) {
            if (resultCode == Activity.RESULT_OK) {
                val sktb = data?.getParcelableExtra<SktbOnSp3>(ChooseSktbSkttActivity.GET_SKTB)
//                Log.e("skhd", "$sktb")
                if (sktb == null) {
                    txt_sktb_sp3_add.text = "No SKTB"
                } else {
                    txt_sktb_sp3_add.text = sktb.no_sktb
                    idSktb = sktb.id
                }
            }
        }

        if (requestCode == REQ_SKTT_ON_SP3) {
            if (resultCode == Activity.RESULT_OK) {
                val sktt =
                    data?.getParcelableExtra<SkttOnSp3>(ChooseSktbSkttActivity.GET_SKTT)
                Log.e("putKKe", "$sktt")
                if (sktt == null) {
                    txt_sktt_sp3_add.text = "No SKTT"
                } else {
                    idSktt = sktt.id
                    txt_sktt_sp3_add.text = sktt.no_sktt
                }
            }
        }
    }


}