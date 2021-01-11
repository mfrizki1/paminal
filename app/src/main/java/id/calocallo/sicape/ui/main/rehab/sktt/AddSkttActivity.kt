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
import id.calocallo.sicape.ui.main.choose.lhp.ChooseLhpActivity
import id.calocallo.sicape.ui.main.choose.lp.ChooseLpActivity
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_sktt.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddSkttActivity : BaseActivity() {
    private var idLhp: Int? = null
    private var idLp: Int? = null
    private var skttReq = SkttReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_sktt)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data SKTT"

        btn_save_sktt_add.attachTextChangeAnimator()
        bindProgressButton(btn_save_sktt_add)
        btn_save_sktt_add.setOnClickListener {
            addSktt()
        }
        btn_pick_lhp_sktt_add.setOnClickListener {
            startActivityForResult(Intent(this, ChooseLhpActivity::class.java), REQ_LHP_ON_SKTT)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }


        btn_pick_lp_sktt_add.setOnClickListener {
            val intent = Intent(this, ChooseLpActivity::class.java)
            intent.putExtra(LP_SKTT, LP_SKTT)
            startActivityForResult(intent, REQ_LP_ON_SKTT)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        txt_no_lp_sktt_add
    }

    private fun addSktt() {
        skttReq.id_lhp = idLhp
        skttReq.id_lp = idLp
        skttReq.no_sktt = edt_no_sktt_add.text.toString()
        skttReq.menimbang = edt_menimbang_sktt_add.text.toString()
        skttReq.mengingat_p5 = edt_mengingat_p5_sktt_add.text.toString()
        skttReq.kota_penetapan = edt_kota_penetapan_sktt_add.text.toString()
        skttReq.tanggal_penetapan = edt_tanggal_penetapan_sktt_add.text.toString()
        skttReq.nama_yang_menetapkan = edt_nama_pimpinan_sktt_add.text.toString()
        skttReq.pangkat_yang_menetapkan =
            edt_pangkat_pimpinan_sktt_add.text.toString().toUpperCase()
        skttReq.nrp_yang_menetapkan = edt_nrp_pimpinan_sktt_add.text.toString()
        skttReq.jabatan_yang_menetapkan = edt_jabatan_pimpinan_sktt_add.text.toString()
        skttReq.tembusan = edt_tembusan_sktt_add.text.toString()
        Log.e("add SKTT", "$skttReq")

        val animated = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animated.setBounds(0, 0, size, size)
        btn_save_sktt_add.showProgress {
            progressColor = Color.WHITE
        }
        btn_save_sktt_add.showDrawable(animated) {
            textMarginRes = R.dimen.space_10dp
            buttonTextRes = R.string.data_saved
        }
        Handler(Looper.getMainLooper()).postDelayed({
            btn_save_sktt_add.hideDrawable(R.string.save)
        }, 2000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_LHP_ON_SKTT) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val lhpResp = data?.getParcelableExtra<LhpResp>("CHOOSE_LHP")
                    idLhp = lhpResp?.id
                    txt_no_lhp_sktt_add.text = lhpResp?.no_lhp
                }
            }
        }
        if (requestCode == REQ_LP_ON_SKTT) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val getLpWithoutSktt = data?.getParcelableExtra<LpCustomResp>(GET_LP_ON_SKTT)
                    idLp = getLpWithoutSktt?.id
                    txt_no_lp_sktt_add.text = getLpWithoutSktt?.no_lp
                }
            }
        }
    }

    companion object {
        const val REQ_LHP_ON_SKTT = 1
        const val REQ_LP_ON_SKTT = 2
        const val GET_LP_ON_SKTT = "GET_LP_ON_SKTT"
        const val LP_SKTT = "LP_SKTT"
    }
}