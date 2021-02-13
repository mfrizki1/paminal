package id.calocallo.sicape.ui.main.lhp.edit.analisa

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ListAnalisa
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_analisa_lhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditAnalisaLhpActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var analisaReq = ListAnalisa()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_analisa_lhp)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Analisa"
        sessionManager1 = SessionManager1(this)
        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_add_analisa_edit.gone()
            btn_delete_analisa_edit.gone()
        }
        val analisa = intent.extras?.getParcelable<ListAnalisa>(EDIT_ANALISA)
        edt_analisa_edit.setText(analisa?.analisa)
        btn_add_analisa_edit.attachTextChangeAnimator()
        bindProgressButton(btn_add_analisa_edit)
        btn_add_analisa_edit.setOnClickListener {
            updateAnalisa(analisa)
        }

        btn_delete_analisa_edit.setOnClickListener {
            alert("Yakin Hapus Data?"){
                positiveButton("Iya"){
                    deleteAnalisa(analisa)
                }
                negativeButton("Tidak"){}
            }.show()
        }
    }

    private fun updateAnalisa(analisa: ListAnalisa?) {
        analisaReq.analisa = edt_analisa_edit.text.toString()

        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)
        btn_add_analisa_edit.showProgress {
            progressColor = Color.WHITE
        }
        btn_add_analisa_edit.showDrawable(animatedDrawable) {
            buttonTextRes = R.string.data_updated
            textMarginRes = R.dimen.space_10dp
        }
        Handler(Looper.getMainLooper()).postDelayed({
        btn_add_analisa_edit.hideDrawable(R.string.save)
        },3000)
    }

    private fun deleteAnalisa(analisa: ListAnalisa?) {

    }

    companion object {
        const val EDIT_ANALISA = "EDIT_ANALISA"
    }
}