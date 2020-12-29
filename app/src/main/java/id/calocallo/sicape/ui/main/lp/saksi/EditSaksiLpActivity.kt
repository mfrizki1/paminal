package id.calocallo.sicape.ui.main.lp.saksi

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.LpSaksiResp
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_saksi_lp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditSaksiLpActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_saksi_lp)
        sessionManager = SessionManager(this)
        val namaJenis = intent.extras?.getString("NAMA_JENIS")
        val saksi = intent.extras?.getParcelable<LpSaksiResp>("SAKSI_EDIT")
        setupActionBarWithBackButton(toolbar)
        when(namaJenis){
            "pidana" -> supportActionBar?.title = "Edit Data Laporan Pidana"
            "disiplin" -> supportActionBar?.title = "Edit Data Laporan Disiplin"
            "kode_etik" -> supportActionBar?.title = "Edit Data Laporan Kode Etik"
        }

        getViewSaksi(saksi)
        btn_save_edit_saksi_lp.attachTextChangeAnimator()
        bindProgressButton(btn_save_edit_saksi_lp)
        btn_save_edit_saksi_lp.setOnClickListener {
            val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
            val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
            animatedDrawable.setBounds(0,0,size,size)

            btn_save_edit_saksi_lp.showProgress {
                progressColor = Color.WHITE
            }
            btn_save_edit_saksi_lp.showDrawable(animatedDrawable){
                buttonTextRes = R.string.data_updated
                textMarginRes = R.dimen.space_10dp
            }
            Handler(Looper.getMainLooper()).postDelayed({
                btn_save_edit_saksi_lp.hideDrawable(R.string.save)
            },3000)
        }
    }

    private fun getViewSaksi(saksi: LpSaksiResp?) {
        edt_saksi_edit.setText(saksi?.nama_saksi)
    }
}