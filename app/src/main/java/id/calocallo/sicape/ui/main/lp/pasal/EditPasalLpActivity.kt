package id.calocallo.sicape.ui.main.lp.pasal

import android.graphics.Color
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.showDrawable
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.LpPasalResp
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_pasal_lp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditPasalLpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pasal_lp)
        val pasal = intent.extras?.getParcelable<LpPasalResp>("DETAIL_EDIT_PASAL")
        val namaJenis = intent.extras?.getString("NAMA_JENIS")
        setupActionBarWithBackButton(toolbar)
        when (namaJenis) {
            "pidana" -> supportActionBar?.title = "Edit Data Laporan Pidana"
            "disiplin" -> supportActionBar?.title = "Edit Data Laporan Disiplin"
            "kode_etik" -> supportActionBar?.title = "Edit Data Laporan Kode Etik"
        }
//        supportActionBar?.title = namaJenis
        edt_pasal_lp_edit.setText(pasal?.nama_pasal)

        btn_save_single_pasal_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_single_pasal_edit)
        btn_save_single_pasal_edit.setOnClickListener {
            val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
            val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
            animatedDrawable.setBounds(0,0,size,size)

            btn_save_single_pasal_edit.showProgress {
                progressColor = Color.WHITE
            }
            btn_save_single_pasal_edit.showDrawable(animatedDrawable){
                buttonTextRes = R.string.data_updated
                textMarginRes = R.dimen.space_10dp
            }

        }
    }
}