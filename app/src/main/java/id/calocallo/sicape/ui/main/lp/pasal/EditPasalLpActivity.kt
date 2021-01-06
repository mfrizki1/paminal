package id.calocallo.sicape.ui.main.lp.pasal

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.PasalReq
import id.calocallo.sicape.network.response.LpPasalResp
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.alert
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_pasal_lp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditPasalLpActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var pasalReq = PasalReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pasal_lp)
        sessionManager = SessionManager(this)
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
        edt_detail_pasal_lp_edit.setText(pasal?.isi_pasal)

        btn_save_single_pasal_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_single_pasal_edit)
        btn_save_single_pasal_edit.setOnClickListener {
            pasalReq.nama_pasal = edt_pasal_lp_edit.text.toString()
            pasalReq.isi_pasal = edt_detail_pasal_lp_edit.text.toString()
            val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
            val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
            animatedDrawable.setBounds(0, 0, size, size)

            btn_save_single_pasal_edit.showProgress {
                progressColor = Color.WHITE
            }
            btn_save_single_pasal_edit.showDrawable(animatedDrawable) {
                buttonTextRes = R.string.data_updated
                textMarginRes = R.dimen.space_10dp
            }
            Handler(Looper.getMainLooper()).postDelayed({
                btn_save_single_pasal_edit.hideDrawable(R.string.save)
                Log.e("edit_pasal", "$pasalReq")
            }, 3000)

        }

        btn_delete_single_pasal_edit.setOnClickListener {
            alert ("Yakin Hapus Data?"){
                positiveButton("Iya"){
                    deletePasal()
                }
                negativeButton("Tidak")
            }.show()
        }
    }

    private fun deletePasal() {

    }
}