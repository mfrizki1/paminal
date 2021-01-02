package id.calocallo.sicape.ui.main.lhp.edit.saksi

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ListSaksi
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditSaksiLhpActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager

    private var saksiLhpReq = ListSaksi()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_saksi_lhp)
        sessionManager = SessionManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Saksi LHP"

        val hak = sessionManager.fetchHakAkses()
        if (hak == "operator") {
            btn_save_saksi_lhp_edit.gone()
            btn_delete_saksi_lhp_edit.gone()
        }

        val saksi = intent.extras?.getParcelable<ListSaksi>(EDIT_SAKSI_LHP)
        getViewSaksiLhp(saksi)

        btn_save_saksi_lhp_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_saksi_lhp_edit)
        btn_save_saksi_lhp_edit.setOnClickListener {
            updateSaksiLhp(saksi)
        }

        btn_delete_saksi_lhp_edit.setOnClickListener {
            alert("Yakin Hapus Data?") {
                positiveButton("Iya") {
                    deleteSaksiLhp(saksi)
                }
                negativeButton("Tidak") {}
            }.show()
        }

    }

    private fun getViewSaksiLhp(saksi: ListSaksi?) {
        edt_nama_saksi_lhp_edit.setText(saksi?.nama_saksi)
        edt_uraian_saksi_lhp_edit.setText(saksi?.uraian_saksi)
    }

    private fun updateSaksiLhp(saksi: ListSaksi?) {
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, size, size)
        btn_save_saksi_lhp_edit.showProgress {
            progressColor = Color.WHITE
        }
        saksiLhpReq.nama_saksi = edt_nama_saksi_lhp_edit.text.toString()
        saksiLhpReq.uraian_saksi = edt_uraian_saksi_lhp_edit.text.toString()

        btn_save_saksi_lhp_edit.showDrawable(animatedDrawable){
            buttonTextRes = R.string.data_updated
            textMarginRes = R.dimen.space_10dp
        }
        Handler(Looper.getMainLooper()).postDelayed({
            btn_save_saksi_lhp_edit.hideDrawable(R.string.save)
        },3000)

    }

    private fun deleteSaksiLhp(saksi: ListSaksi?) {

    }

    companion object {
        const val EDIT_SAKSI_LHP = "EDIT_SAKSI_LHP"
    }
}