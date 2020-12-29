package id.calocallo.sicape.ui.main.lp.saksi

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.SaksiReq
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_pasal_lp.*
import kotlinx.android.synthetic.main.activity_add_saksi_lp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddSaksiLpActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var saksiLpReq = SaksiReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_saksi_lp)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Saksi"

        btn_save_add_saksi.attachTextChangeAnimator()
        bindProgressButton(btn_save_add_saksi)
        btn_save_add_saksi.setOnClickListener {
            addSaksiLp()
        }
    }

    private fun addSaksiLp() {
        saksiLpReq.nama_saksi = edt_nama_saksi_single.text.toString()

        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, size, size)
        btn_save_add_saksi.showProgress {
            progressColor = Color.WHITE
        }
        btn_save_add_saksi.showDrawable(animatedDrawable) {
            buttonTextRes = R.string.data_saved
            textMarginRes = R.dimen.space_10dp 
        }

        Handler(Looper.getMainLooper()).postDelayed({
            btn_save_add_saksi.hideDrawable(R.string.data_saved)
        }, 3000)
    }
}