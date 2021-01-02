package id.calocallo.sicape.ui.main.lhp.edit.saksi

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LhpModel
import id.calocallo.sicape.model.ListSaksi
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_single_saksi_lhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddSingleSaksiLhpActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var saksiReqLhp = ListSaksi()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_single_saksi_lhp)
        sessionManager = SessionManager(this)
        val detailLhp = intent.extras?.getParcelable<LhpModel>(ADD_SAKSI_LHP)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Saksi LHP"

        btn_add_saksi_lhp_single.attachTextChangeAnimator()
        bindProgressButton(btn_add_saksi_lhp_single)
        btn_add_saksi_lhp_single.setOnClickListener {
            addSaksiLhp(detailLhp)
        }
    }

    private fun addSaksiLhp(detailLhp: LhpModel?) {
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, size, size)
        btn_add_saksi_lhp_single.showProgress {
            progressColor = Color.WHITE
        }
        saksiReqLhp.nama_saksi = edt_nama_saksi_lhp_single.text.toString()
        saksiReqLhp.uraian_saksi = edt_uraian_saksi_lhp_single.text.toString()

        btn_add_saksi_lhp_single.showDrawable(animatedDrawable) {
            buttonTextRes = R.string.data_saved
            textMarginRes = R.dimen.space_10dp
        }
        Handler(Looper.getMainLooper()).postDelayed({
            btn_add_saksi_lhp_single.hideDrawable(R.string.save)
        }, 3000)

    }

    companion object {
        const val ADD_SAKSI_LHP = "ADD_SAKSI_LHP"
    }
}