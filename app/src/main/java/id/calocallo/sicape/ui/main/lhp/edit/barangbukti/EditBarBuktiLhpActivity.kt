package id.calocallo.sicape.ui.main.lhp.edit.barangbukti

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ListBukti
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_bar_bukti_lhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditBarBuktiLhpActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var buktiReq = ListBukti()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_bar_bukti_lhp)
        sessionManager = SessionManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Barang Bukti"

        val bukti = intent.extras?.getParcelable<ListBukti>(EDIT_BARBUKTI)
        val hak = sessionManager.fetchHakAkses()
        if (hak == "operator") {
            btn_add_bukti_edit.gone()
            btn_delete_bukti_edit.gone()
        }
        edt_bukti_edit.setText(bukti?.bukti)
        btn_add_bukti_edit.attachTextChangeAnimator()
        bindProgressButton(btn_add_bukti_edit)
        btn_add_bukti_edit.setOnClickListener {
            updateBukti(bukti)
        }
        btn_delete_bukti_edit.attachTextChangeAnimator()
        bindProgressButton(btn_delete_bukti_edit)
        btn_delete_bukti_edit.setOnClickListener {
            alert("Yakin Hapus Data?") {
                positiveButton("Iya") {
                    deleteBukti(bukti)
                }
                negativeButton("TIdak") {
                }
            }.show()
        }

    }

    private fun updateBukti(bukti: ListBukti?) {
        buktiReq.bukti = edt_bukti_edit.text.toString()

        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)
        btn_add_bukti_edit.showProgress {
            progressColor = Color.WHITE
        }
        btn_add_bukti_edit.showDrawable(animatedDrawable) {
            buttonTextRes = R.string.data_updated
            textMarginRes = R.dimen.space_10dp
        }
        Handler(Looper.getMainLooper()).postDelayed({
            btn_add_bukti_edit.hideDrawable(R.string.save)
        }, 3000)
    }

    private fun deleteBukti(bukti: ListBukti?) {

    }

    companion object {
        const val EDIT_BARBUKTI = "EDIT_BARBUKTI"
    }
}