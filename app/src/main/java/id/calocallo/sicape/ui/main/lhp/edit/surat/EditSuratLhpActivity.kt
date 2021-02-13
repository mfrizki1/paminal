package id.calocallo.sicape.ui.main.lhp.edit.surat

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ListSurat
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_surat_lhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditSuratLhpActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var suratReq = ListSurat()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_surat_lhp)
        sessionManager1 = SessionManager1(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Surat"

        val surat = intent.extras?.getParcelable<ListSurat>(EDIT_SURAT)
        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_save_surat_edit.gone()
            btn_delete_surat_edit.gone()
        }
        edt_surat_edit.setText(surat?.surat)
        btn_save_surat_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_surat_edit)
        btn_save_surat_edit.setOnClickListener {
            updateSurat(surat)

        }
        btn_delete_surat_edit.setOnClickListener {
            alert("Yakin Hapus Data?") {
                positiveButton("Iya") {
                    deleteSurat(surat)
                }
                negativeButton("Tidak") {}
            }.show()
        }

    }

    private fun updateSurat(surat: ListSurat?) {
        suratReq.surat = edt_surat_edit.text.toString()

        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        btn_save_surat_edit.showProgress {
            progressColor = Color.WHITE
        }
        btn_save_surat_edit.showDrawable(animatedDrawable) {
            buttonTextRes = R.string.data_updated
            textMarginRes = R.dimen.space_10dp
        }
        Handler(Looper.getMainLooper()).postDelayed({
            btn_save_surat_edit.hideDrawable(R.string.save)
        },3000)
    }
    private fun deleteSurat(surat: ListSurat?) {

    }

    companion object {
        const val EDIT_SURAT = "EDIT_SURAT"
    }
}