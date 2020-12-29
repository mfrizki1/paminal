package id.calocallo.sicape.ui.main.lp.pasal

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.PasalReq
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_pasal_lp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddPasalLpActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var pasalReq = PasalReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pasal_lp)
        sessionManager = SessionManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Pasal"

        bindProgressButton(btn_save_single_pasal)
        btn_save_single_pasal.attachTextChangeAnimator()
        btn_save_single_pasal.setOnClickListener {
            addPasal()
        }

    }

    private fun addPasal() {
        pasalReq.nama_pasal = edt_pasal_lp.text.toString()

        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, size, size)
        btn_save_single_pasal.showProgress {
            progressColor = Color.WHITE
        }
        btn_save_single_pasal.showDrawable(animatedDrawable) {
            buttonTextRes = R.string.data_saved
            textMarginRes = R.dimen.space_10dp
        }
        Handler(Looper.getMainLooper()).postDelayed({
            btn_save_single_pasal.hideDrawable(R.string.data_saved)
        }, 3000)
    }
}