package id.calocallo.sicape.ui.main.rehab.sktt

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LhpResp
import id.calocallo.sicape.network.request.SkttReq
import id.calocallo.sicape.network.response.LpCustomResp
import id.calocallo.sicape.network.response.SkttResp
import id.calocallo.sicape.ui.main.choose.lhp.ChooseLhpActivity
import id.calocallo.sicape.ui.main.choose.lp.PickJenisLpActivity
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_sktt.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditSkttActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var idLhp: Int? = null
    private var idLp: Int? = null
    private var skttReq = SkttReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_sktt)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data SKTT"
        val getSktt = intent.extras?.getParcelable<SkttResp>(EDIT_SKTT)
        getViewEditSktt(getSktt)
        btn_pick_lhp_sktt_edit.setOnClickListener {
            startActivityForResult(
                Intent(this, ChooseLhpActivity::class.java),
                AddSkttActivity.REQ_LHP_ON_SKTT
            )
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        btn_pick_lp_sktt_edit.setOnClickListener {
            val intent = Intent(this, PickJenisLpActivity::class.java)
            intent.putExtra(AddSkttActivity.LP_SKTT, AddSkttActivity.LP_SKTT)
            startActivityForResult(intent, AddSkttActivity.REQ_LP_ON_SKTT)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        btn_save_sktt_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_sktt_edit)
        btn_save_sktt_edit.setOnClickListener {
            updateSktt(getSktt)
        }
    }

    private fun updateSktt(sktt: SkttResp?) {

        skttReq.no_sktt = edt_no_sktt_edit.text.toString()
        skttReq.menimbang = edt_menimbang_sktt_edit.text.toString()
        skttReq.mengingat_p5 = edt_mengingat_p5_sktt_edit.text.toString()
        skttReq.kota_penetapan = edt_kota_penetapan_sktt_edit.text.toString()
        skttReq.tanggal_penetapan = edt_tanggal_penetapan_sktt_edit.text.toString()
        skttReq.nama_yang_menetapkan = edt_nama_pimpinan_sktt_edit.text.toString()
        skttReq.pangkat_yang_menetapkan = edt_pangkat_pimpinan_sktt_edit.text.toString()
        skttReq.nrp_yang_menetapkan = edt_nrp_pimpinan_sktt_edit.text.toString()
        skttReq.jabatan_yang_menetapkan = edt_jabatan_pimpinan_sktt_edit.text.toString()
        skttReq.tembusan = edt_tembusan_sktt_edit.text.toString()
        skttReq.id_lhp = idLhp
        skttReq.id_lp = idLp
        Log.e("edit SKTT", "$skttReq")


        val animated = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animated.setBounds(0, 0, size, size)
        btn_save_sktt_edit.showProgress {
            progressColor = Color.WHITE
        }
        btn_save_sktt_edit.showDrawable(animated) {
            textMarginRes = R.dimen.space_10dp
            buttonTextRes = R.string.data_updated
        }
        Handler(Looper.getMainLooper()).postDelayed({
            btn_save_sktt_edit.hideDrawable(R.string.save)
        }, 2000)
    }

    private fun getViewEditSktt(sktt: SkttResp?) {
        edt_no_sktt_edit.setText(sktt?.no_sktt)
        edt_menimbang_sktt_edit.setText(sktt?.menimbang)
        edt_mengingat_p5_sktt_edit.setText(sktt?.mengingat_p5)
        edt_kota_penetapan_sktt_edit.setText(sktt?.kota_penetapan)
        edt_tanggal_penetapan_sktt_edit.setText(sktt?.tanggal_penetapan)
        edt_nama_pimpinan_sktt_edit.setText(sktt?.nama_yang_menetapkan)
        edt_pangkat_pimpinan_sktt_edit.setText(
            sktt?.pangkat_yang_menetapkan.toString().toUpperCase()
        )
        edt_nrp_pimpinan_sktt_edit.setText(sktt?.nrp_yang_menetapkan)
        edt_jabatan_pimpinan_sktt_edit.setText(sktt?.jabatan_yang_menetapkan)
        edt_tembusan_sktt_edit.setText(sktt?.tembusan)
        txt_no_lhp_sktt_edit.text = sktt?.lhp?.no_lhp
//        txt_no_lp_sktt_edit.text = sktt?.lp?.no_lp
        idLhp = sktt?.lhp?.id
        idLp = sktt?.lp?.id
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AddSkttActivity.REQ_LHP_ON_SKTT) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val lhpResp = data?.getParcelableExtra<LhpResp>("CHOOSE_LHP")
                    idLhp = lhpResp?.id
                    txt_no_lhp_sktt_edit.text = lhpResp?.no_lhp
                }
            }
        }
        if (requestCode == AddSkttActivity.REQ_LP_ON_SKTT) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val getLpWithoutSktt =
                        data?.getParcelableExtra<LpCustomResp>(AddSkttActivity.GET_LP_ON_SKTT)
                    idLp = getLpWithoutSktt?.id
                    txt_no_lp_sktt_edit.text = getLpWithoutSktt?.no_lp
                }
            }
        }
    }

    companion object {
        const val EDIT_SKTT = "EDIT_SKTT"
    }
}