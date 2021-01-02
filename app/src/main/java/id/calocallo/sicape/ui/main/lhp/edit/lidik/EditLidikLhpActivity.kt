package id.calocallo.sicape.ui.main.lhp.edit.lidik

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ListLidik
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_lidik_lhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditLidikLhpActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var lidikReq = ListLidik()
    private var statusLidik: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_lidik_lhp)
        sessionManager = SessionManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Personel Penyelidik"
        val lhp = intent.extras?.getParcelable<ListLidik>(EDIT_LIDIK)

        val hak = sessionManager.fetchHakAkses()
        if (hak == "operator") {
            btn_delete_lidik_edit.gone()
            btn_save_lidik_edit.gone()
        }
        btn_save_lidik_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_lidik_edit)
        btn_save_lidik_edit.setOnClickListener {
            updateLidik(lhp)
        }
        btn_delete_lidik_edit.setOnClickListener {
            alert("Yakin Hapus Data?") {
                positiveButton("Iya") {
                    deleteLidik(lhp)
                }
                negativeButton("Tidak")
            }.show()
        }
        getViewLidik(lhp)
    }

    companion object {
        const val EDIT_LIDIK = "EDIT_LIDIK"
    }

    private fun getViewLidik(lhp: ListLidik?) {
        when (lhp?.status_penyelidik) {
            "ketua_tim" -> spinner_status_lidik_edit.setText("Ketua Tim")
            "anggota" -> spinner_status_lidik_edit.setText("Anggota")
        }
        edt_nama_lidik_edit.setText(lhp?.nama_lidik)
        edt_pangkat_lidik_edit.setText(lhp?.pangkat_lidik)
        edt_nrp_lidik_edit.setText(lhp?.nrp_lidik)

        val item = listOf("Ketua Tim", "Anggota")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        spinner_status_lidik_edit.setAdapter(adapter)
        spinner_status_lidik_edit.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> statusLidik = "ketua_tim"
                1 -> statusLidik = "anggota"
            }
        }
    }

    private fun updateLidik(lhp: ListLidik?) {
        btn_save_lidik_edit.showProgress {
            progressColor = Color.WHITE
        }
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, size, size)
        lidikReq.nama_lidik = edt_nama_lidik_edit.text.toString()
        lidikReq.pangkat_lidik = edt_pangkat_lidik_edit.text.toString()
        lidikReq.nrp_lidik = edt_nrp_lidik_edit.text.toString()
        lidikReq.status_penyelidik = statusLidik

        btn_save_lidik_edit.showDrawable(animatedDrawable) {
            buttonTextRes = R.string.data_updated
            textMarginRes = R.dimen.space_10dp
        }
        Handler(Looper.getMainLooper()).postDelayed({
            btn_save_lidik_edit.hideDrawable(R.string.save)
        }, 3000)

    }

    private fun deleteLidik(lhp: ListLidik?) {

    }
}