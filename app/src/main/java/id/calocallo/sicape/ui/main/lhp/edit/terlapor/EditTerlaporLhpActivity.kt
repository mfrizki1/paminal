package id.calocallo.sicape.ui.main.lhp.edit.terlapor

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ListTerlapor
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_terlapor_lhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditTerlaporLhpActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var terlaporReq = ListTerlapor()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_terlapor_lhp)
        sessionManager = SessionManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Terlapor"
        val hak = sessionManager.fetchHakAkses()
        if (hak == "operator") {
            btn_save_terlapor_edit.gone()
            btn_delete_terlapor_edit.gone()
        }
        val terlapor = intent.extras?.getParcelable<ListTerlapor>(EDIT_TERLAPOR)

        edt_nama_terlapor_edit.setText(terlapor?.nama_terlapor)
        edt_penjelasan_terlapor_edit.setText(terlapor?.uraian_terlapor)
        btn_save_terlapor_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_terlapor_edit)
        btn_save_terlapor_edit.setOnClickListener {
            updateTerlapor(terlapor)

        }

        btn_delete_terlapor_edit.setOnClickListener {
            alert("Yakin Hapus Data?") {
                positiveButton("Iya") {
                    deleteTerlapor(terlapor)
                }
                negativeButton("Tidak") {}
            }.show()
        }


    }

    private fun updateTerlapor(terlapor: ListTerlapor?) {
        terlaporReq.nama_terlapor = edt_nama_terlapor_edit.text.toString()
        terlaporReq.uraian_terlapor = edt_penjelasan_terlapor_edit.text.toString()
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        btn_save_terlapor_edit.showProgress {
            progressColor = Color.WHITE
        }
        btn_save_terlapor_edit.showDrawable(animatedDrawable) {
            buttonTextRes = R.string.data_updated
            textMarginRes = R.dimen.space_10dp

        }
        Handler(Looper.getMainLooper()).postDelayed({
            btn_save_terlapor_edit.hideDrawable(R.string.save)
        }, 3000)
    }

    private fun deleteTerlapor(terlapor: ListTerlapor?) {

    }

    companion object {
        const val EDIT_TERLAPOR = "EDIT_TERLAPOR"
    }
}