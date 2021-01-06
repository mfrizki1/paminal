package id.calocallo.sicape.ui.main.lhp.edit.barangbukti

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LhpResp
import id.calocallo.sicape.model.ListBukti
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_bar_bukti_lhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddBarBuktiLhpActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var buktiReq = ListBukti()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_bar_bukti_lhp)
        sessionManager = SessionManager(this)
        val detailLhp = intent.extras?.getParcelable<LhpResp>(ADD_BARBUKTI)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Barang Bukti"

        btn_add_bukti_single.attachTextChangeAnimator()
        bindProgressButton(btn_add_bukti_single)
        btn_add_bukti_single.setOnClickListener {
            val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
            //Defined bounds are required for your drawable
            val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
            animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)
            buktiReq.bukti = edt_bukti_single.text.toString()

            btn_add_bukti_single.showProgress {
                progressColor = Color.WHITE
            }

            btn_add_bukti_single.showDrawable(animatedDrawable) {
                buttonTextRes = R.string.data_saved
                textMarginRes = R.dimen.space_10dp
            }

            Handler(Looper.getMainLooper()).postDelayed({
                btn_add_bukti_single.hideDrawable(R.string.save)
            }, 3000)
        }
    }

    companion object {
        const val ADD_BARBUKTI = "ADD_BARBUKTI"
    }
}