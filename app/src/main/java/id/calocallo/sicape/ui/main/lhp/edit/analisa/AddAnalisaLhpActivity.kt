package id.calocallo.sicape.ui.main.lhp.edit.analisa

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.hideDrawable
import com.github.razir.progressbutton.showDrawable
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LhpModel
import id.calocallo.sicape.model.ListAnalisa
import id.calocallo.sicape.ui.main.lhp.EditLhpActivity
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_analisa_lhp.*
import kotlinx.android.synthetic.main.activity_add_terlapor_lhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddAnalisaLhpActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var analisaReq = ListAnalisa()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_analisa_lhp)
        sessionManager = SessionManager(this)
        val detailLhp = intent.extras?.getParcelable<LhpModel>(EditLhpActivity.EDIT_LHP)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Analisa"
        btn_add_analisa_single.setOnClickListener {
            addAnalisa()
        }
    }

    private fun addAnalisa() {
        analisaReq.analisa = edt_analisa_single.text.toString()

        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        btn_add_analisa_single.showProgress {
            progressColor = Color.WHITE
        }
        btn_add_analisa_single.showDrawable(animatedDrawable) {
            buttonTextRes = R.string.data_saved
            textMarginRes = R.dimen.space_10dp
        }
        Handler(Looper.getMainLooper()).postDelayed({
            btn_add_analisa_single.hideDrawable(R.string.save)
        }, 3000)

    }

    companion object {
        const val ADD_ANALISA = "ADD_ANALISA"
    }
}