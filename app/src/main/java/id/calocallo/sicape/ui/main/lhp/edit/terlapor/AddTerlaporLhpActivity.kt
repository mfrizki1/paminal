package id.calocallo.sicape.ui.main.lhp.edit.terlapor

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import com.github.razir.progressbutton.ProgressButtonUtils.Companion.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LhpModel
import id.calocallo.sicape.model.ListTerlapor
import id.calocallo.sicape.ui.main.lhp.EditLhpActivity
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_terlapor_lhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddTerlaporLhpActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var terlaporReq = ListTerlapor()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_terlapor_lhp)
        sessionManager = SessionManager(this)
        val detailLhp = intent.extras?.getParcelable<LhpModel>(EditLhpActivity.EDIT_LHP)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Terlapor"

        btn_add_terlapor_single.attachTextChangeAnimator()
        bindProgressButton(btn_add_terlapor_single)
        btn_add_terlapor_single.setOnClickListener {
            addTerlapor()
        }
    }

    private fun addTerlapor() {
        terlaporReq.nama_terlapor = edt_nama_terlapor_single.text.toString()
        terlaporReq.uraian_terlapor = edt_penjelasan_terlapor_single.text.toString()

        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        btn_add_terlapor_single.showProgress {
            progressColor = Color.WHITE
        }

        btn_add_terlapor_single.showDrawable(animatedDrawable) {
            buttonTextRes = R.string.data_saved
            textMarginRes = R.dimen.space_10dp
        }
        Handler(Looper.getMainLooper()).postDelayed({
            btn_add_terlapor_single.hideDrawable(R.string.save)
        }, 3000)
    }

    companion object {
        const val ADD_TERLAPOR = "ADD_TERLAPOR"
    }
}