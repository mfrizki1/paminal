package id.calocallo.sicape.ui.main.lhp.edit.lidik

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.hideDrawable
import com.github.razir.progressbutton.showDrawable
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LhpResp
import id.calocallo.sicape.model.ListLidik
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_single_lidik_lhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddSingleLidikLhpActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var lidikReq = ListLidik()
    private var statusLidik: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_single_lidik_lhp)
        sessionManager1 = SessionManager1(this)
        val detailLhp = intent.extras?.getParcelable<LhpResp>(ADD_LIDIK)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Personel Penyelidik"

        btn_save_lidik_single.setOnClickListener {
            addLidik(detailLhp)
        }
        val item = listOf("Ketua Tim", "Anggota")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        spinner_status_lidik_single.setAdapter(adapter)
        spinner_status_lidik_single.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> statusLidik = "ketua_tim"
                1 -> statusLidik = "anggota"
            }
        }

    }

    private fun addLidik(detailLhp: LhpResp?) {
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, size, size)
        btn_save_lidik_single.showProgress {
            progressColor = Color.WHITE
        }
        lidikReq.nama_lidik=edt_nama_lidik_single.text.toString()
        lidikReq.pangkat_lidik=edt_pangkat_lidik_single.text.toString()
        lidikReq.nrp_lidik=edt_nrp_lidik_single.text.toString()
        lidikReq.status_penyelidik = statusLidik

        btn_save_lidik_single.showDrawable(animatedDrawable) {
            buttonTextRes = R.string.data_saved
            textMarginRes = R.dimen.space_10dp
        }
        Handler(Looper.getMainLooper()).postDelayed({
            btn_save_lidik_single.hideDrawable(R.string.save)
        }, 3000)
    }

    companion object {
        const val ADD_LIDIK = "ADD_LIDIK"
    }
}